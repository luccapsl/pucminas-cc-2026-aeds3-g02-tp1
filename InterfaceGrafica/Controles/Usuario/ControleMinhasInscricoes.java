package InterfaceGrafica.Controles.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import CRUD.CrudCurso;
import CRUD.CrudCursoUsuario;
//import CRUD.CrudCursoUsuarioTest;
import Entidades.Curso;
import Entidades.CursoUsuario;
import Entidades.Usuario;
import InterfaceGrafica.Menus.Curso.MenuDetalhesCurso;
import InterfaceGrafica.Menus.Curso.MenuListaCursos;
import InterfaceGrafica.Menus.GerenciadorDeMenus;

public class ControleMinhasInscricoes {
    private CrudCurso crudCurso;
    private CrudCursoUsuario crudCursoUsuario;
    private Curso curso;

    public ControleMinhasInscricoes() {
        try {
            this.crudCurso = new CrudCurso();
            this.crudCursoUsuario = new CrudCursoUsuario();
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o controle de cursos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String nomeEstado(char idEstado){
        if(idEstado == '0'){
            return "Ativo";
        }else if(idEstado == '1'){
            return "Inscrições encerradas";
        }else if(idEstado == '2'){
            return "Concluído";
        }else{
            return "Cancelado";
        }
    }

    public void exibirMinhasInscricoes(Usuario usuarioLogado) {
        System.out.println("===== MINHAS INSCRIÇÕES ====");
        try {
            //Busca a lista de ids de cursos nos quais o aluno está inscrito
            ArrayList<CursoUsuario> minhasInscricoes = crudCursoUsuario.readAllByUsuario(usuarioLogado.getId());

            //Se a lista tiver vazia avisamos o usuario
            if (minhasInscricoes == null || minhasInscricoes.isEmpty()) {
                System.out.println("Você ainda não se inscreveu em nenhum curso.");
                System.out.println("==============================\n");
                return;
            }

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

            //Iteração sobre cada inscrição pra buscar o nome e Estado do curso
            for(int i = 0; i < minhasInscricoes.size(); i++){
                CursoUsuario inscricao = minhasInscricoes.get(i);

                //Vamos no arquivo pegar o objeto curso completo pelo id
                Curso cursoDetalhe = crudCurso.read(inscricao.getIdCurso());

                if (cursoDetalhe != null) {
                    //Impressão no formato: Nome - Estado (Inscrição: dd/mm/yyyy)
                    System.out.printf("(%d) %s - Estado: %s (Inscrito a: %s)%n",
                        i+1,
                        cursoDetalhe.getNome(),
                        nomeEstado(cursoDetalhe.getEstado()),
                        sdf.format(inscricao.getDataInscricao())
                    );
                }
            }
            System.out.println("==============================\n");
        } catch (Exception e) {
            System.out.println("Erro ao carregar suas inscrições: " + e.getMessage());
        }
    }

    public void buscarPorCodigo(Scanner scanner, GerenciadorDeMenus gerenciadorDeMenus) throws IOException {
        try {
            System.out.print("Digite o código do curso: "); 
            String codigoDigitado = scanner.nextLine();
            curso = crudCurso.read(codigoDigitado);
            if (curso != null) {
                gerenciadorDeMenus.irPara(new MenuDetalhesCurso(curso));
            } else {
                System.out.println("Curso não encontrado com o código informado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar curso: " + e.getMessage());
        }
    }

    public void listarTodosCursos(GerenciadorDeMenus gerenciadorDeMenus) throws IOException {
        try {
            ArrayList<Curso> cursos = crudCurso.listarCursosOrdenadoDataInicio();
            gerenciadorDeMenus.irPara(new MenuListaCursos(cursos));
        } catch (Exception e) {
            System.out.println("Erro ao listar cursos: " + e.getMessage());
        }
    }
    
    public ArrayList<Curso> obterCursosDisponiveis(Usuario usuarioLogado){
        ArrayList<Curso> cursosDisponiveis = new ArrayList<>();

        try {
            ArrayList<Curso> listagemCursos = crudCurso.readAll();
            ArrayList<CursoUsuario> cursosUsuario = crudCursoUsuario.readAllByUsuario(usuarioLogado.getId());


            //Varro curso por curso
            for (int i = 0; i < listagemCursos.size(); i++) {
                Curso cursoAtual = listagemCursos.get(i);
                boolean jaInscrito = false;

                //E pra cada um verifico se o usuario esta matriculado nesse curso
                for (int j = 0; j < cursosUsuario.size(); j++) {
                    if(cursosUsuario.get(j).getIdCurso() == cursoAtual.getId()){
                        jaInscrito = true;
                        break;
                    }
                }

                //Caso não, insiro na lista de cursos disponíveis
                if (!jaInscrito) {
                    cursosDisponiveis.add(cursoAtual);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar os cursos: " + e.getMessage());
        }

        return cursosDisponiveis;
    }

    public void efetivarInscricao(int idCurso, Usuario usuarioLogado){
        try {
            CursoUsuario novaInscricao = new CursoUsuario(idCurso, usuarioLogado.getId(), new java.util.Date());
            crudCursoUsuario.create(novaInscricao);
            System.out.println("Inscrição realizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao efetivar a inscrição: " + e.getMessage());
        }
    }

    public boolean possuiInscricoes(Usuario usuario){
        try {
            // Se a lista que vier do banco não estiver vazia, retorna true. Senão, false.
            return !crudCursoUsuario.readAllByUsuario(usuario.getId()).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void cancelarInscricao(int idCurso, Usuario usuarioLogado){
        try {
            ArrayList<CursoUsuario> inscricoes = crudCursoUsuario.readAllByUsuario(usuarioLogado.getId());
            for (int i = 0; i < inscricoes.size(); i++) {
                CursoUsuario inscricaoAtual = inscricoes.get(i);
                if (inscricaoAtual.getIdCurso() == idCurso) {
                    crudCursoUsuario.delete(inscricaoAtual.getId());
                    System.out.println("Curso deletado com sucesso!");
                    return;
                }
            }
            System.out.println("Curso não encontrado.");

        } catch (Exception e) {
            System.out.println("Erro ao cancelar curso: " + e.getMessage());
        }   
    }
}
