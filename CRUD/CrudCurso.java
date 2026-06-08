package CRUD;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;

import Entidades.Curso;
import Genericos.ArvoreBMais;
import Genericos.HashExtensivel;
import Genericos.ParCodigoID;
import Genericos.ParIdUsuarioIdCurso;
import Genericos.ParUsuarioNomeCursoId;
import IndiceInvertido.ElementoLista;
import IndiceInvertido.ListaInvertida;
import IndiceInvertido.PreProcessamento;

public class CrudCurso extends Genericos.Arquivo<Curso> {

    HashExtensivel<ParCodigoID> indiceIndiretoCodigo;
    ArvoreBMais<ParIdUsuarioIdCurso> arvoreUsuarioCurso;
    ArvoreBMais<ParUsuarioNomeCursoId> arvoreUsuarioNome;
    ListaInvertida listaInvertida;
    PreProcessamento preProcessamento;

    /**
     * 
     * Construtor da classe CrudCurso
     * 
     */
    public CrudCurso() throws Exception {
        super("cursos", Curso.class.getConstructor());
        indiceIndiretoCodigo = new HashExtensivel<>(
                ParCodigoID.class.getConstructor(),
                4,
                "." + File.separator + "dados" + File.separator + "cursos" + File.separator + "indiceCodigo.d.db", // diretório
                "." + File.separator + "dados" + File.separator + "cursos" + File.separator + "indiceCodigo.c.db" // cestos
        );
        arvoreUsuarioCurso = new ArvoreBMais<>(
                ParIdUsuarioIdCurso.class.getConstructor(),
                4,
                "." + File.separator + "dados" + File.separator + "cursos" + File.separator
                        + "arvoreUsuarioCurso.d.db");
        arvoreUsuarioNome = new ArvoreBMais<>(
                ParUsuarioNomeCursoId.class.getConstructor(),
                4,
                "." + File.separator + "dados" + File.separator + "cursos" + File.separator + "arvoreUsuarioNome.d.db");
        listaInvertida = new ListaInvertida(
                4,
                "." + File.separator + "dados" + File.separator + "cursos" + File.separator + "indiceInvertido.dicionario.db",
                "." + File.separator + "dados" + File.separator + "cursos" + File.separator + "indiceInvertido.blocos.db");
        preProcessamento = new PreProcessamento();
    }

    /**
     * Calcula o TF (Term Frequency) de cada termo do nome.
     * TF = ocorrências do termo / total de termos.
     */
    private Map<String, Float> calcularTF(String nome) {
        List<String> termos = preProcessamento.preProccessString(nome);
        Map<String, Float> tf = new HashMap<>();
        for (String t : termos) {
            tf.put(t, tf.getOrDefault(t, 0f) + 1f);
        }
        // Normaliza: freq / total de termos
        for (String t : tf.keySet()) {
            tf.put(t, tf.get(t) / termos.size());
        }
        return tf;
    }

    /**
     * 
     * Cria um novo curso no arquivo
     * 
     */
    @Override
    public int create(Curso c) throws Exception {
        int id = super.create(c);

        System.out.println("Curso criado com ID: " + id + " para o usuário ID: " + c.getIdUsuario());
        indiceIndiretoCodigo.create(new ParCodigoID(c.getCodigo(), id));
        arvoreUsuarioCurso.create(new ParIdUsuarioIdCurso(c.getIdUsuario(), id));
        arvoreUsuarioNome.create(new ParUsuarioNomeCursoId(c.getIdUsuario(), c.getNome(), id));

        // Insere termos do nome no índice invertido com TF
        Map<String, Float> tf = calcularTF(c.getNome());
        for (Map.Entry<String, Float> entry : tf.entrySet()) {
            listaInvertida.create(entry.getKey(), new ElementoLista(id, entry.getValue()));
        }
        listaInvertida.incrementaEntidades();

        return id;
    }

    /**
     * 
     * Le um curso pelo id
     * 
     */
    public Curso read(int id) throws Exception {
        return super.read(id);
    }

    /**
     * 
     * Le um curso pelo codigo
     * 
     */
    public Curso read(String codigo) throws Exception {
        ParCodigoID pci = indiceIndiretoCodigo.read(ParCodigoID.hash(codigo));
        if (pci == null)
            return null;
        return super.read(pci.getId());
    }

    /**
     * 
     * Lista todos os cursos de um usuario
     * 
     */
    public ArrayList<Curso> readAllByUsuario(int idUsuario) throws Exception {
        List<Curso> list = arvoreUsuarioCurso.read(new ParIdUsuarioIdCurso(idUsuario, Integer.MIN_VALUE)).stream()
                .map(p -> {
                    try {
                        return read(p.getIdCurso());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(c -> c != null)
                .collect(Collectors.toList());
        return new ArrayList<>(list);
    }
    

    /**
     * 
     * Lista cursos de um usuario ordenados por nome
     * 
     */
    public ArrayList<Curso> listarCursosUsuarioOrdenadoNome(int idUsuario) throws Exception {
        List<Curso> list = arvoreUsuarioNome.read(new ParUsuarioNomeCursoId(idUsuario, "", Integer.MIN_VALUE)).stream()
                .map(p -> {
                    try {
                        return read(p.getIdCurso());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(c -> c != null)
                .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Lista cursos de um usuario ordenados por dataInicio
     * 
     */
    public ArrayList<Curso> listarCursosUsuarioOrdenadoDataInicio(int idUsuario) throws Exception {
        List<Curso> list = super.readAll().stream()
                .filter(c -> c.getIdUsuario() == idUsuario)
                .sorted(Comparator.comparing(Curso::getDataInicio))
                .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Lista cursos ordenados por dataInicio
     * 
     */
    public ArrayList<Curso> listarCursosOrdenadoDataInicio() throws Exception {
        List<Curso> list = super.readAll().stream()
                .sorted(Comparator.comparing(Curso::getDataInicio))
                .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Deleta um curso pelo codigo
     * 
     */
    public boolean delete(String codigo) throws Exception {
        ParCodigoID pci = indiceIndiretoCodigo.read(ParCodigoID.hash(codigo));
        if (pci != null) {
            Curso c = read(pci.getId());
            if (c != null) {
                if (delete(pci.getId())) {
                    indiceIndiretoCodigo.delete(ParCodigoID.hash(codigo));
                    arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(c.getIdUsuario(), pci.getId()));
                    arvoreUsuarioNome.delete(new ParUsuarioNomeCursoId(c.getIdUsuario(), c.getNome(), pci.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * Deleta um curso pelo id
     * 
     */
    @Override
    public boolean delete(int id) throws Exception {
        Curso c = super.read(id);
        if (c != null) {
            if (super.delete(id)) {
                indiceIndiretoCodigo.delete(ParCodigoID.hash(c.getCodigo()));
                arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(c.getIdUsuario(), id));
                arvoreUsuarioNome.delete(new ParUsuarioNomeCursoId(c.getIdUsuario(), c.getNome(), id));

                // Remove termos do nome do índice invertido
                List<String> termos = preProcessamento.preProccessString(c.getNome());
                Set<String> termosUnicos = new HashSet<>(termos);
                for (String t : termosUnicos) {
                    listaInvertida.delete(t, id);
                }
                listaInvertida.decrementaEntidades();

                // Exclusão em cascata das inscrições
                CRUD.CrudCursoUsuario crudCursoUsuario = new CRUD.CrudCursoUsuario();
                ArrayList<Entidades.CursoUsuario> inscricoes = crudCursoUsuario.readAllByCurso(id);
                for (Entidades.CursoUsuario cu : inscricoes) {
                    crudCursoUsuario.delete(cu.getId());
                }

                return true;
            }
        }
        return false;
    }

    /**
     * 
     * Atualiza um curso
     * 
     */
    @Override
    public boolean update(Curso novoCurso) throws Exception {
        Curso cursoVelho = read(novoCurso.getId());

        if (super.update(novoCurso)) {
            // Verifica se o nome mudou para atualizar o índice invertido
            boolean nomeMudou = novoCurso.getNome().compareTo(cursoVelho.getNome()) != 0;

            if (novoCurso.getCodigo().compareTo(cursoVelho.getCodigo()) != 0) {
                indiceIndiretoCodigo.delete(ParCodigoID.hash(cursoVelho.getCodigo()));
                indiceIndiretoCodigo.create(new ParCodigoID(novoCurso.getCodigo(), novoCurso.getId()));
                arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(cursoVelho.getIdUsuario(), cursoVelho.getId()));
                arvoreUsuarioCurso.create(new ParIdUsuarioIdCurso(novoCurso.getIdUsuario(), novoCurso.getId()));
                arvoreUsuarioNome.delete(
                        new ParUsuarioNomeCursoId(cursoVelho.getIdUsuario(), cursoVelho.getNome(), cursoVelho.getId()));
                arvoreUsuarioNome.create(
                        new ParUsuarioNomeCursoId(novoCurso.getIdUsuario(), novoCurso.getNome(), novoCurso.getId()));
            } else if (novoCurso.getIdUsuario() != cursoVelho.getIdUsuario()) {
                arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(cursoVelho.getIdUsuario(), cursoVelho.getId()));
                arvoreUsuarioCurso.create(new ParIdUsuarioIdCurso(novoCurso.getIdUsuario(), novoCurso.getId()));
                arvoreUsuarioNome.delete(
                        new ParUsuarioNomeCursoId(cursoVelho.getIdUsuario(), cursoVelho.getNome(), cursoVelho.getId()));
                arvoreUsuarioNome.create(
                        new ParUsuarioNomeCursoId(novoCurso.getIdUsuario(), novoCurso.getNome(), novoCurso.getId()));
            } else if (nomeMudou) {
                arvoreUsuarioNome.delete(
                        new ParUsuarioNomeCursoId(cursoVelho.getIdUsuario(), cursoVelho.getNome(), cursoVelho.getId()));
                arvoreUsuarioNome.create(
                        new ParUsuarioNomeCursoId(novoCurso.getIdUsuario(), novoCurso.getNome(), novoCurso.getId()));
            }

            // Atualiza o índice invertido se o nome mudou
            if (nomeMudou) {
                // Remove termos antigos do índice invertido
                List<String> termosAntigos = preProcessamento.preProccessString(cursoVelho.getNome());
                Set<String> termosAntigosUnicos = new HashSet<>(termosAntigos);
                for (String t : termosAntigosUnicos) {
                    listaInvertida.delete(t, novoCurso.getId());
                }

                // Insere termos novos no índice invertido com TF
                Map<String, Float> tfNovo = calcularTF(novoCurso.getNome());
                for (Map.Entry<String, Float> entry : tfNovo.entrySet()) {
                    listaInvertida.create(entry.getKey(), new ElementoLista(novoCurso.getId(), entry.getValue()));
                }
            }

            return true;
        }
        return false;
    }

    /**
     * 
     * Lista cursos de um usuario ordenados por nome
     * 
     */
    public ArrayList<Curso> listarPorUsuarioOrdenadoNome(int idUsuario) throws Exception {
        List<Curso> list = arvoreUsuarioNome.read(new ParUsuarioNomeCursoId(idUsuario, "", Integer.MIN_VALUE)).stream()
                .map(p -> {
                    try {
                        return read(p.getIdCurso());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(c -> c != null)
                .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Lista cursos de um usuario
     * 
     */
    public ArrayList<Curso> listarPorUsuario(int idUsuario) throws Exception {
        List<Curso> list = arvoreUsuarioCurso.read(new ParIdUsuarioIdCurso(idUsuario, Integer.MIN_VALUE)).stream()
                .map(p -> {
                    try {
                        return read(p.getIdCurso());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(c -> c != null)
                .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Abre um curso para inscricoes
     * 
     */
    boolean abrirCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if (c != null) {
            c.setEstado('0');
            return update(c);
        }
        return false;
    }

    /**
     * 
     * Encerra inscricoes de um curso
     * 
     */
    boolean encerrarIncricoes(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if (c != null) {
            c.setEstado('1');
            return update(c);
        }
        return false;
    }

    /**
     * 
     * Encerra um curso
     * 
     */
    boolean encerrarCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if (c != null) {
            c.setEstado('2');
            return update(c);
        }
        return false;
    }

    /**
     * 
     * Cancela um curso
     * 
     */
    boolean cancelarCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if (c != null) {
            c.setEstado('3');
            return update(c);
        }
        return false;
    }

    /**
     * Imprime o conteúdo do índice invertido no console (para testes).
     */
    public void printIndiceInvertido() throws Exception {
        System.out.println("\n=== ÍNDICE INVERTIDO DE CURSOS ===");
        System.out.println("Total de entidades cadastradas: " + listaInvertida.numeroEntidades());
        listaInvertida.print();
        System.out.println("=================================\n");
    }

    /**
     * Classe auxiliar para o cálculo do Ranking TF-IDF.
     * Implementa Comparable para organizar a lista de maior para o menor Score.
     */
    private class CursoScore implements Comparable<CursoScore> {
        int idCurso;
        float score;

        public CursoScore(int idCurso, float score) {
            this.idCurso = idCurso;
            this.score = score;
        }

        @Override
        public int compareTo(CursoScore outro) {
            return Float.compare(outro.score, this.score);
        }
    }

    /**
     * Aciona a busca TF×IDF recebendo uma string de pesquisa.
     */
    public ArrayList<Curso> buscarPorPalavrasChave(String busca) throws Exception {
        List<String> termosBusca = preProcessamento.preProccessString(busca);
        
        if (termosBusca == null || termosBusca.isEmpty()) {
            return new ArrayList<>(); // Retorna vazio se digitarem stop words
        }

        // Obtém total de cursos para o cálculo do IDF
        int totalCursos = listaInvertida.numeroEntidades();
        if (totalCursos == 0) return new ArrayList<>();

        // Mapa para Combinar listas de múltiplos termos somando scores de mesmo ID
        Map<Integer, Float> mapaScores = new HashMap<>();

        // Transforma em Set para evitar calcular o mesmo termo duas vezes se o usuário repetiu na busca
        Set<String> termosUnicos = new HashSet<>(termosBusca);

        for (String termo : termosUnicos) {
            // Puxa do índice invertido em quais cursos este termo aparece
            ElementoLista[] ocorrencias = listaInvertida.read(termo);
            
            int docFreq = ocorrencias.length; 
            
            if (docFreq > 0) {
                // Cálculo de IDF: log(total_cursos / doc_freq) + 1
                float idf = (float) (Math.log10((double) totalCursos / docFreq) + 1);

                for (ElementoLista ocorrencia : ocorrencias) {
                    float tf = ocorrencia.getFrequencia(); 
                    
                    // Cálculo de TF×IDF para o termo atual
                    float tfIdf = tf * idf;
                    int idCurso = ocorrencia.getId();
                    
                    // Somar scores de um mesmo curso se a busca tem múltiplos termos
                    mapaScores.put(idCurso, mapaScores.getOrDefault(idCurso, 0f) + tfIdf);
                }
            }
        }

        // Transfere tudo do mapa para uma Lista para podermos ordenar
        List<CursoScore> listaScores = new ArrayList<>();
        for (Map.Entry<Integer, Float> entry : mapaScores.entrySet()) {
            listaScores.add(new CursoScore(entry.getKey(), entry.getValue()));
        }
        
        // Ordena resultado final por score decrescente
        Collections.sort(listaScores); 

        // Agora transformamos essa lista de IDs e Scores na lista de Objetos Curso real
        ArrayList<Curso> cursosRanqueados = new ArrayList<>();
        for (CursoScore cs : listaScores) {
            Curso c = this.read(cs.idCurso); 
            // Garante que não vai recomendar cursos cancelados
            if (c != null && c.getEstado() != '3') { 
                cursosRanqueados.add(c);
            }
        }

        return cursosRanqueados;
    }
}
