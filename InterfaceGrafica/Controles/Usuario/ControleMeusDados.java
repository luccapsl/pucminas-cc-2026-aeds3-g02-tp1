package InterfaceGrafica.Controles.Usuario;

import java.util.Scanner;

import CRUD.CrudUsuario;
import Entidades.Usuario;
import InterfaceGrafica.Menus.GerenciadorDeMenus;

public class ControleMeusDados {
    private CrudUsuario crudUsuario;

    public ControleMeusDados() {
        try {
            this.crudUsuario = new CrudUsuario();
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o controle de usuários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exibirDadosUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }
        System.out.println(usuario.toStringSafe());
    }

    public void alterarNome(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        Usuario usuario = gerenciadorDeMenus.getUsuarioLogado();
        if (usuario == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }
        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        usuario.setNome(novoNome);
        try {
            crudUsuario.update(usuario);
            System.out.println("Nome atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar nome: " + e.getMessage());
        }
    }

    public void alterarEmail(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        Usuario usuario = gerenciadorDeMenus.getUsuarioLogado();
        if (usuario == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }
        System.out.print("Digite o novo email: ");
        String novoEmail = scanner.nextLine();
        usuario.setEmail(novoEmail);
        try {
            crudUsuario.update(usuario);
            System.out.println("Email atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar email: " + e.getMessage());
        }
    }

    public void alterarSenha(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        Usuario usuario = gerenciadorDeMenus.getUsuarioLogado();
        if (usuario == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }
        System.out.println("Para alterar a senha, por favor, responda à pergunta secreta:");
        System.out.println(usuario.getPerguntaSecreta());
        System.out.print("Resposta: ");
        String resposta = scanner.nextLine();
        if (!resposta.equalsIgnoreCase(usuario.getRespostaSecreta())) {
            System.out.println("Resposta incorreta. A senha não será alterada.");
            return;
        }
        System.out.println("Resposta correta! Agora, digite a nova senha.");

        System.out.print("Digite a nova senha: ");
        String novaSenha = scanner.nextLine();
        usuario.setSenha(novaSenha);

        try {
            crudUsuario.update(usuario);
            System.out.println("Senha atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar senha: " + e.getMessage());
        }
    }
}
