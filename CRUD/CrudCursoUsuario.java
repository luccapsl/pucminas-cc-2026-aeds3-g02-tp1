package CRUD;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Entidades.CursoUsuario;
import Genericos.ArvoreBMais;
import Genericos.ParIdCursoIdCursoUsuario;
import Genericos.ParIdUsuarioIdCursoUsuario;

public class CrudCursoUsuario extends Genericos.Arquivo<CursoUsuario> {

    ArvoreBMais<ParIdCursoIdCursoUsuario> arvoreCursoCursoUsuario;
    ArvoreBMais<ParIdUsuarioIdCursoUsuario> arvoreUsuarioCursoUsuario;

    /**
     * 
     * Construtor da classe CrudCursoUsuario
     * 
     */
    public CrudCursoUsuario() throws Exception {
        super("cursoUsuario", CursoUsuario.class.getConstructor());
        arvoreCursoCursoUsuario = new ArvoreBMais<>(
                ParIdCursoIdCursoUsuario.class.getConstructor(),
                4,
                "." + File.separator + "dados" + File.separator + "cursoUsuario" + File.separator
                        + "arvoreCursoCursoUsuario.d.db");
        arvoreUsuarioCursoUsuario = new ArvoreBMais<>(
                ParIdUsuarioIdCursoUsuario.class.getConstructor(),
                4,
                "." + File.separator + "dados" + File.separator + "cursoUsuario" + File.separator
                        + "arvoreUsuarioCursoUsuario.d.db");
    }

    /**
     * 
     * Cria uma nova inscrição (CursoUsuario) no arquivo
     * 
     */
    @Override
    public int create(CursoUsuario cu) throws Exception {
        int id = super.create(cu);

        System.out.println("Inscrição criada com ID: " + id
                + " | Curso ID: " + cu.getIdCurso()
                + " | Usuário ID: " + cu.getIdUsuario());

        arvoreCursoCursoUsuario.create(new ParIdCursoIdCursoUsuario(cu.getIdCurso(), id));
        arvoreUsuarioCursoUsuario.create(new ParIdUsuarioIdCursoUsuario(cu.getIdUsuario(), id));
        return id;
    }

    /**
     * 
     * Lê uma inscrição pelo id
     * 
     */
    public CursoUsuario read(int id) throws Exception {
        return super.read(id);
    }

    /**
     * 
     * Lista todas as inscrições de um curso
     * 
     */
    public ArrayList<CursoUsuario> readAllByCurso(int idCurso) throws Exception {
        List<CursoUsuario> list = arvoreCursoCursoUsuario
                .read(new ParIdCursoIdCursoUsuario(idCurso, Integer.MIN_VALUE)).stream()
                .map(p -> {
                    try {
                        return read(p.getIdCursoUsuario());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(cu -> cu != null)
                .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Lista todas as inscrições de um usuário
     * 
     */
    public ArrayList<CursoUsuario> readAllByUsuario(int idUsuario) throws Exception {
        List<CursoUsuario> list = arvoreUsuarioCursoUsuario
                .read(new ParIdUsuarioIdCursoUsuario(idUsuario, Integer.MIN_VALUE)).stream()
                .map(p -> {
                    try {
                        return read(p.getIdCursoUsuario());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(cu -> cu != null)
                .collect(Collectors.toList());
        return new ArrayList<>(list);
    }

    /**
     * 
     * Deleta uma inscrição pelo id
     * 
     */
    @Override
    public boolean delete(int id) throws Exception {
        CursoUsuario cu = super.read(id);
        if (cu != null) {
            if (super.delete(id)) {
                arvoreCursoCursoUsuario.delete(new ParIdCursoIdCursoUsuario(cu.getIdCurso(), id));
                arvoreUsuarioCursoUsuario.delete(new ParIdUsuarioIdCursoUsuario(cu.getIdUsuario(), id));
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * Atualiza uma inscrição
     * 
     */
    @Override
    public boolean update(CursoUsuario novoCursoUsuario) throws Exception {
        CursoUsuario velho = read(novoCursoUsuario.getId());

        if (super.update(novoCursoUsuario)) {
            // Atualiza os índices se o idCurso ou idUsuario mudaram
            if (novoCursoUsuario.getIdCurso() != velho.getIdCurso()) {
                arvoreCursoCursoUsuario.delete(new ParIdCursoIdCursoUsuario(velho.getIdCurso(), velho.getId()));
                arvoreCursoCursoUsuario.create(new ParIdCursoIdCursoUsuario(novoCursoUsuario.getIdCurso(), novoCursoUsuario.getId()));
            }
            if (novoCursoUsuario.getIdUsuario() != velho.getIdUsuario()) {
                arvoreUsuarioCursoUsuario.delete(new ParIdUsuarioIdCursoUsuario(velho.getIdUsuario(), velho.getId()));
                arvoreUsuarioCursoUsuario.create(new ParIdUsuarioIdCursoUsuario(novoCursoUsuario.getIdUsuario(), novoCursoUsuario.getId()));
            }
            return true;
        }
        return false;
    }
}
