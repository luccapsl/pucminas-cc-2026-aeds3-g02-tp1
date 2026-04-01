package Genericos;

/*

Esta classe representa um PAR CHAVE VALOR (PCV) 
para uma entidade Usuario e Curso. Seu objetivo e representar
uma entrada de indice para arvore B+ do relacionamento 1:N.

Um indice de relacionamento contem um int para o idUsuario
e um int para o idCurso
 
Implementado pelo Prof. Marcos Kutova
v1.0 - 2021
 
*/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIdUsuarioIdCurso implements Genericos.RegistroArvoreBMais<ParIdUsuarioIdCurso> {
    private int idUsuario;
    private int idCurso;

    public ParIdUsuarioIdCurso() {
        this(0, 0);
    }

    public ParIdUsuarioIdCurso(int idUsuario, int idCurso) {
        this.idUsuario = idUsuario;
        this.idCurso = idCurso;
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
        dos.writeInt(idCurso);
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
        this.idCurso = dis.readInt();
    }

    /**
     * 
     * Compara dois objetos
     *
     * @param obj o objeto a comparar
     * @return int resultado da comparacao
     */
    @Override
    public int compareTo(ParIdUsuarioIdCurso obj) {
        ParIdUsuarioIdCurso outro = (ParIdUsuarioIdCurso) obj;
        if (this.idUsuario != outro.idUsuario) {
            return Integer.compare(this.idUsuario, outro.idUsuario);
        } else {
            return Integer.compare(this.idCurso, outro.idCurso);
        }
    }

    /**
     * 
     * Clona o objeto
     *
     * @return ParIdUsuarioIdCurso o clone
     */
    public ParIdUsuarioIdCurso clone() {
        return new ParIdUsuarioIdCurso(this.idUsuario, this.idCurso);
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
     * Retorna o idCurso
     *
     * @return int o idCurso
     */
    public int getIdCurso() {
        return this.idCurso;
    }
}