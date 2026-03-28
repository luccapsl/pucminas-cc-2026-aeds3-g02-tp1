/*

Esta classe representa um PAR CHAVE VALOR (PCV) 
para uma entidade Pessoa. Seu objetivo é representar
uma entrada de índice. 


Um índice direto de ID precisaria ser criado por meio
de outra classe, cujos dados fossem um int para o ID
e um long para o endereço
 
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

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(idUsuario);
        dos.writeInt(idCurso);
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idUsuario = dis.readInt();
        this.idCurso = dis.readInt();
    }

  public int compareTo(T obj) {
    ParIdUsuarioIdCurso outro = (ParIdUsuarioIdCurso) obj;
    if (this.idUsuario != outro.idUsuario) {
        return Integer.compare(this.idUsuario, outro.idUsuario);
    } else {
        return Integer.compare(this.idCurso, outro.idCurso);
    }
  }

  public ParIdUsuarioIdCurso clone() {
    return new ParIdUsuarioIdCurso(this.idUsuario, this.idCurso);
  }
}