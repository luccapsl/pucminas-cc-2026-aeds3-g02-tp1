package Genericos;

/*

Esta classe representa um PAR CHAVE VALOR (PCV) 
para uma entidade generica. Seu objetivo e representar
uma entrada de indice direto. 

Um indice direto de ID contem um int para o ID
e um long para o endereco
 
Implementado pelo Prof. Marcos Kutova
v1.0 - 2021
 
*/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ParIDEndereco implements Genericos.RegistroHashExtensivel<ParIDEndereco> {

  private int id;
  private long endereco;
  private short TAMANHO = 12;

  public ParIDEndereco() {
    this(-1, -1);
  }

  public ParIDEndereco(int i, long e) {
    this.id = i;
    this.endereco = e;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(this.id);
  }

  public short size() {
    return this.TAMANHO;
  }

  public String toString() {
    return "ID: " + this.id + " Endereço: " + this.endereco;
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
    dos.writeInt(id);
    dos.writeLong(endereco);
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
    this.id = dis.readInt();
    this.endereco = dis.readLong();
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

  /**
   * 
   * Retorna o endereco
   *
   * @return long o endereco
   */
  public long getEndereco() {
    return this.endereco;
  }
}