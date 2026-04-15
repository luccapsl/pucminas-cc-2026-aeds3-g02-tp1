/*********
 * ARVORE B+ 
 * 
 * Os nomes dos métodos foram mantidos em inglês
 * apenas para manter a coerência com o resto da
 * disciplina:
 * - boolean create(RegistroArvoreBMais objeto)   
 * - int[] read(RegistroArvoreBMais objeto)
 * - boolean delete(RegistroArvoreBMais objeto)
 * 
 * Implementado pelo Prof. Marcos Kutova
 * v2.0 - 2021
 * 
 * CORREÇÕES APLICADAS:
 * - Evita acesso a índice -1 no split (Caso 2).
 * - Busca por prefixo com navegação entre folhas encadeadas.
 */
package Genericos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class ArvoreBMais<T extends RegistroArvoreBMais<T>> {

    private int ordem;
    private int maxElementos;
    private int maxFilhos;
    private RandomAccessFile arquivo;
    private String nomeArquivo;
    private Constructor<T> construtor;

    private T elemAux;
    private long paginaAux;
    private boolean cresceu;
    private boolean diminuiu;

    private class Pagina {

        protected int ordem;
        protected Constructor<T> construtor;
        protected int maxElementos;
        protected int maxFilhos;
        protected int TAMANHO_ELEMENTO;
        protected int TAMANHO_PAGINA;

        protected ArrayList<T> elementos;
        protected ArrayList<Long> filhos;
        protected long proxima;

        public Pagina(Constructor<T> ct, int o) throws Exception {

            this.construtor = ct;
            this.ordem = o;
            this.maxFilhos = this.ordem;
            this.maxElementos = this.ordem - 1;
            this.elementos = new ArrayList<>(this.maxElementos);
            this.filhos = new ArrayList<>(this.maxFilhos);
            this.proxima = -1;

            this.TAMANHO_ELEMENTO = this.construtor.newInstance().size();
            this.TAMANHO_PAGINA = 4 + this.maxElementos * this.TAMANHO_ELEMENTO + this.maxFilhos * 8 + 8;
        }

        protected byte[] toByteArray() throws IOException {

            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(ba);

            out.writeInt(this.elementos.size());

            int i = 0;
            while (i < this.elementos.size()) {
                out.writeLong(this.filhos.get(i).longValue());
                out.write(this.elementos.get(i).toByteArray());
                i++;
            }
            if (this.filhos.size() > 0)
                out.writeLong(this.filhos.get(i).longValue());
            else
                out.writeLong(-1L);

            byte[] registroVazio = new byte[TAMANHO_ELEMENTO];
            while (i < this.maxElementos) {
                out.write(registroVazio);
                out.writeLong(-1L);
                i++;
            }

            out.writeLong(this.proxima);

            return ba.toByteArray();
        }

        public void fromByteArray(byte[] buffer) throws Exception {

            ByteArrayInputStream ba = new ByteArrayInputStream(buffer);
            DataInputStream in = new DataInputStream(ba);

            int n = in.readInt();

            int i = 0;
            this.elementos = new ArrayList<>(this.maxElementos);
            this.filhos = new ArrayList<>(this.maxFilhos);
            T elem;
            while (i < n) {
                this.filhos.add(in.readLong());
                byte[] registro = new byte[TAMANHO_ELEMENTO];
                in.read(registro);
                elem = this.construtor.newInstance();
                elem.fromByteArray(registro);
                this.elementos.add(elem);
                i++;
            }
            this.filhos.add(in.readLong());
            in.skipBytes((this.maxElementos - i) * (TAMANHO_ELEMENTO + 8));
            this.proxima = in.readLong();
        }
    }

    public ArvoreBMais(Constructor<T> c, int o, String na) throws Exception {

        construtor = c;
        ordem = o;
        maxElementos = o - 1;
        maxFilhos = o;
        nomeArquivo = na;

        arquivo = new RandomAccessFile(nomeArquivo, "rw");
        if (arquivo.length() < 16) {
            arquivo.writeLong(-1);
            arquivo.writeLong(-1);
        }
    }

    public boolean empty() throws IOException {
        long raiz;
        arquivo.seek(0);
        raiz = arquivo.readLong();
        return raiz == -1;
    }

    public ArrayList<T> read(T elem) throws Exception {

        long raiz;
        arquivo.seek(0);
        raiz = arquivo.readLong();

        if (raiz != -1)
            return read1(elem, raiz);
        else {
            ArrayList<T> resposta = new ArrayList<>();
            return resposta;
        }
    }

    /**
     * Busca recursiva com suporte a prefixo (apenas idUsuario) e navegação
     * sequencial pelas folhas encadeadas.
     */
    private ArrayList<T> read1(T elem, long pagina) throws Exception {
        if (pagina == -1) {
            return new ArrayList<>();
        }

        arquivo.seek(pagina);
        Pagina pa = new Pagina(construtor, ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.fromByteArray(buffer);

        int i = 0;
        while (elem != null && i < pa.elementos.size() && elem.compareTo(pa.elementos.get(i)) > 0) {
            i++;
        }

        // Se for folha, tenta encontrar o primeiro elemento que satisfaz a busca
        if (pa.filhos.get(0) == -1) {
            // Se não encontrou um elemento exato ou prefixo, verifica próxima folha
            if (i >= pa.elementos.size()) {
                if (pa.proxima != -1) {
                    return read1(elem, pa.proxima);
                } else {
                    return new ArrayList<>();
                }
            }

            T primeiro = pa.elementos.get(i);
            boolean match = false;

            if (elem == null) {
                match = true;
            } else if (elem.compareTo(primeiro) == 0) {
                match = true;
            } else if (elem instanceof ParUsuarioNomeCursoId) {
                ParUsuarioNomeCursoId e = (ParUsuarioNomeCursoId) elem;
                ParUsuarioNomeCursoId p = (ParUsuarioNomeCursoId) primeiro;
                match = (e.getIdUsuario() == p.getIdUsuario() && e.getNomeCurso().isEmpty() && e.getIdCurso() == Integer.MIN_VALUE);
            } else if (elem instanceof ParIdUsuarioIdCurso) {
                ParIdUsuarioIdCurso e = (ParIdUsuarioIdCurso) elem;
                ParIdUsuarioIdCurso p = (ParIdUsuarioIdCurso) primeiro;
                match = (e.getIdUsuario() == p.getIdUsuario() && e.getIdCurso() == Integer.MIN_VALUE);
            }

            if (!match) {
                return new ArrayList<>();
            }

            // A partir deste ponto, coleta todos os registros que satisfazem o critério
            ArrayList<T> lista = new ArrayList<>();
            boolean continuar = true;
            long paginaAtual = pagina;

            while (continuar && paginaAtual != -1) {
                arquivo.seek(paginaAtual);
                pa = new Pagina(construtor, ordem);
                arquivo.read(buffer);
                pa.fromByteArray(buffer);

                for (int j = 0; j < pa.elementos.size(); j++) {
                    T atual = pa.elementos.get(j);
                    boolean adicionar = false;

                    if (elem == null) {
                        adicionar = true;
                    } else if (elem.compareTo(atual) == 0) {
                        adicionar = true;
                    } else if (elem instanceof ParUsuarioNomeCursoId) {
                        ParUsuarioNomeCursoId e = (ParUsuarioNomeCursoId) elem;
                        ParUsuarioNomeCursoId a = (ParUsuarioNomeCursoId) atual;
                        adicionar = (e.getIdUsuario() == a.getIdUsuario());
                    } else if (elem instanceof ParIdUsuarioIdCurso) {
                        ParIdUsuarioIdCurso e = (ParIdUsuarioIdCurso) elem;
                        ParIdUsuarioIdCurso a = (ParIdUsuarioIdCurso) atual;
                        adicionar = (e.getIdUsuario() == a.getIdUsuario());
                    }

                    if (adicionar) {
                        lista.add(atual);
                    } else {
                        // Como a árvore é ordenada, ao encontrar um registro que não satisfaz,
                        // podemos interromper a coleta nesta página e nas seguintes
                        continuar = false;
                        break;
                    }
                }

                paginaAtual = pa.proxima;
            }

            return lista;
        }

        // Não é folha: continua descendo
        if (elem == null || i == pa.elementos.size() || elem.compareTo(pa.elementos.get(i)) <= 0) {
            return read1(elem, pa.filhos.get(i));
        } else {
            return read1(elem, pa.filhos.get(i + 1));
        }
    }

    public boolean create(T elem) throws Exception {

        arquivo.seek(0);
        long pagina;
        pagina = arquivo.readLong();

        elemAux = elem.clone();

        paginaAux = -1;
        cresceu = false;

        boolean inserido = create1(pagina);

        if (cresceu) {

            Pagina novaPagina = new Pagina(construtor, ordem);
            novaPagina.elementos = new ArrayList<>(this.maxElementos);
            novaPagina.elementos.add(elemAux);
            novaPagina.filhos = new ArrayList<>(this.maxFilhos);
            novaPagina.filhos.add(pagina);
            novaPagina.filhos.add(paginaAux);

            arquivo.seek(8);
            long end = arquivo.readLong();
            if(end==-1) {
                end = arquivo.length();
            } else {
                arquivo.seek(end);
                Pagina pa_excluida = new Pagina(construtor, ordem);
                byte[] buffer = new byte[pa_excluida.TAMANHO_PAGINA];
                arquivo.read(buffer);
                pa_excluida.fromByteArray(buffer);
                arquivo.seek(8);
                arquivo.writeLong(pa_excluida.proxima);
            }
            arquivo.seek(end);
            long raiz = arquivo.getFilePointer();
            arquivo.write(novaPagina.toByteArray());
            arquivo.seek(0);
            arquivo.writeLong(raiz);
            inserido = true;
        }

        return inserido;
    }

    private boolean create1(long pagina) throws Exception {

        if (pagina == -1) {
            cresceu = true;
            paginaAux = -1;
            return false;
        }

        arquivo.seek(pagina);
        Pagina pa = new Pagina(construtor, ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.fromByteArray(buffer);

        int i = 0;
        while (i < pa.elementos.size() && (elemAux.compareTo(pa.elementos.get(i)) > 0)) {
            i++;
        }

        if (i < pa.elementos.size() && pa.filhos.get(0) == -1 && elemAux.compareTo(pa.elementos.get(i)) == 0) {
            cresceu = false;
            return false;
        }

        boolean inserido;
        if (i == pa.elementos.size() || elemAux.compareTo(pa.elementos.get(i)) < 0)
            inserido = create1(pa.filhos.get(i));
        else
            inserido = create1(pa.filhos.get(i + 1));

        if (!cresceu)
            return inserido;

        if (pa.elementos.size() < maxElementos) {

            pa.elementos.add(i, elemAux);
            pa.filhos.add(i + 1, paginaAux);

            arquivo.seek(pagina);
            arquivo.write(pa.toByteArray());

            cresceu = false;
            return true;
        }

        Pagina np = new Pagina(construtor, ordem);

        int meio = maxElementos / 2;
        np.filhos.add(pa.filhos.get(meio));
        for (int j = 0; j < (maxElementos - meio); j++) {
            np.elementos.add(pa.elementos.remove(meio));
            np.filhos.add(pa.filhos.remove(meio + 1));
        }

        if (i <= meio) {
            pa.elementos.add(i, elemAux);
            pa.filhos.add(i + 1, paginaAux);

            if (pa.filhos.get(0) == -1)
                elemAux = np.elementos.get(0).clone();

            else {
                elemAux = pa.elementos.remove(pa.elementos.size() - 1);
                pa.filhos.remove(pa.filhos.size() - 1);
            }
        }
        // Caso 2 - Novo registro deve ficar na página da direita
        else {
            int j = maxElementos - meio;
            // CORREÇÃO: evita acessar índice -1
            while (j > 0 && elemAux.compareTo(np.elementos.get(j - 1)) < 0)
                j--;
            np.elementos.add(j, elemAux);
            np.filhos.add(j + 1, paginaAux);

            elemAux = np.elementos.get(0).clone();

            if (pa.filhos.get(0) != -1) {
                np.elementos.remove(0);
                np.filhos.remove(0);
            }
        }

        arquivo.seek(8);
        long end = arquivo.readLong();
        if(end==-1) {
            end = arquivo.length();
        } else {
            arquivo.seek(end);
            Pagina pa_excluida = new Pagina(construtor, ordem);
            buffer = new byte[pa_excluida.TAMANHO_PAGINA];
            arquivo.read(buffer);
            pa_excluida.fromByteArray(buffer);
            arquivo.seek(8);
            arquivo.writeLong(pa_excluida.proxima);
        }

        if (pa.filhos.get(0) == -1) {
            np.proxima = pa.proxima;
            pa.proxima = end;
        }

        paginaAux = end;
        arquivo.seek(paginaAux);
        arquivo.write(np.toByteArray());

        arquivo.seek(pagina);
        arquivo.write(pa.toByteArray());

        return true;
    }

    public boolean delete(T elem) throws Exception {

        arquivo.seek(0);
        long pagina;
        pagina = arquivo.readLong();

        diminuiu = false;

        boolean excluido = delete1(elem, pagina);

        if (excluido && diminuiu) {

            arquivo.seek(pagina);
            Pagina pa = new Pagina(construtor, ordem);
            byte[] buffer = new byte[pa.TAMANHO_PAGINA];
            arquivo.read(buffer);
            pa.fromByteArray(buffer);

            if (pa.elementos.size() == 0) {
                arquivo.seek(0);
                arquivo.writeLong(pa.filhos.get(0));

                arquivo.seek(8);
                long end = arquivo.readLong();
                pa.proxima = end;
                arquivo.seek(8);
                arquivo.writeLong(pagina);
                arquivo.seek(pagina);
                arquivo.write(pa.toByteArray());
            }
        }

        return excluido;
    }

    private boolean delete1(T elem, long pagina) throws Exception {

        boolean excluido = false;
        int diminuido;

        if (pagina == -1) {
            diminuiu = false;
            return false;
        }

        arquivo.seek(pagina);
        Pagina pa = new Pagina(construtor, ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.fromByteArray(buffer);

        int i = 0;
        while (i < pa.elementos.size() && elem.compareTo(pa.elementos.get(i)) > 0) {
            i++;
        }

        if (i < pa.elementos.size() && pa.filhos.get(0) == -1 && elem.compareTo(pa.elementos.get(i)) == 0) {

            pa.elementos.remove(i);
            pa.filhos.remove(i + 1);

            arquivo.seek(pagina);
            arquivo.write(pa.toByteArray());

            diminuiu = pa.elementos.size() < maxElementos / 2;
            return true;
        }

        if (i == pa.elementos.size() || elem.compareTo(pa.elementos.get(i)) < 0) {
            excluido = delete1(elem, pa.filhos.get(i));
            diminuido = i;
        } else {
            excluido = delete1(elem, pa.filhos.get(i + 1));
            diminuido = i + 1;
        }

        if (diminuiu) {

            long paginaFilho = pa.filhos.get(diminuido);
            Pagina pFilho = new Pagina(construtor, ordem);
            arquivo.seek(paginaFilho);
            arquivo.read(buffer);
            pFilho.fromByteArray(buffer);

            long paginaIrmaoEsq = -1, paginaIrmaoDir = -1;
            Pagina pIrmaoEsq = null, pIrmaoDir = null;

            if (diminuido > 0) {
                paginaIrmaoEsq = pa.filhos.get(diminuido - 1);
                pIrmaoEsq = new Pagina(construtor, ordem);
                arquivo.seek(paginaIrmaoEsq);
                arquivo.read(buffer);
                pIrmaoEsq.fromByteArray(buffer);
            }
            if (diminuido < pa.elementos.size()) {
                paginaIrmaoDir = pa.filhos.get(diminuido + 1);
                pIrmaoDir = new Pagina(construtor, ordem);
                arquivo.seek(paginaIrmaoDir);
                arquivo.read(buffer);
                pIrmaoDir.fromByteArray(buffer);
            }

            if (pIrmaoEsq != null && pIrmaoEsq.elementos.size() > maxElementos / 2) {

                if (pFilho.filhos.get(0) == -1)
                    pFilho.elementos.add(0, pIrmaoEsq.elementos.remove(pIrmaoEsq.elementos.size() - 1));

                else
                    pFilho.elementos.add(0, pa.elementos.get(diminuido - 1));

                pa.elementos.set(diminuido - 1, pFilho.elementos.get(0));

                pFilho.filhos.add(0, pIrmaoEsq.filhos.remove(pIrmaoEsq.filhos.size() - 1));

            }

            else if (pIrmaoDir != null && pIrmaoDir.elementos.size() > maxElementos / 2) {
                if (pFilho.filhos.get(0) == -1) {

                    pFilho.elementos.add(pIrmaoDir.elementos.remove(0));
                    pFilho.filhos.add(pIrmaoDir.filhos.remove(0));

                    pa.elementos.set(diminuido, pIrmaoDir.elementos.get(0));
                }

                else {
                    pFilho.elementos.add(pa.elementos.get(diminuido));
                    pFilho.filhos.add(pIrmaoDir.filhos.remove(0));

                    pa.elementos.set(diminuido, pIrmaoDir.elementos.remove(0));
                }
            }

            else if (pIrmaoEsq != null) {
                if (pFilho.filhos.get(0) != -1) {
                    pIrmaoEsq.elementos.add(pa.elementos.remove(diminuido - 1));
                    pIrmaoEsq.filhos.add(pFilho.filhos.remove(0));
                }
                else {
                    pa.elementos.remove(diminuido - 1);
                    pFilho.filhos.remove(0);
                }
                pa.filhos.remove(diminuido);

                pIrmaoEsq.elementos.addAll(pFilho.elementos);
                pIrmaoEsq.filhos.addAll(pFilho.filhos);
                pFilho.elementos.clear(); 
                pFilho.filhos.clear();

                if (pIrmaoEsq.filhos.get(0) == -1)
                    pIrmaoEsq.proxima = pFilho.proxima;

                arquivo.seek(8);
                pFilho.proxima = arquivo.readLong();
                arquivo.seek(8);
                arquivo.writeLong(paginaFilho);

            }

            else {
                if (pFilho.filhos.get(0) != -1) {
                    pFilho.elementos.add(pa.elementos.remove(diminuido));
                    pFilho.filhos.add(pIrmaoDir.filhos.remove(0));
                }
                else {
                    pa.elementos.remove(diminuido);
                    pFilho.filhos.remove(0);
                }
                pa.filhos.remove(diminuido + 1);

                pFilho.elementos.addAll(pIrmaoDir.elementos);
                pFilho.filhos.addAll(pIrmaoDir.filhos);
                pIrmaoDir.elementos.clear(); 
                pIrmaoDir.filhos.clear();

                pFilho.proxima = pIrmaoDir.proxima;

                arquivo.seek(8);
                pIrmaoDir.proxima = arquivo.readLong();
                arquivo.seek(8);
                arquivo.writeLong(paginaIrmaoDir);

            }

            diminuiu = pa.elementos.size() < maxElementos / 2;

            arquivo.seek(pagina);
            arquivo.write(pa.toByteArray());
            arquivo.seek(paginaFilho);
            arquivo.write(pFilho.toByteArray());
            if (pIrmaoEsq != null) {
                arquivo.seek(paginaIrmaoEsq);
                arquivo.write(pIrmaoEsq.toByteArray());
            }
            if (pIrmaoDir != null) {
                arquivo.seek(paginaIrmaoDir);
                arquivo.write(pIrmaoDir.toByteArray());
            }
        }
        return excluido;
    }

    public void print() throws Exception {
        long raiz;
        arquivo.seek(0);
        raiz = arquivo.readLong();
        System.out.println("Raiz: " + String.format("%04d", raiz));
        if (raiz != -1)
            print1(raiz);
        System.out.println();
    }

    private void print1(long pagina) throws Exception {

        if (pagina == -1)
            return;
        int i;

        arquivo.seek(pagina);
        Pagina pa = new Pagina(construtor, ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.fromByteArray(buffer);

        String endereco = String.format("%04d", pagina);
        System.out.print(endereco + "  " + pa.elementos.size() + ":");
        for (i = 0; i < pa.elementos.size(); i++) {
            System.out.print("(" + String.format("%04d", pa.filhos.get(i)) + ") " + pa.elementos.get(i) + " ");
        }
        if (i > 0)
            System.out.print("(" + String.format("%04d", pa.filhos.get(i)) + ")");
        else
            System.out.print("(-001)");
        for (; i < maxElementos; i++) {
            System.out.print(" ------- (-001)");
        }
        if (pa.proxima == -1)
            System.out.println();
        else
            System.out.println(" --> (" + String.format("%04d", pa.proxima) + ")");

        if (pa.filhos.get(0) != -1) {
            for (i = 0; i < pa.elementos.size(); i++)
                print1(pa.filhos.get(i));
            print1(pa.filhos.get(i));
        }
    }
}