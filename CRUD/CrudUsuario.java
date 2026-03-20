package CRUD;
import Entidades.Usuario;
import Genericos.*;

public class CrudUsuario extends Genericos.Arquivo<Usuario> {

    HashExtensivel<ParEmailID> indiceIndiretoEmail;

    public CrudUsuario() throws Exception {
        super("usuarios", Usuario.class.getConstructor());
        indiceIndiretoEmail = new HashExtensivel<>(
            ParEmailID.class.getConstructor(), 
            4, 
            ".\\dados\\usuarios\\indiceEmail.d.db",   // diretório
            ".\\dados\\usuarios\\indiceEmail.c.db"    // cestos 
        );
    }

    @Override
    public int create(Usuario c) throws Exception {
        int id = super.create(c);
        indiceIndiretoEmail.create(new ParEmailID(c.getEmail(), id));
        return id;
    }

    public Usuario read(String email) throws Exception {
        ParEmailID pei = indiceIndiretoEmail.read(ParEmailID.hash(email));
        if(pei == null)
            return null;
        return read(pei.getId());
    }
    
    public boolean delete(String email) throws Exception {
        ParEmailID pei = indiceIndiretoEmail.read(ParEmailID.hash(email));
        if(pei != null) 
            if(delete(pei.getId())) 
                return indiceIndiretoEmail.delete(ParEmailID.hash(email));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Usuario c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return indiceIndiretoEmail.delete(ParEmailID.hash(c.getEmail()));
        }
        return false;
    }

    @Override
    public boolean update(Usuario novoUsuario) throws Exception {
        Usuario usuarioVelho = read(novoUsuario.getId());

        if(super.update(novoUsuario)) {
            if(novoUsuario.getEmail().compareTo(usuarioVelho.getEmail())!=0) {
                indiceIndiretoEmail.delete(ParEmailID.hash(usuarioVelho.getEmail()));
                indiceIndiretoEmail.create(new ParEmailID(novoUsuario.getEmail(), novoUsuario.getId()));
            }
            return true;
        }
        return false;
    }
}
