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

public class ParUsuarioNomeCursoId implements aed3.RegistroArvoreBMais<ParUsuarioNomeCursoId> {

  private String usuario;
  private String nomeCurso;
  private int id;
  private short TAMANHO = 44;

  public ParUsuarioNomeCursoId() {
    this("", "", -1);
  }

  public ParUsuarioNomeCursoId(String u, String nc, int i) {
    try {
      this.usuario = u;
      this.nomeCurso = nc;
      this.id = i;
      if (u.getBytes().length + nc.getBytes().length + 4 > TAMANHO)
        throw new Exception("Número de caracteres do usuário ou do curso maior que o permitido. Os dados serão cortados.");
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  @Override
  public int hashCode() {
    return Math.abs(this.usuario.hashCode() * 31 + this.nomeCurso.hashCode());
  }

  public short size() {
    return this.TAMANHO;
  }

  public String toString() {
    return this.usuario + ";" + this.nomeCurso + ";" + this.id;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeUTF(usuario);
    dos.writeUTF(nomeCurso);
    dos.writeInt(id);
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
    this.usuario = dis.readUTF();
    this.nomeCurso = dis.readUTF();
    this.id = dis.readInt();
  }

  public static int hash(String usuario, String nomeCurso) {
    return Math.abs(usuario.hashCode() * 31 + nomeCurso.hashCode());
  }

}