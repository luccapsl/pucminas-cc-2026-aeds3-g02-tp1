import java.io.*;
import java.util.Date;

public class Curso {
    protected int idCurso = -1;
    protected String nome;
    protected short codigo;
    protected char estado;
    protected int idUsuario;
    protected Date dataInicio;
    protected String descricao;

    public Curso(String nome, short codigo, char estado, int idUsuario, Date dataInicio, String descricao) {
        this.nome = nome;
        this.codigo = codigo;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.dataInicio = dataInicio;
        this.descricao = descricao;
        
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.idCurso = dis.readInt();
        this.nome = dis.readUTF();
        this.codigo = dis.readShort();
        this.estado = dis.readChar();
        this.idUsuario = dis.readInt();
        this.dataInicio = new Date(dis.readLong());
        this.descricao = dis.readUTF();
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.idCurso);
        dos.writeUTF(this.nome);
        dos.writeShort(this.codigo);
        dos.writeChar(this.estado);
        dos.writeInt(this.idUsuario);
        dos.writeLong(this.dataInicio.getTime());
        dos.writeUTF(this.descricao);

        return baos.toByteArray();
    }

    public short getCodigo() {
        return codigo;
    }

    public void setCodigo(short codigo) {
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
        this.estado = estado;
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