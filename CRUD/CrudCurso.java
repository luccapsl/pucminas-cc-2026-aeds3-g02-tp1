package CRUD;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Entidades.Curso;
import Genericos.ArvoreBMais;
import Genericos.HashExtensivel;
import Genericos.ParCodigoID;
import Genericos.ParIdUsuarioIdCurso;
import Genericos.ParUsuarioNomeCursoId;

public class CrudCurso extends Genericos.Arquivo<Curso> {

    HashExtensivel<ParCodigoID> indiceIndiretoCodigo;
    ArvoreBMais<ParIdUsuarioIdCurso> arvoreUsuarioCurso;
    ArvoreBMais<ParUsuarioNomeCursoId> arvoreUsuarioNome;

    /**
     * 
     * Construtor da classe CrudCurso
     * 
     */
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

    /**
     * 
     * Cria um novo curso no arquivo
     * 
     */
    @Override
    public int create(Curso c) throws Exception {
        int id = super.create(c);
        indiceIndiretoCodigo.create(new ParCodigoID(c.getCodigo(), id));
        arvoreUsuarioCurso.create(new ParIdUsuarioIdCurso(c.getIdUsuario(), id));
        arvoreUsuarioNome.create(new ParUsuarioNomeCursoId(c.getIdUsuario(), c.getNome(), id));
        return id;
    }

    /**
     * 
     * Le um curso pelo id
     * 
     */
    public Curso read(int id) throws Exception {
        return super.read(id);
    }

    /**
     * 
     * Le um curso pelo codigo
     * 
     */
    public Curso read(String codigo) throws Exception {
        ParCodigoID pci = indiceIndiretoCodigo.read(ParCodigoID.hash(codigo));
        if(pci == null)
            return null;
        return read(pci.getId());
    }

    /**
     * 
     * Lista todos os cursos de um usuario
     * 
     */
    public ArrayList<Curso> readAllByUsuario(int idUsuario) throws Exception {
        List<Curso> list = arvoreUsuarioCurso.read(new ParIdUsuarioIdCurso(idUsuario, Integer.MIN_VALUE)).stream()
            .map(p -> {
                try {
                    return read(p.getIdCurso());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(c -> c != null)
            .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Lista cursos de um usuario ordenados por nome
     * 
     */
    public ArrayList<Curso> listarCursosUsuarioOrdenadoNome(int idUsuario) throws Exception {
        List<Curso> list = arvoreUsuarioNome.read(new ParUsuarioNomeCursoId(idUsuario, "", Integer.MIN_VALUE)).stream()
            .map(p -> {
                try {
                    return read(p.getIdCurso());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(c -> c != null)
            .collect(Collectors.toList());
        return new ArrayList<>(list);
    }
    
    /**
     * 
     * Deleta um curso pelo codigo
     * 
     */
    public boolean delete(String codigo) throws Exception {
        ParCodigoID pci = indiceIndiretoCodigo.read(ParCodigoID.hash(codigo));
        if(pci != null) {
            Curso c = read(pci.getId());
            if(c != null) {
                if(delete(pci.getId())) {
                    indiceIndiretoCodigo.delete(ParCodigoID.hash(codigo));
                    arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(c.getIdUsuario(), pci.getId()));
                    arvoreUsuarioNome.delete(new ParUsuarioNomeCursoId(c.getIdUsuario(), c.getNome(), pci.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * Deleta um curso pelo id
     * 
     */
    @Override
    public boolean delete(int id) throws Exception {
        Curso c = super.read(id);
        if(c != null) {
            if(super.delete(id)) {
                indiceIndiretoCodigo.delete(ParCodigoID.hash(c.getCodigo()));
                arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(c.getIdUsuario(), id));
                arvoreUsuarioNome.delete(new ParUsuarioNomeCursoId(c.getIdUsuario(), c.getNome(), id));
                return true;
            }
        }
        return false;
    }
    

    /**
     * 
     * Atualiza um curso
     * 
     */
    @Override
    public boolean update(Curso novoCurso) throws Exception {
        Curso cursoVelho = read(novoCurso.getId());

        if(super.update(novoCurso)) {
            if(novoCurso.getCodigo().compareTo(cursoVelho.getCodigo())!=0) {
                indiceIndiretoCodigo.delete(ParCodigoID.hash(cursoVelho.getCodigo()));
                indiceIndiretoCodigo.create(new ParCodigoID(novoCurso.getCodigo(), novoCurso.getId()));
                arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(cursoVelho.getIdUsuario(), cursoVelho.getId()));
                arvoreUsuarioCurso.create(new ParIdUsuarioIdCurso(novoCurso.getIdUsuario(), novoCurso.getId()));
                arvoreUsuarioNome.delete(new ParUsuarioNomeCursoId(cursoVelho.getIdUsuario(), cursoVelho.getNome(), cursoVelho.getId()));
                arvoreUsuarioNome.create(new ParUsuarioNomeCursoId(novoCurso.getIdUsuario(), novoCurso.getNome(), novoCurso.getId()));
            } else if (novoCurso.getIdUsuario() != cursoVelho.getIdUsuario()) {
                arvoreUsuarioCurso.delete(new ParIdUsuarioIdCurso(cursoVelho.getIdUsuario(), cursoVelho.getId()));
                arvoreUsuarioCurso.create(new ParIdUsuarioIdCurso(novoCurso.getIdUsuario(), novoCurso.getId()));
                arvoreUsuarioNome.delete(new ParUsuarioNomeCursoId(cursoVelho.getIdUsuario(), cursoVelho.getNome(), cursoVelho.getId()));
                arvoreUsuarioNome.create(new ParUsuarioNomeCursoId(novoCurso.getIdUsuario(), novoCurso.getNome(), novoCurso.getId()));
            } else if (novoCurso.getNome().compareTo(cursoVelho.getNome()) != 0) {
                arvoreUsuarioNome.delete(new ParUsuarioNomeCursoId(cursoVelho.getIdUsuario(), cursoVelho.getNome(), cursoVelho.getId()));
                arvoreUsuarioNome.create(new ParUsuarioNomeCursoId(novoCurso.getIdUsuario(), novoCurso.getNome(), novoCurso.getId()));
            }
            return true;
        }
        return false;
    }

    /**
     * 
     * Lista cursos de um usuario ordenados por nome
     * 
     */
    public ArrayList<Curso> listarPorUsuarioOrdenadoNome(int idUsuario) throws Exception {
        List<Curso> list = arvoreUsuarioNome.read(new ParUsuarioNomeCursoId(idUsuario, "", Integer.MIN_VALUE)).stream()
            .map(p -> {
                try {
                    return read(p.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(c -> c != null)
            .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Lista cursos de um usuario
     * 
     */
    public ArrayList<Curso> listarPorUsuario(int idUsuario) throws Exception {
        List<Curso> list = arvoreUsuarioCurso.read(new ParIdUsuarioIdCurso(idUsuario, Integer.MIN_VALUE)).stream()
            .map(p -> {
                try {
                    return read(p.getIdCurso());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            })
            .filter(c -> c != null)
            .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Abre um curso para inscricoes
     * 
     */
    boolean abrirCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if(c != null) {
            c.setEstado('0');
            return update(c);
        }
        return false;
    }

    /**
     * 
     * Encerra inscricoes de um curso
     * 
     */
    boolean encerrarIncricoes(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if(c != null) {
            c.setEstado('1');
            return update(c);
        }
        return false;
    }

    /**
     * 
     * Encerra um curso
     * 
     */
    boolean encerrarCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if(c != null) {
            c.setEstado('2');
            return update(c);
        }
        return false;
    }

    /**
     * 
     * Cancela um curso
     * 
     */
    boolean cancelarCurso(int idCurso) throws Exception {
        Curso c = read(idCurso);
        if(c != null) {
            c.setEstado('3');
            return update(c);
        }
        return false;
    }
}
