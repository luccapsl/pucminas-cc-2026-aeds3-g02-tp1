package InterfaceGrafica.Menus.Home;
import java.util.Scanner;

import CRUD.CrudCurso;
import InterfaceGrafica.Controles.Home.ControleHome;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Home.OpcaoHome;

public class MenuHome implements IMenu {
    private ControleHome controleHome;

    @Override
    public String getTitulo() {
        return "Início";
    }

    public MenuHome() {
        this.controleHome = new ControleHome();
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {

        OpcaoHome opcaoHome = MenuUtils.interacaoMenu(OpcaoHome.class, scanner);
        if (opcaoHome == null) return;
        switch (opcaoHome) {
            case MEUS_DADOS:
                controleHome.meusDados(gerenciadorDeMenus, scanner);
                break;
            case MEUS_CURSOS:
                controleHome.meusCursos(gerenciadorDeMenus, scanner);
                break;
            case MINHAS_INSCRICOES:
                controleHome.minhasInscricoes(gerenciadorDeMenus, scanner);
                break;
            case DEBUG_INDICE_INVERTIDO:
                try {
                    new CrudCurso().printIndiceInvertido();
                } catch (Exception e) {
                    System.out.println("Erro ao exibir índice invertido: " + e.getMessage());
                }
                break;
            case SAIR:
                gerenciadorDeMenus.deslogarUsuario();
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
