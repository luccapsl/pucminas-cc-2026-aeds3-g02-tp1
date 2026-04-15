package InterfaceGrafica.Menus;

import java.util.Scanner;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public class MenuUtils {

    public static <T extends Enum<T> & IOpcaoMenu> T interacaoMenu(Class<T> classeDoEnum, Scanner scanner) {
        exibir(classeDoEnum);
        String digitado = scanner.nextLine();
        T opcaoSelecionada = interpretar(digitado, classeDoEnum);
        
        if (opcaoSelecionada != null) {
            System.out.println("Opção selecionada: " + opcaoSelecionada.getDescricao());
        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }

        return opcaoSelecionada;
    }
    /**
     * Imprime qualquer menu na tela automaticamente.
     */
    public static <T extends Enum<T> & IOpcaoMenu> void exibir(Class<T> classeDoEnum) {
        for (T opcao : classeDoEnum.getEnumConstants()) {
            System.out.printf("(%s) %s\n", opcao.getCodigo(), opcao.getDescricao());
        }
        System.out.print("\nOpção: ");
    }

    /**
     * Lê a string digitada e devolve a opção correspondente de QUALQUER menu.
     */
    public static <T extends Enum<T> & IOpcaoMenu> T interpretar(String digitado, Class<T> classeDoEnum) {
        if (digitado == null || digitado.trim().isEmpty()) {
            return null;
        }
        
        for (T opcao : classeDoEnum.getEnumConstants()) {
            if (opcao.getCodigo().equalsIgnoreCase(digitado.trim())) {
                return opcao;
            }
        }
        return null;
    }
}