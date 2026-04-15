package Menus;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;

public class GerenciadorDeMenus {
    private Deque<IMenu> pilhaDeMenus;

    public GerenciadorDeMenus(IMenu menuInicial) {
        this.pilhaDeMenus = new ArrayDeque<>();
        this.pilhaDeMenus.push(menuInicial);
    }

    private void imprimirBreadcumber() {
        for(IMenu menu : pilhaDeMenus) {
            System.out.print(menu.getTitulo() + " > ");
        }
        System.out.println();
    }

    public void iniciar(Scanner scanner) {
        while(!pilhaDeMenus.isEmpty()) {
            imprimirBreadcumber();

            IMenu menuAtual = pilhaDeMenus.peek();
            menuAtual.show(this, scanner);
        }

        System.out.println("Encerrando o programa...");
    }

    public void irPara(IMenu novoMenu) {
        pilhaDeMenus.push(novoMenu);
    }

    public void voltar() {
        if(!pilhaDeMenus.isEmpty()) {
            pilhaDeMenus.pop();
        }
    }
}
