package Menus;

import java.util.Scanner;

public interface IMenu {
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner);
    public String getTitulo();
}