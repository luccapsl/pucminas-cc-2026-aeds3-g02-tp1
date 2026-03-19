package Entidades;

import java.io.*;
import java.util.Date;
import com.soundicly.jnanoidenhanced.jnanoid.NanoIdUtils;

public class Curso {
    protected final int TAM_CODIGO = 10;
    protected int idCurso = -1;
    protected String nome;
    protected String codigo;
    protected char estado;
    protected int idUsuario;
    protected Date dataInicio;
    protected String descricao;

    public Curso(String nome, char estado, int idUsuario, Date dataInicio, String descricao) {
        this.nome = nome;
        this.codigo = this.genCodigo();
        this.setEstado(estado);
        this.idUsuario = idUsuario;
        this.dataInicio = dataInicio;
        this.descricao = descricao;
        
    }

    public void listar() {
        System.out.println("("+idCurso+") "+nome+" - "+dataInicio);
    }

    public void printar() {
        System.out.println("NOME.........: " + nome);
        System.out.println("CODIGO.......: " + codigo);
        System.out.println("DESCRIÇÃO....: " + descricao);
        System.out.println("DATA INÍCIO..: " + dataInicio);
        System.out.print("ESTADO: ");
        
        switch (this.estado) {
            case ('0'):
                System.out.println("Ativo e recebendo inscrições");
                break;
            case ('1'):
                System.out.println("Ativo, mas sem novas inscrições");
                break;
            case ('2'):
                System.out.println("Concluído");
                break;
            case ('3'):
                System.out.println("Coancelado");
                break;
            default:
                System.out.println("[ERRO] - Estado de curso invalido");
                throw new AssertionError();
        }
        
    }

    /**
     * GEra o codigo NanoId de TAM_CODIGO caracteres
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
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
        if (estado < 0 || estado > 3) {
            System.out.println("[ERRO] - Estado invalido");
        } else {
            this.estado = estado;
        }
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
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
        this.nome = nome;
    }

}