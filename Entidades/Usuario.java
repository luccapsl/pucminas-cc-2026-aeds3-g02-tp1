package Entidades;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Genericos.Registro;

public class Usuario implements Registro{
    
    private int id;
    private int idCurso;
    private String nome;
    private String email;
    private String hashSenha;
    private String perguntaSecreta;
    private String respostaSecreta;

    /**
     * 
     * Construtor vazio para Arquivo.java
     * 
     */
    public Usuario() {
        this.id = -1;
        this.idCurso = -1;
        this.nome = "";
        this.email = "";
        this.hashSenha = "";
        this.perguntaSecreta = "";
        this.respostaSecreta = "";
    }

    /**
     * 
     * Construtor padrao
     * 
     */
    public Usuario (String nome, int idCurso, String email, String hashSenha, String perguntaSecreta, String respostaSecreta){
        this.nome = nome;
        this.idCurso = idCurso;
        this.email = email;
        this.hashSenha = hashSenha;
        this.perguntaSecreta = perguntaSecreta;
        this.respostaSecreta = respostaSecreta;
    }

    // ===== Getters =====
    public int getId(){
        return this.id;
    }

    public int getIdCurso(){
        return this.idCurso;
    }


    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }

    public String getSenha(){
        return this.hashSenha;
    }

    public String getPerguntaSecreta(){
        return this.perguntaSecreta;
    }

    public String getRespostaSecreta(){
        return this.respostaSecreta;
    }

    // ===== Setters =====
    public void setId(int id){
        this.id = id;
    }

    public void setIdCurso(int idCurso){
        this.idCurso = idCurso;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSenha(String senha){
        this.hashSenha = String.valueOf(senha.hashCode());
    }

    public void setPerguntaSecreta(String perguntaSecreta){
        this.perguntaSecreta = perguntaSecreta;
    }

    public void setRespostaSecreta(String respostaSecreta){
        this.respostaSecreta = respostaSecreta;
    }

    public String toString() {
        return "\nID........: " + this.id +
               "\nID Curso..: " + this.idCurso +
               "\nNome......: " + this.nome +
               "\nEmail.....: " + this.email +
               "\nSenha.....: " + this.hashSenha +
               "\nPergunta..: " + this.perguntaSecreta +
               "\nResposta..: " + this.respostaSecreta;
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

        dos.writeInt(this.id);
        dos.writeInt(this.idCurso);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.email);
        dos.writeUTF(this.hashSenha);
        dos.writeUTF(this.perguntaSecreta);
        dos.writeUTF(this.respostaSecreta);

        return baos.toByteArray();
    }

    /**
     * Converte o array de bytes enviado para atributos do Objeto
     *
     * @param byte[] ba contendo os dados do objeto em bytes
     * @throws IOException se ocorrer erro ao ler os dados do array
     */
    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.idCurso = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
        this.hashSenha = dis.readUTF();
        this.perguntaSecreta = dis.readUTF();
        this.respostaSecreta = dis.readUTF();
    }
}
