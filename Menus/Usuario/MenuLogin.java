package Menus.Usuario;

import java.util.Scanner;
import Menus.GerenciadorDeMenus;
import Menus.IMenu;

public class MenuLogin implements IMenu {
    private ControleLogin controleLogin;
    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        
    }

    @Override
    public String getTitulo() {
        return "";
    }
}
