package InterfaceGrafica.Controles.Home;

import java.util.Scanner;

import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.Usuario.MenuMeusCursos;
import InterfaceGrafica.Menus.Usuario.MenuMeusDados;
import InterfaceGrafica.Menus.Usuario.MenuMinhasInscricoes;

public class ControleHome {
    public ControleHome() {}

    public void meusDados(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        MenuMeusDados menuMeusDados = new MenuMeusDados();
        gerenciadorDeMenus.irPara(menuMeusDados);
    }

    public void meusCursos(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        MenuMeusCursos menuMeusCursos = new MenuMeusCursos();
        gerenciadorDeMenus.irPara(menuMeusCursos);
    }

    public void minhasInscricoes(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        MenuMinhasInscricoes menuMinhasInscricoes = new MenuMinhasInscricoes();
        gerenciadorDeMenus.irPara(menuMinhasInscricoes);
    }
}
