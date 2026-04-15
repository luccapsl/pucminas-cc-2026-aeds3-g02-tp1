package InterfaceGrafica.Menus.Usuario;

import java.util.Scanner;

import CRUD.CrudUsuario;
import Entidades.Usuario;
import InterfaceGrafica.Controles.Usuario.ControleCadastro;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;

public class MenuCadastro implements IMenu {
    private ControleCadastro controleCadastro;
    public MenuCadastro() {
        this.controleCadastro = new ControleCadastro();
    }

    @Override
    public String getTitulo() {
        return "Cadastro";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        Usuario novoUsuario = this.controleCadastro.processarCadastro(scanner);
        if (novoUsuario == null) {
            System.out.println("Falha no cadastro. Tente novamente.");
        } else {
            System.out.println("Cadastro bem-sucedido! Redirecionando para o menu principal...");
        }

        gerenciadorDeMenus.setUsuarioLogado(novoUsuario);
        gerenciadorDeMenus.voltar();
    }
}