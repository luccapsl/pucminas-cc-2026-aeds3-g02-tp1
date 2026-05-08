package Genericos;

/*

Esta classe representa um PAR CHAVE VALOR (PCV)
para o índice da árvore B+ do relacionamento N:N entre Usuario e CursoUsuario.

Um índice de relacionamento contém um int para o idUsuario
e um int para o idCursoUsuario.

*/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIdUsuarioIdCursoUsuario implements Genericos.RegistroArvoreBMais<ParIdUsuarioIdCursoUsuario> {
    private int idUsuario;
    private int idCursoUsuario;

    public ParIdUsuarioIdCursoUsuario() {
        this(0, 0);
    }

    public ParIdUsuarioIdCursoUsuario(int idUsuario, int idCursoUsuario) {
        this.idUsuario = idUsuario;
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
        dos.writeInt(idUsuario);
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
        this.idUsuario = dis.readInt();
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
    public int compareTo(ParIdUsuarioIdCursoUsuario obj) {
        if (this.idUsuario != obj.idUsuario) {
            return Integer.compare(this.idUsuario, obj.idUsuario);
        } else {
            return Integer.compare(this.idCursoUsuario, obj.idCursoUsuario);
        }
    }

    /**
     * 
     * Clona o objeto
     *
     * @return ParIdUsuarioIdCursoUsuario o clone
     */
    public ParIdUsuarioIdCursoUsuario clone() {
        return new ParIdUsuarioIdCursoUsuario(this.idUsuario, this.idCursoUsuario);
    }

    /**
     * 
     * Retorna o idUsuario
     *
     * @return int o idUsuario
     */
    public int getIdUsuario() {
        return this.idUsuario;
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
