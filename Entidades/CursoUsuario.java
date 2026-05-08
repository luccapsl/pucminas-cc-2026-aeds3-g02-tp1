package Entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Genericos.Registro;

public class CursoUsuario implements Registro {

    private int idCursoUsuario;
    private int idCurso;
    private int idUsuario;
    private Date dataInscricao;

    /**
     * 
     * Construtor vazio para Arquivo.java
     * 
     */
    public CursoUsuario() {
        this.idCursoUsuario = -1;
        this.idCurso = -1;
        this.idUsuario = -1;
        this.dataInscricao = new Date();
    }

    /**
     * 
     * Construtor padrão
     * 
     */
    public CursoUsuario(int idCurso, int idUsuario, Date dataInscricao) {
        this.idCursoUsuario = -1;
        this.idCurso = idCurso;
        this.idUsuario = idUsuario;
        this.dataInscricao = dataInscricao;
    }

    // ===== Manipuladores de Arquivo =====

    /**
     * Converte os atributos do Objeto para um array de bytes
     *
     * @return byte[] contendo os dados do objeto em bytes
     * @throws IOException se ocorrer erro ao escrever os dados no array
     */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.idCursoUsuario);
        dos.writeInt(this.idCurso);
        dos.writeInt(this.idUsuario);
        dos.writeLong(this.dataInscricao.getTime());

        return baos.toByteArray();
    }

    /**
     * Converte o array de bytes enviado para atributos do Objeto
     *
     * @param byte[] ba contendo os dados do objeto em bytes
     * @throws IOException se ocorrer erro ao ler os dados do array
     */
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.idCursoUsuario = dis.readInt();
        this.idCurso = dis.readInt();
        this.idUsuario = dis.readInt();
        this.dataInscricao = new Date(dis.readLong());
    }

    // ===== Getters =====

    @Override
    public int getId() {
        return this.idCursoUsuario;
    }

    public int getIdCurso() {
        return this.idCurso;
    }

    public int getIdUsuario() {
        return this.idUsuario;
    }

    public Date getDataInscricao() {
        return this.dataInscricao;
    }

    // ===== Setters =====

    @Override
    public void setId(int id) {
        this.idCursoUsuario = id;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setDataInscricao(Date dataInscricao) {
        if (dataInscricao == null) {
            throw new IllegalArgumentException("[ERRO] - Data de inscrição inválida.");
        }
        this.dataInscricao = dataInscricao;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "ID: " + this.idCursoUsuario + "\n" +
               "ID Curso: " + this.idCurso + "\n" +
               "ID Usuário: " + this.idUsuario + "\n" +
               "Data de Inscrição: " + sdf.format(this.dataInscricao);
    }

    public String toStringSafe() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "\nID...............: " + this.idCursoUsuario +
               "\nID Curso.........: " + this.idCurso +
               "\nID Usuário.......: " + this.idUsuario +
               "\nData Inscrição...: " + sdf.format(this.dataInscricao);
    }
}
