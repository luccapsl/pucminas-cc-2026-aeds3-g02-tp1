import java.util.Scanner;

import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.Usuario.MenuAuth;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GerenciadorDeMenus gerenciador = new GerenciadorDeMenus(new MenuAuth());

        try {
            gerenciador.iniciar(scanner);
        } catch (Exception e) {
            System.err.println("Erro crítico no sistema: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}