package InterfaceGrafica.Controles.Usuario;

import java.util.Scanner;

import CRUD.CrudUsuario;
import Entidades.Usuario;

public class ControleCadastro {
    private CrudUsuario crudUsuario;

    public ControleCadastro() {
        try {
            this.crudUsuario = new CrudUsuario();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o controle de cadastro: " + e.getMessage());
        }
    }

    public Usuario processarCadastro(Scanner scanner) {
        try {
            Usuario novoUsuario = new Usuario();

            String nome, email, senha, perguntaSecreta, respostaSecreta;

            System.out.print("Digite seu nome: ");
            nome = scanner.nextLine();
            Usuario usuarioExistente = null;
            do {
                System.out.print("Digite seu email: ");
                email = scanner.nextLine();
                usuarioExistente = crudUsuario.read(email);
                if (usuarioExistente != null) {
                    System.out.println("Email já cadastrado. Por favor, tente outro.");
                }
            } while (usuarioExistente != null);

            System.out.print("Digite sua senha: ");
            senha = scanner.nextLine();
            System.out.print("Digite sua pergunta secreta: ");
            perguntaSecreta = scanner.nextLine();
            System.out.print("Digite sua resposta secreta: ");
            respostaSecreta = scanner.nextLine();

            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);

            novoUsuario.setSenha(senha);
            novoUsuario.setPerguntaSecreta(perguntaSecreta);
            novoUsuario.setRespostaSecreta(respostaSecreta);

            int id = this.crudUsuario.create(novoUsuario);
            if (id <= 0) {
                return null;
            }

            novoUsuario.setId(id);

            return novoUsuario;

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }

        return null;
    }
}
