package InterfaceGrafica.Controles.Usuario;

import java.util.Scanner;

import CRUD.CrudUsuario;
import Entidades.Usuario;

public class ControleLogin {
    private CrudUsuario crudUsuario;

    public ControleLogin() {
        try {
            this.crudUsuario = new CrudUsuario();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o controle de login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Usuario processarLogin(Scanner scanner) {
        try {
            System.out.print("Digite seu email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Digite sua senha: ");
            String senha = scanner.nextLine().trim();
            String hashSenha = String.valueOf(senha.hashCode());

            Usuario usuario = crudUsuario.read(email);

            if (usuario == null) {
                System.out.println("Usuário não encontrado com o email fornecido.");
                return null;
            }

            if (!usuario.getSenha().equals(hashSenha)) {
                System.out.println("Senha incorreta. Tente novamente.");
                return null;
            }

            return usuario;

        } catch (Exception e) {
            System.err.println("Erro ao processar login: " + e.getMessage());
            return null;
        }
    }
}
