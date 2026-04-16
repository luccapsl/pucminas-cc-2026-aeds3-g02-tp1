package Entidades;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.soundicly.jnanoidenhanced.jnanoid.NanoIdUtils;

import Genericos.Registro;

public class Curso implements Registro {
    protected final int TAM_CODIGO = 10;
    protected int idCurso = -1;
    protected String nome;
    protected String codigo;
    protected char estado;
    protected int idUsuario;
    protected Date dataInicio;
    protected String descricao;
    /**
     * 
     * Construtor vazio para Arquivo.java
     * 
     */
    public Curso() {
        this.nome = "";
        this.codigo = this.genCodigo();
        this.estado = '0';
        this.idUsuario = -1;
        this.dataInicio = new Date();
        this.descricao = "";
    }

    /**
     * 
     * Construtor padrão
     * 
     */
    public Curso(String nome, char estado, Date dataInicio, String descricao, int idUsuario) {
        this.setNome(nome);

        this.codigo = this.genCodigo();

        this.idUsuario = idUsuario;

        this.setEstado(estado);

        this.setDataInicio(dataInicio);

        this.descricao = descricao;
    }

    /**
     * Gera o codigo NanoId de TAM_CODIGO caracteres
     *
     * @return String
     */
    protected String genCodigo() {
        return NanoIdUtils.randomNanoId(TAM_CODIGO);
    }

    /**
     * Converte o array de bytes enviado para atributos do Objeto
     *
     * @param byte[] ba contendo os dados do objeto em bytes
     * @return void
     * @throws IOException se ocorrer erro ao ler os dados do array
     */
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.idCurso = dis.readInt();
        this.nome = dis.readUTF();
        this.codigo = dis.readUTF();
        this.estado = dis.readChar();
        this.idUsuario = dis.readInt();
        this.dataInicio = new Date(dis.readLong());
        this.descricao = dis.readUTF();
    }

    /**
     * Converte os atributos do Objeto para um array de bytes
     *
     * @return byte[] contendo os dados do objeto em bytes
     * @throws IOException se ocorrer erro ao escrever os dados no array
     */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.idCurso);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.codigo);
        dos.writeChar(this.estado);
        dos.writeInt(this.idUsuario);
        dos.writeLong(this.dataInicio.getTime());
        dos.writeUTF(this.descricao);

        return baos.toByteArray();
    }

    // ================== //
    // Getters / Setters //
    // ================== //

    public String getCodigo() {
        return codigo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        if (dataInicio == null) {
            throw new IllegalArgumentException("[ERRO] - Data de início inválida.");
        } else {
            this.dataInicio = dataInicio;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        if (estado != '0' && estado != '1' && estado != '2' && estado != '3') {
            System.out.println("[WARNING] - Estado invalido. Setando estado 0 como padrão.");
            this.estado = '0';
        } else {
            this.estado = estado;
        }
    }

    @Override
    public int getId() {
        return idCurso;
    }

    @Override
    public void setId(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERRO] - Nome está vazio.");
        } else {
            this.nome = nome;
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "ID: " + this.idCurso + "\n" +
               "Nome: " + this.nome + "\n" +
               "Código: " + this.codigo + "\n" +
               "Estado: " + this.estado + "\n" +
               "ID do usuário: " + this.idUsuario + "\n" +
               "Data de início: " + sdf.format(this.dataInicio) + "\n" +
               "Descrição: " + this.descricao;
    }

    public String toStringSafe() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        EstadoCurso estadoCurso = EstadoCurso.fromCodigo(this.estado);
        String estadoStr = (estadoCurso != null) ? estadoCurso.getDescricao() : "Desconhecido";
        return "\nNome.........: " + this.nome +
               "\nCódigo.......: " + this.codigo +
               "\nEstado.......: " + estadoStr +
               "\nData início..: " + sdf.format(this.dataInicio) +
               "\nDescrição....: " + this.descricao;
    }

}