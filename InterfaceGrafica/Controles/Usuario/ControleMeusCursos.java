package InterfaceGrafica.Controles.Usuario;

import java.util.ArrayList;
import java.util.List;

import CRUD.CrudCurso;
import Entidades.Curso;
import Entidades.Usuario;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.Curso.MenuCurso;
import InterfaceGrafica.Menus.Curso.MenuNovoCurso;

public class ControleMeusCursos {
    private CrudCurso crudCurso;

    public ControleMeusCursos() {
        try{
            this.crudCurso = new CrudCurso();
        } catch(Exception e) {
            System.out.println("Erro ao inicializar o controle de cursos: " + e.getMessage());
        }
    }

    public ArrayList<Curso> obterCursosDoUsuario(Usuario usuario) {
        try {
            return this.crudCurso.listarCursosUsuarioOrdenadoNome(usuario.getId());
        } catch (Exception e) {
            System.out.println("Erro ao obter cursos do usuário: " + e.getMessage());
            return null;
        }
    }

    public void exibirCursos(List<Curso> cursos) {
        if (cursos == null || cursos.isEmpty()) {
            System.out.println("Você não está matriculado em nenhum curso.");
            return;
        }

        System.out.println("CURSOS");
        for (int i = 0; i < cursos.size(); i++) {
            Curso curso = cursos.get(i);
            System.out.printf("(%d) %s - %s%n", i + 1, curso.getNome(), curso.getDataInicio().toString());
        }
        System.out.println();
    }

    public void handleEscolhaCurso(int escolha, List<Curso> cursos, GerenciadorDeMenus gerenciadorDeMenus) {
        if (escolha < 1 || escolha > cursos.size()) {
            System.out.println("Escolha inválida. Por favor, tente novamente.");
            return;
        }

        Curso cursoSelecionado = cursos.get(escolha - 1);
        gerenciadorDeMenus.irPara(new MenuCurso(cursoSelecionado));
    }
    public void novoCurso(java.util.Scanner scanner, GerenciadorDeMenus gerenciadorDeMenus) {
        MenuNovoCurso menuNovoCurso = new MenuNovoCurso();
        gerenciadorDeMenus.irPara(menuNovoCurso);
        
    }
}
