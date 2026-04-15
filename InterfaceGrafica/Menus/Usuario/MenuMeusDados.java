package InterfaceGrafica.Menus.Usuario;

import java.util.Scanner;

import InterfaceGrafica.Controles.Usuario.ControleMeusDados;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Usuario.OpcaoMeusDados;;;

public class MenuMeusDados implements IMenu {
    private ControleMeusDados controleMeusDados;

    public MenuMeusDados() {
        this.controleMeusDados = new ControleMeusDados();
    }

    @Override
    public String getTitulo() {
        return "Meus Dados";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        this.controleMeusDados.exibirDadosUsuario(gerenciadorDeMenus.getUsuarioLogado());

        OpcaoMeusDados opcaoMeusDados = MenuUtils.interacaoMenu(OpcaoMeusDados.class, scanner);

        switch (opcaoMeusDados) {
            case ALTERAR_NOME:
                controleMeusDados.alterarNome(gerenciadorDeMenus, scanner);
                break;
            case ALTERAR_EMAIL:
                controleMeusDados.alterarEmail(gerenciadorDeMenus, scanner);
                break;
            case ALTERAR_SENHA:
                controleMeusDados.alterarSenha(gerenciadorDeMenus, scanner);
                break;
            case RETORNAR:
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}