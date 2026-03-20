package Entidades;
import Genericos.Registro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Usuario implements Registro{
    
    private int id;
    private String nome;
    private String email;
    private String hashSenha;
    private String perguntaSecreta;
    private String respostaSecreta;

    public Usuario() {
        this.id = -1;
        this.nome = "";
        this.email = "";
        this.hashSenha = "";
        this.perguntaSecreta = "";
        this.respostaSecreta = "";
    }

    public Usuario (String nome, String email, String hashSenha, String perguntaSecreta, String respostaSecreta){
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha;
        this.perguntaSecreta = perguntaSecreta;
        this.respostaSecreta = respostaSecreta;
    }

    // ===== Getters =====
    public int getId(){
        return this.id;
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
               "\nNome......: " + this.nome +
               "\nEmail.....: " + this.email +
               "\nSenha.....: " + this.hashSenha +
               "\nPergunta..: " + this.perguntaSecreta +
               "\nResposta..: " + this.respostaSecreta;
    }

    // ===== Manipuladores de Arquivo =====
    
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.email);
        dos.writeUTF(this.hashSenha);
        dos.writeUTF(this.perguntaSecreta);
        dos.writeUTF(this.respostaSecreta);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
        this.hashSenha = dis.readUTF();
        this.perguntaSecreta = dis.readUTF();
        this.respostaSecreta = dis.readUTF();
    }
}
