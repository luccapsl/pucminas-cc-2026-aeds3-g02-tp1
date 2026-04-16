package InterfaceGrafica.Menus.Curso;

import java.util.Scanner;

import Entidades.Curso;
import InterfaceGrafica.Controles.Curso.ControleAlterarCurso;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Curso.OpcaoAlterarCurso;

public class MenuAlterarCurso implements IMenu {
    private ControleAlterarCurso controleAlterarCurso;
    private Curso curso;

    public MenuAlterarCurso(Curso curso) {
        this.controleAlterarCurso = new ControleAlterarCurso();
        this.curso = curso;
    }

    @Override
    public String getTitulo() {
        return "Alterar Curso";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        this.controleAlterarCurso.exibirDadosCurso(this.curso);

        OpcaoAlterarCurso opcao = MenuUtils.interacaoMenu(OpcaoAlterarCurso.class, scanner);

        switch (opcao) {
            case ALTERAR_NOME:
                controleAlterarCurso.alterarNome(gerenciadorDeMenus, scanner, this.curso);
                break;
            case ALTERAR_DESCRICAO:
                controleAlterarCurso.alterarDescricao(gerenciadorDeMenus, scanner, this.curso);
                break;
            case ALTERAR_DATA_INICIO:
                controleAlterarCurso.alterarDataInicio(gerenciadorDeMenus, scanner, this.curso);
                break;
            case RETORNAR:
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
