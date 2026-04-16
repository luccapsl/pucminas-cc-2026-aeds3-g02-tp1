package InterfaceGrafica.Menus.Usuario;

import java.util.Scanner;

import InterfaceGrafica.Controles.Usuario.ControleAuth;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.IOpcaoMenu;
import InterfaceGrafica.Opcoes.Usuario.OpcaoAuth;



public class MenuAuth implements IMenu {
    private ControleAuth controleAuth;

    public MenuAuth() {
        this.controleAuth = new ControleAuth();
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {

        OpcaoAuth opcaoAuth = MenuUtils.interacaoMenu(OpcaoAuth.class, scanner);
        if (opcaoAuth == null) return;
        switch (opcaoAuth) {
            case LOGIN:
                controleAuth.login(gerenciadorDeMenus, scanner);
                break;
            case CADASTRO:
                controleAuth.cadastro(gerenciadorDeMenus, scanner);
                break;
            case VOLTAR:
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    @Override
    public String getTitulo() {
        return "";
    }
}
