package InterfaceGrafica.Menus.Curso;

import java.util.Scanner;

import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Curso.OpcaoGerenciarInscritos;

public class MenuGerenciarInscritos implements IMenu {

    @Override
    public String getTitulo() {
        return "Gerenciar Inscritos";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        System.out.println("GERENCIAR INSCRITOS:");
        System.out.println("EM BREVE.");
        System.out.println();

        OpcaoGerenciarInscritos opcao = MenuUtils.interacaoMenu(OpcaoGerenciarInscritos.class, scanner);
        if (opcao == null) return;
        switch (opcao) {
            case RETORNAR:
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}
