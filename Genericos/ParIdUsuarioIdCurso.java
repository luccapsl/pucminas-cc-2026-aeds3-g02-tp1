/*

Esta classe representa um PAR CHAVE VALOR (PCV) 
para uma entidade Pessoa. Seu objetivo é representar
uma entrada de índice. 

Esse índice será secundário e indireto, baseado no
email de uma pessoa. Ao fazermos a busca por pessoa,
ele retornará o ID dessa pessoa, para que esse ID
possa ser buscado em um índice direto (que não é
apresentado neste projeto)

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

public class ParIdUsuarioIdCurso implements aed3.RegistroHashExtensivel<ParIdUsuarioIdCurso> {

  private int idUsuario;
  private int idCurso;
  private short TAMANHO = 8;

  public ParIdUsuarioIdCurso() {
    this(-1, -1);
  }

  public ParIdUsuarioIdCurso(int idUsuario, int idCurso) {
    try {
      this.idUsuario = idUsuario;
      this.idCurso = idCurso;
      if (idUsuario < 0 || idCurso < 0)
        throw new Exception("ID do usuário ou do curso inválido.");
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  @Override
  public int hashCode() {
    return Math.abs(this.idUsuario * 31 + this.idCurso);
  }

  public short size() {
    return this.TAMANHO;
  }

  public String toString() {
    return this.idUsuario + ";" + this.idCurso;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(idUsuario);
    dos.writeInt(idCurso);
    byte[] bs = baos.toByteArray();
    byte[] bs2 = new byte[TAMANHO];
    for (int i = 0; i < TAMANHO; i++)
      bs2[i] = ' ';
    for (int i = 0; i < bs.length && i < TAMANHO; i++)
      bs2[i] = bs[i];
    return bs2;
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.idUsuario = dis.readInt();
    this.idCurso = dis.readInt();
  }

  public static int hash(int idUsuario, int idCurso) {
    return Math.abs(idUsuario * 31 + idCurso);
  }
}