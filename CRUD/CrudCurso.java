package CRUD;
import Entidades.Curso;
import Genericos.*;
import java.util.*;

public class CrudCurso extends Genericos.Arquivo<Curso> {

    HashExtensivel<ParCodigoID> indiceIndiretoCodigo;
    ArvoreBMais<ParIdUsuarioIdCurso> arvoreUsuarioCurso;
    ArvoreBMais<ParUsuarioNomeCursoId> arvoreUsuarioNome;

    public CrudCurso() throws Exception {
        super("cursos", Curso.class.getConstructor());
        indiceIndiretoCodigo = new HashExtensivel<>(
            ParCodigoID.class.getConstructor(), 
            4, 
            ".\\dados\\cursos\\indiceCodigo.d.db",   // diretório
            ".\\dados\\cursos\\indiceCodigo.c.db"    // cestos 
        );
        arvoreUsuarioCurso = new ArvoreBMais<>(
            ParIdUsuarioIdCurso.class.getConstructor(), 
            4, 
            ".\\dados\\cursos\\arvoreUsuarioCurso.d.db"
        );
        arvoreUsuarioNome = new ArvoreBMais<>(
            ParUsuarioNomeCursoId.class.getConstructor(), 
            4, 
            ".\\dados\\cursos\\arvoreUsuarioNome.d.db"
        );
    }

    @Override
    public int create(Curso c) throws Exception {
        int id = super.create(c);
        indiceIndiretoCodigo.create(new ParCodigoID(c.getCodigo(), id));
        arvoreUsuarioCurso.create(new ParIdUsuarioIdCurso(c.getIdUsuario(), id));
        arvoreUsuarioNome.create(new ParUsuarioNomeCursoId(c.getNome(), c.getNome(), id));
        return id;
    }

    public Curso read(int id) throws Exception {
        return super.read(id);
    }

    public Curso read(String codigo) throws Exception {
        ParCodigoID pci = indiceIndiretoCodigo.read(ParCodigoID.hash(codigo));
        if(pci == null)
            return null;
        return read(pci.getId());
    }

    public ArrayList<Curso> readAllByUsuario(int idUsuario) throws Exception {
        return arvoreUsuarioCurso.read(idUsuario).stream()
            .map(p -> {
                try {
                    return read(p.getIdCurso());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(c -> c != null)
            .toList();
    }

    public ArrayList<Curso> listarCursosUsuarioOrdenadoNome(int idUsuario) throws Exception {
        return arvoreUsuarioNome.read(idUsuario).stream()
            .map(p -> {
                try {
                    return read(p.getIdCurso());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(c -> c != null)
            .toList();
    }
    
    public boolean delete(String codigo) throws Exception {
        ParCodigoID pci = indiceIndiretoCodigo.read(ParCodigoID.hash(codigo));
        if(pci != null) 
            if(delete(pci.getId())) 
                return indiceIndiretoCodigo.delete(ParCodigoID.hash(codigo));
        return false;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Curso c = super.read(id);
        if(c != null) {
            if(super.delete(id))
                return indiceIndiretoCodigo.delete(ParCodigoID.hash(c.getCodigo()));
        }
        return false;
    }
    

    @Override
    public boolean update(Curso novoCurso) throws Exception {
        Curso cursoVelho = read(novoCurso.getId());

        if(super.update(novoCurso)) {
            if(novoCurso.getCodigo().compareTo(cursoVelho.getCodigo())!=0) {
                indiceIndiretoCodigo.delete(ParCodigoID.hash(cursoVelho.getCodigo()));
                indiceIndiretoCodigo.create(new ParCodigoID(novoCurso.getCodigo(), novoCurso.getId()));
                arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(cursoVelho.getIdUsuario(), cursoVelho.getId()));
                arvoreUsuarioCurso.create(new ParIdUsuarioIdCurso(novoCurso.getIdUsuario(), novoCurso.getId()));
                arvoreUsuarioNome.delete(new ParUsuarioNomeCursoId(cursoVelho.getNome(), cursoVelho.getNome(), cursoVelho.getId()));
                arvoreUsuarioNome.create(new ParUsuarioNomeCursoId(novoCurso.getNome(), novoCurso.getNome(), novoCurso.getId()));
            }
            return true;
        }
        return false;
    }

    public ArrayList<Curso> listarPorUsuarioOrdenadoNome(int idUsuario) throws Exception {
        return arvoreUsuarioNome.read(idUsuario).stream()
            .map(p -> {
                try {
                    return read(p.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(c -> c != null)
            .toList();
    }

    public ArrayList<Curso> listarPorUsuario(int idUsuario) throws Exception {
        return arvoreUsuarioCurso.read(idUsuario).stream()
            .map(p -> {
                try {
                    return read(p.getIdCurso());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(c -> c != null)
            .toList();
    }

    boolean abrirCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if(c != null) {
            c.setEstado('0');
            return update(c);
        }
        return false;
    }

    boolean encerrarIncricoes(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if(c != null) {
            c.setEstado('1');
            return update(c);
        }
        return false;
    }

    boolean encerrarCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if(c != null) {
            c.setEstado('2');
            return update(c);
        }
        return false;
    }

    boolean cancelarCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if(c != null) {
            c.setEstado('3');
            return update(c);
        }
        return false;
    }
}
