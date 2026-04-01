package Genericos;

/*

Esta classe representa um PAR CHAVE VALOR (PCV) 
para uma entidade Usuario. Seu objetivo e representar
uma entrada de indice. 

Esse indice sera secundario e indireto, baseado no
email de um usuario. Ao fazermos a busca por usuario,
ele retornara o ID desse usuario, para que esse ID
possa ser buscado em um indice direto (que nao e
apresentado neste projeto)

Um indice direto de ID precisaria ser criado por meio
de outra classe, cujos dados fossem um int para o ID
e um long para o endereco
 
Implementado pelo Prof. Marcos Kutova
v1.0 - 2021
 
*/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParEmailID implements Genericos.RegistroHashExtensivel<ParEmailID> {

  private String email;
  private int id;
  private short TAMANHO = 44;

  public ParEmailID() {
    this("", -1);
  }

  public ParEmailID(String e, int i) {
    try {
      this.email = e;
      this.id = i;
      if (e.getBytes().length + 4 > TAMANHO)
        throw new Exception("Número de caracteres do email maior que o permitido. Os dados serão cortados.");
    } catch (Exception ec) {
      ec.printStackTrace();
    }
  }

  @Override
  public int hashCode() {
    return Math.abs(this.email.hashCode());
  }

  public short size() {
    return this.TAMANHO;
  }

  public String toString() {
    return this.email + ";" + this.id;
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
    dos.writeUTF(email);
    dos.writeInt(id);
    byte[] bs = baos.toByteArray();
    byte[] bs2 = new byte[TAMANHO];
    for (int i = 0; i < TAMANHO; i++)
      bs2[i] = ' ';
    for (int i = 0; i < bs.length && i < TAMANHO; i++)
      bs2[i] = bs[i];
    return bs2;
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
    this.email = dis.readUTF();
    this.id = dis.readInt();
  }

  /**
   * 
   * Metodo estatico para calcular hash do email
   *
   * @param email o email para calcular o hash
   * @return int o valor do hash
   */
  public static int hash(String email) {
    return Math.abs(email.hashCode());
  }

  /**
   * 
   * Retorna o id
   *
   * @return int o id
   */
  public int getId() {
    return this.id;
  }

}