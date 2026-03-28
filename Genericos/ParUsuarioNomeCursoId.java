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

public class ParUsuarioNomeCursoId implements Genericos.RegistroArvoreBMais<ParUsuarioNomeCursoId> {

  private int idUsuario;
  private String nomeCurso;
  private int idCurso;
  private short TAMANHO = 44;

  public ParUsuarioNomeCursoId() {
    this(0, "", 0);
  }

  public ParUsuarioNomeCursoId(int u, String nc, int i) {
    try {
      this.idUsuario = u;
      this.nomeCurso = nc;
      this.idCurso = i;
      if (nc.getBytes().length + 8 > TAMANHO)
        throw new Exception("Número de caracteres do usuário ou do curso maior que o permitido. Os dados serão cortados.");
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  public short size() {
    return this.TAMANHO;
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(idUsuario);
    dos.writeInt(idCurso);
    dos.write(nomeCurso.getBytes());
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.idUsuario = dis.readInt();
    this.idCurso = dis.readInt();
    byte[] nomeBytes = new byte[TAMANHO - 8];
    dis.read(nomeBytes);
    this.nomeCurso = new String(nomeBytes).trim();
  }

  public int compareTo(ParUsuarioNomeCursoId obj) {
    int cmp = Integer.compare(this.idUsuario, obj.idUsuario);
    if (cmp != 0) return cmp;
    cmp = this.nomeCurso.compareTo(obj.nomeCurso);
    if (cmp != 0) return cmp;
    return Integer.compare(this.idCurso, obj.idCurso);
  }

  public ParUsuarioNomeCursoId clone() {
    return new ParUsuarioNomeCursoId(this.idUsuario, this.nomeCurso, this.idCurso);
  }
}