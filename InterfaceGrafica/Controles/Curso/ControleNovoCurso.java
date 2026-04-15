package InterfaceGrafica.Controles.Curso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import CRUD.CrudCurso;
import Entidades.Curso;
import Entidades.Usuario;
import InterfaceGrafica.Menus.GerenciadorDeMenus;

public class ControleNovoCurso {
    CrudCurso crudCurso;

    public ControleNovoCurso() {
        try {
            crudCurso = new CrudCurso();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o controle de curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Curso processarNovoCurso(Usuario usuarioLogado, Scanner scanner) {
        System.out.println("Digite o nome do curso: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a descrição do curso: ");
        String descricao = scanner.nextLine();

        Date dataInicio = null;
        do {
            System.out.println("Digite a data de início do curso (formato: dd/MM/yyyy): ");
            String dataInicioString = scanner.nextLine();
            try {
                dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicioString);
            } catch (ParseException e) {
                System.out.println("Data inválida. Digite novamente.");
            }
        } while (dataInicio == null);

        try {
            Curso novoCurso = new Curso(nome, '0', dataInicio, descricao, usuarioLogado.getId());
            this.crudCurso.create(novoCurso);
            System.out.println("Curso criado com sucesso!");
            return novoCurso;
        } catch (Exception e) {
            System.out.println("Erro ao criar curso: " + e.getMessage());
            // e.printStackTrace();
        }

        return null;
    }
}
