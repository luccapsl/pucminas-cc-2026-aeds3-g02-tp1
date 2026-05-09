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
        System.out.println();

        OpcaoGerenciarInscritos opcao =
                MenuUtils.interacaoMenu(OpcaoGerenciarInscritos.class, scanner);

        if (opcao == null) return;

        switch (opcao) {

            case EXPORTAR_INSCRITOS:
                System.out.println("Exportando inscritos...");
                
                // implementar lógica de exportação aqui
                
                break;

            case RETORNAR:
                gerenciadorDeMenus.voltar();
                break;

            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}