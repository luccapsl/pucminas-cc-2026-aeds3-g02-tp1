package InterfaceGrafica.Menus.Usuario;

import java.util.ArrayList;
import java.util.Scanner;

import Entidades.Curso;
import InterfaceGrafica.Controles.Usuario.ControleMeusCursos;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Usuario.OpcaoMeusCursos;

public class MenuMeusCursos implements IMenu {
    private ControleMeusCursos controleMeusCursos;

    public MenuMeusCursos() {
        this.controleMeusCursos = new ControleMeusCursos();
    }

    @Override
    public String getTitulo() {
        return "Meus Cursos";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        ArrayList<Curso> cursosDoUsuario = this.controleMeusCursos.obterCursosDoUsuario(gerenciadorDeMenus.getUsuarioLogado());


        this.controleMeusCursos.exibirCursos(cursosDoUsuario);

        MenuUtils.exibir(OpcaoMeusCursos.class);
        String opcao = scanner.nextLine();
        OpcaoMeusCursos opcaoSelecionada = MenuUtils.interpretar(opcao, OpcaoMeusCursos.class);
        if (opcaoSelecionada == null) {
            this.controleMeusCursos.handleEscolhaCurso(Integer.parseInt(opcao), cursosDoUsuario, gerenciadorDeMenus);
        }else {
            switch (opcaoSelecionada) {
                case NOVO_CURSO:
                    this.controleMeusCursos.novoCurso(scanner, gerenciadorDeMenus);
                    break;
                case RETORNAR:
                    gerenciadorDeMenus.voltar();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}