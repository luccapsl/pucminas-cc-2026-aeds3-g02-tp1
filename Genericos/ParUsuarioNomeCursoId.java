package Genericos;

/*

Esta classe representa um PAR CHAVE VALOR (PCV) 
para uma entidade Usuario. Seu objetivo é representar
uma entrada de índice. 

Esse índice será secundário e indireto, baseado no
nome do curso de um usuario. Ao fazermos a busca por usuario,
ele retornará o ID desse usuario, para que esse ID
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

  /**
   * 
   * Construtor vazio
   *
   */
  public ParUsuarioNomeCursoId() {
    this(0, "", 0);
  }

  /**
   * 
   * Construtor padrao
   *
   * @param u o idUsuario
   * @param nc o nomeCurso
   * @param i o idCurso
   */
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

  /**
   * 
   * Retorna o tamanho do registro
   *
   * @return short o tamanho
   */
  public short size() {
    return this.TAMANHO;
  }

  /**
   * 
   * Converte os atributos do Objeto para um array de bytes
   *
   * @return byte[] contendo os dados do objeto em bytes
   * @throws IOException se ocorrer erro ao escrever os dados no array
   */
  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(idUsuario);
    dos.writeInt(idCurso);
    byte[] nomeBytes = new byte[TAMANHO - 8]; // preenche com zeros
    byte[] original = nomeCurso.getBytes();
    System.arraycopy(original, 0, nomeBytes, 0, Math.min(original.length, nomeBytes.length));
    dos.write(nomeBytes);
    return baos.toByteArray();
  }

  /**
   * 
   * Converte o array de bytes enviado para atributos do Objeto
   *
   * @param byte[] ba contendo os dados do objeto em bytes
   * @throws IOException se ocorrer erro ao ler os dados do array
   */
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.idUsuario = dis.readInt();
    this.idCurso = dis.readInt();
    byte[] nomeBytes = new byte[TAMANHO - 8];
    dis.read(nomeBytes);
    this.nomeCurso = new String(nomeBytes).trim();
  }

  /**
   * 
   * Compara dois objetos
   *
   * @param obj o objeto a comparar
   * @return int resultado da comparacao
   */
  public int compareTo(ParUsuarioNomeCursoId obj) {
    int cmp = Integer.compare(this.idUsuario, obj.idUsuario);
    if (cmp != 0) return cmp;
    cmp = this.nomeCurso.compareTo(obj.nomeCurso);
    if (cmp != 0) return cmp;
    return Integer.compare(this.idCurso, obj.idCurso);
  }

  /**
   * 
   * Clona o objeto
   *
   * @return ParUsuarioNomeCursoId o clone
   */
  public ParUsuarioNomeCursoId clone() {
    return new ParUsuarioNomeCursoId(this.idUsuario, this.nomeCurso, this.idCurso);
  }

  /**
   * 
   * Retorna o idUsuario
   *
   * @return int o idUsuario
   */
  public int getIdUsuario() {
    return this.idUsuario;
  }

  /**
   * 
   * Retorna o nomeCurso
   *
   * @return String o nomeCurso
   */
  public String getNomeCurso() {
    return this.nomeCurso;
  }

  /**
   * 
   * Retorna o idCurso
   *
   * @return int o idCurso
   */
  public int getIdCurso() {
    return this.idCurso;
  }

  /**
   * 
   * Retorna o id
   *
   * @return int o id
   */
  public int getId() {
    return this.idCurso;
  }
}