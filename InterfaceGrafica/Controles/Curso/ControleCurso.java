package InterfaceGrafica.Controles.Curso;

import java.util.Scanner;

import CRUD.CrudCurso;
import Entidades.Curso;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.Curso.MenuAlterarCurso;
import InterfaceGrafica.Menus.Curso.MenuGerenciarInscritos;

public class ControleCurso {
    private CrudCurso crudCurso;

    public ControleCurso() {
        try {
            this.crudCurso = new CrudCurso();
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o controle de curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void gerenciarInscritos(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        gerenciadorDeMenus.irPara(new MenuGerenciarInscritos());
    }

    public void corrigirDados(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner, Curso curso) {
        gerenciadorDeMenus.irPara(new MenuAlterarCurso(curso));
    }

    public void encerrarInscricoes(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner, Curso curso) {
        System.out.println("Tem certeza que deseja encerrar as inscrições do curso \"" + curso.getNome() + "\"?");
        System.out.print("(S) Sim / (N) Não: ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            try {
                curso.setEstado('1');
                crudCurso.update(curso);
                System.out.println("Inscrições encerradas com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao encerrar inscrições: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public void concluirCurso(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner, Curso curso) {
        System.out.println("Tem certeza que deseja concluir o curso \"" + curso.getNome() + "\"?");
        System.out.print("(S) Sim / (N) Não: ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            try {
                curso.setEstado('2');
                crudCurso.update(curso);
                System.out.println("Curso concluído com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao concluir curso: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public void cancelarCurso(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner, Curso curso) {
        System.out.println("Tem certeza que deseja cancelar o curso \"" + curso.getNome() + "\"?");
        System.out.println("ATENÇÃO: Esta ação não pode ser desfeita!");
        System.out.print("(S) Sim / (N) Não: ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            try {
                curso.setEstado('3');
                crudCurso.update(curso);
                System.out.println("Curso cancelado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao cancelar curso: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
