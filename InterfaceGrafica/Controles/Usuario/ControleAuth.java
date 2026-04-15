package InterfaceGrafica.Controles.Usuario;

import java.util.Scanner;

import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.Usuario.MenuLogin;
import InterfaceGrafica.Menus.Usuario.MenuCadastro;

public class ControleAuth {
    public ControleAuth() {}

    public void login(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        MenuLogin menuLogin = new MenuLogin();
        gerenciadorDeMenus.irPara(menuLogin);
    }
    public void cadastro(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        MenuCadastro menuCadastro = new MenuCadastro();
        gerenciadorDeMenus.irPara(menuCadastro);
    }
}
