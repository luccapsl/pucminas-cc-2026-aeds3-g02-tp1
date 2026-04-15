package Menus;
import java.util.Scanner;

public class MenuPrincipal implements IMenu {
    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        System.out.println("Menu Principal");
        System.out.println("1 - Menu de Usuarios");
        System.out.println("2 - Menu de Produtos");
        System.out.println("3 - Menu de Pedidos");
        System.out.println("(R) - Voltar");

        int opcao = scanner.nextInt();
        switch (opcao) {
            case 1:
                System.out.println("Menu de Usuarios");
                break;
            case 2:
                System.out.println("Menu de Produtos");
                break;
            case 3:
                System.out.println("Menu de Pedidos");
                break;
            case 0:
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    @Override
    public String getTitulo() {
        return "Menu Principal";
    }
}
