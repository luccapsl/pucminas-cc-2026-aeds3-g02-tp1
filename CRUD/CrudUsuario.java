package CRUD;

import java.io.File;

import Entidades.Usuario;
import Genericos.HashExtensivel;
import Genericos.ParEmailID;

public class CrudUsuario extends Genericos.Arquivo<Usuario> {

    HashExtensivel<ParEmailID> indiceIndiretoEmail;

    /**
     * 
     * Construtor da classe CrudUsuario
     * 
     */
    public CrudUsuario() throws Exception {
        super("usuarios", Usuario.class.getConstructor());
        indiceIndiretoEmail = new HashExtensivel<>(
                ParEmailID.class.getConstructor(),
                4,
                "." + File.separator + "dados" + File.separator + "usuarios" + File.separator + "indiceEmail.d.db", // diretório
                "." + File.separator + "dados" + File.separator + "usuarios" + File.separator + "indiceEmail.c.db" // cestos
        );
    }

    /**
     * 
     * Cria um novo usuario no arquivo
     * 
     */
    @Override
    public int create(Usuario c) throws Exception {
        int id = super.create(c);
        indiceIndiretoEmail.create(new ParEmailID(c.getEmail(), id));
        return id;
    }

    /**
     * 
     * Le um usuario pelo email
     * 
     */
    public Usuario read(String email) throws Exception {
        ParEmailID pei = indiceIndiretoEmail.read(ParEmailID.hash(email));
        if (pei == null)
            return null;
        return read(pei.getId());
    }

    /**
     * 
     * Deleta um usuario pelo email
     * 
     */
    public boolean delete(String email) throws Exception {
        ParEmailID pei = indiceIndiretoEmail.read(ParEmailID.hash(email));
        if (pei != null)
            if (delete(pei.getId()))
                return indiceIndiretoEmail.delete(ParEmailID.hash(email));
        return false;
    }

    /**
     * 
     * Deleta um usuario pelo id
     * 
     */
    @Override
    public boolean delete(int id) throws Exception {
        Usuario c = super.read(id);
        if (c != null) {
            if (super.delete(id))
                return indiceIndiretoEmail.delete(ParEmailID.hash(c.getEmail()));
        }
        return false;
    }

    /**
     * 
     * Atualiza um usuario
     * 
     */
    @Override
    public boolean update(Usuario novoUsuario) throws Exception {
        Usuario usuarioVelho = read(novoUsuario.getId());

        if (super.update(novoUsuario)) {
            if (novoUsuario.getEmail().compareTo(usuarioVelho.getEmail()) != 0) {
                indiceIndiretoEmail.delete(ParEmailID.hash(usuarioVelho.getEmail()));
                indiceIndiretoEmail.create(new ParEmailID(novoUsuario.getEmail(), novoUsuario.getId()));
            }
            return true;
        }
        return false;
    }
}
