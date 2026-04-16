package InterfaceGrafica.Controles.Curso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import CRUD.CrudCurso;
import Entidades.Curso;
import InterfaceGrafica.Menus.GerenciadorDeMenus;

public class ControleAlterarCurso {
    private CrudCurso crudCurso;

    public ControleAlterarCurso() {
        try {
            this.crudCurso = new CrudCurso();
        } catch (Exception e) {
            System.out.println("Erro ao inicializar o controle de cursos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exibirDadosCurso(Curso curso) {
        if (curso == null) {
            System.out.println("Nenhum curso selecionado.");
            return;
        }
        System.out.println(curso.toStringSafe());
    }

    public void alterarNome(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner, Curso curso) {
        System.out.print("Digite o novo nome do curso: ");
        String novoNome = scanner.nextLine();
        curso.setNome(novoNome);
        try {
            crudCurso.update(curso);
            System.out.println("Nome do curso atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar nome do curso: " + e.getMessage());
        }
    }

    public void alterarDescricao(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner, Curso curso) {
        System.out.print("Digite a nova descrição do curso: ");
        String novaDescricao = scanner.nextLine();
        curso.setDescricao(novaDescricao);
        try {
            crudCurso.update(curso);
            System.out.println("Descrição do curso atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar descrição do curso: " + e.getMessage());
        }
    }

    public void alterarDataInicio(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner, Curso curso) {
        Date novaData = null;
        do {
            System.out.print("Digite a nova data de início do curso (formato: dd/MM/yyyy): ");
            String dataString = scanner.nextLine();
            try {
                novaData = new SimpleDateFormat("dd/MM/yyyy").parse(dataString);
            } catch (ParseException e) {
                System.out.println("Data inválida. Digite novamente.");
            }
        } while (novaData == null);

        curso.setDataInicio(novaData);
        try {
            crudCurso.update(curso);
            System.out.println("Data de início do curso atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar data de início do curso: " + e.getMessage());
        }
    }
}
