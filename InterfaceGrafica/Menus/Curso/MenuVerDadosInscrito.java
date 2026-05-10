package InterfaceGrafica.Menus.Curso;

import java.util.Scanner;

import CRUD.CrudCursoUsuario;
import Entidades.CursoUsuario;
import Entidades.Usuario;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Curso.OpcaoVerDadosInscrito;

public class MenuVerDadosInscrito implements IMenu {

    private Usuario aluno;
    private CursoUsuario inscricao;

    public MenuVerDadosInscrito(Usuario aluno, CursoUsuario inscricao) {
        this.aluno = aluno;
        this.inscricao = inscricao;
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        System.out.println("NOME...........: " + aluno.getNome());
        System.out.println("EMAIL..........: " + aluno.getEmail());

        System.out.println();
        OpcaoVerDadosInscrito opcao = MenuUtils.interacaoMenu(OpcaoVerDadosInscrito.class, scanner);
        if (opcao == null) return;

        switch (opcao) {
            case CANCELAR_INSCRICAO:
                System.out.println("Tem certeza que deseja cancelar a inscrição de " + aluno.getNome() + "?");
                System.out.print("(S) Sim / (N) Não: ");
                String confirmacao = scanner.nextLine();
                if (confirmacao.equalsIgnoreCase("S")) {
                    try {
                        CrudCursoUsuario crudCursoUsuario = new CrudCursoUsuario();
                        crudCursoUsuario.delete(inscricao.getId());
                        System.out.println("Inscrição cancelada com sucesso!");
                        gerenciadorDeMenus.voltar();
                    } catch (Exception e) {
                        System.out.println("Erro ao cancelar inscrição: " + e.getMessage());
                    }
                } else {
                    System.out.println("Operação cancelada.");
                }
                break;
            case RETORNAR:
                gerenciadorDeMenus.voltar();
                break;
        }
    }

    @Override
    public String getTitulo() {
        return "Dados do Inscrito";
    }
}
