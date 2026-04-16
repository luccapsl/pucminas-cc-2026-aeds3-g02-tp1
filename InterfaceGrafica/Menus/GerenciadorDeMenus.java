package InterfaceGrafica.Menus;

import java.util.Deque;
import java.util.Iterator;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.StringJoiner;

import Entidades.Usuario;

public class GerenciadorDeMenus {
    public static final String TITULO_SISTEMA = "MyCursos";
    public static final String VERSAO_SISTEMA = "1.0";
    private Deque<IMenu> pilhaDeMenus;
    private Usuario usuarioLogado;

    public GerenciadorDeMenus(IMenu menuInicial) {
        this.pilhaDeMenus = new ArrayDeque<>();
        this.pilhaDeMenus.push(menuInicial);
    }

    private void imprimirHeader() {
        String header = TITULO_SISTEMA + " " + VERSAO_SISTEMA;
        if (usuarioLogado != null) {
            header += " - Usuário: " + usuarioLogado.getNome();
        }

        System.out.println(header);
        System.out.println("-".repeat(header.length()));

    }

    private void imprimirBreadcumber() {
        StringJoiner breadcrumb = new StringJoiner(" > ");

        Iterator<IMenu> iterador = pilhaDeMenus.descendingIterator();

        while (iterador.hasNext()) {
            breadcrumb.add(iterador.next().getTitulo());
        }
        System.out.println(breadcrumb.toString());
    }

    public void iniciar(Scanner scanner) {
        while (!pilhaDeMenus.isEmpty()) {
            // Limpar a tela (funciona na maioria dos terminais, mas pode variar)
            System.out.print("\033[H\033[2J");
            imprimirHeader();
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
        if (!pilhaDeMenus.isEmpty()) {
            pilhaDeMenus.pop();
        }
    }

    public void trocarTelaAtual(IMenu novoMenu) {
        if (!pilhaDeMenus.isEmpty()) {
            pilhaDeMenus.pop();
        }
        pilhaDeMenus.push(novoMenu);
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuarioLogado() {
        return this.usuarioLogado;
    }

    public void deslogarUsuario() {
        this.usuarioLogado = null;
    }
}
