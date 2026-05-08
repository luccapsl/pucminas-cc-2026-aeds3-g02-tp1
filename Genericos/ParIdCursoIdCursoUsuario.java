package Genericos;

/*

Esta classe representa um PAR CHAVE VALOR (PCV)
para o índice da árvore B+ do relacionamento N:N entre Curso e CursoUsuario.

Um índice de relacionamento contém um int para o idCurso
e um int para o idCursoUsuario.

*/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIdCursoIdCursoUsuario implements Genericos.RegistroArvoreBMais<ParIdCursoIdCursoUsuario> {
    private int idCurso;
    private int idCursoUsuario;

    public ParIdCursoIdCursoUsuario() {
        this(0, 0);
    }

    public ParIdCursoIdCursoUsuario(int idCurso, int idCursoUsuario) {
        this.idCurso = idCurso;
        this.idCursoUsuario = idCursoUsuario;
    }

    public short size() {
        return 8; // tamanho fixo do registro (4 bytes para cada inteiro)
    }

    /**
     * 
     * Converte os atributos do Objeto para um array de bytes
     *
     * @return byte[] contendo os dados do objeto em bytes
     * @throws IOException se ocorrer erro ao escrever os dados no array
     */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(idCurso);
        dos.writeInt(idCursoUsuario);
        return baos.toByteArray();
    }

    /**
     * 
     * Converte o array de bytes enviado para atributos do Objeto
     *
     * @param byte[] ba contendo os dados do objeto em bytes
     * @throws IOException se ocorrer erro ao ler os dados do array
     */
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idCurso = dis.readInt();
        this.idCursoUsuario = dis.readInt();
    }

    /**
     * 
     * Compara dois objetos
     *
     * @param obj o objeto a comparar
     * @return int resultado da comparacao
     */
    @Override
    public int compareTo(ParIdCursoIdCursoUsuario obj) {
        if (this.idCurso != obj.idCurso) {
            return Integer.compare(this.idCurso, obj.idCurso);
        } else {
            return Integer.compare(this.idCursoUsuario, obj.idCursoUsuario);
        }
    }

    /**
     * 
     * Clona o objeto
     *
     * @return ParIdCursoIdCursoUsuario o clone
     */
    public ParIdCursoIdCursoUsuario clone() {
        return new ParIdCursoIdCursoUsuario(this.idCurso, this.idCursoUsuario);
    }

    /**
     * 
     * Retorna o idCurso
     *
     * @return int o idCurso
     */
    public int getIdCurso() {
        return this.idCurso;
    }

    /**
     * 
     * Retorna o idCursoUsuario
     *
     * @return int o idCursoUsuario
     */
    public int getIdCursoUsuario() {
        return this.idCursoUsuario;
    }
}
