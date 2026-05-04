package InterfaceGrafica.Controles.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import CRUD.CrudCurso;
import Entidades.Curso;
import Entidades.Usuario;
import InterfaceGrafica.Menus.Curso.MenuDetalhesCurso;
import InterfaceGrafica.Menus.Curso.MenuListaCursos;
import InterfaceGrafica.Menus.GerenciadorDeMenus;

public class ControleMinhasInscricoes {
    private CrudCurso crudCurso;
    private Curso curso;

    public ControleMinhasInscricoes() {
        try {
            this.crudCurso = new CrudCurso();
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o controle de cursos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exibirMinhasInscricoes(Usuario usuarioLogado) {
        System.out.println("INSCRIÇÕES:");
        //Implementacao - Integrante 3
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
}
