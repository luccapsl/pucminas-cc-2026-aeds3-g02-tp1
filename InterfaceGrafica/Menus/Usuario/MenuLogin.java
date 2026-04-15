package InterfaceGrafica.Menus.Usuario;

import java.util.Scanner;

import Entidades.Usuario;
import InterfaceGrafica.Controles.Usuario.ControleLogin;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.Home.MenuHome;

public class MenuLogin implements IMenu {
    private ControleLogin controleLogin;

    public MenuLogin() {
        this.controleLogin = new ControleLogin();
    }

    @Override
    public String getTitulo() {
        return "Login";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        Usuario usuarioLogado = controleLogin.processarLogin(scanner);
        if (usuarioLogado == null) {
            System.out.println("Falha no login. Verifique suas credenciais e tente novamente.");
            gerenciadorDeMenus.voltar();
        } else {
            gerenciadorDeMenus.setUsuarioLogado(usuarioLogado);
            System.out.println("Login bem-sucedido! Redirecionando para o menu principal...");
            gerenciadorDeMenus.trocarTelaAtual(new MenuHome());
        }
    }
}
