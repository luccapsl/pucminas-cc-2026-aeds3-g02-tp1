import java.util.Scanner;
import Menus.GerenciadorDeMenus;
import Menus.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        GerenciadorDeMenus gerenciador = new GerenciadorDeMenus(new MenuPrincipal());
        
        try {
            gerenciador.iniciar(scanner);
        } catch (Exception e) {
            System.err.println("Erro crítico no sistema: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}