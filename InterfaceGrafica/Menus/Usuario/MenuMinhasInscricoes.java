package InterfaceGrafica.Menus.Usuario;

import java.util.Scanner;

import InterfaceGrafica.Controles.Usuario.ControleMinhasInscricoes;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Usuario.OpcaoMinhasInscricoes;

public class MenuMinhasInscricoes implements IMenu {
    private ControleMinhasInscricoes controleMinhasInscricoes;

    public MenuMinhasInscricoes() {
        this.controleMinhasInscricoes = new ControleMinhasInscricoes();
    }

    @Override
    public String getTitulo() {
        return "Minhas Inscrições";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        this.controleMinhasInscricoes.exibirMinhasInscricoes(gerenciadorDeMenus.getUsuarioLogado());
        OpcaoMinhasInscricoes opcaoMinhasInscricoes = MenuUtils.interacaoMenu(OpcaoMinhasInscricoes.class, scanner);
        if (opcaoMinhasInscricoes == null) return;
        switch (opcaoMinhasInscricoes) {
            case BUSCAR:
                try {
                    controleMinhasInscricoes.buscarPorCodigo(scanner, gerenciadorDeMenus);
                } catch (Exception e) {
                    System.out.println("Erro ao buscar curso: " + e.getMessage());
                }
                break;
            case LISTAR_CURSOS:
                try {
                    controleMinhasInscricoes.listarTodosCursos(gerenciadorDeMenus);
                } catch (Exception e) {
                    System.out.println("Erro ao listar cursos: " + e.getMessage());
                }
                break;
            case RETORNAR:
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}