package InterfaceGrafica.Controles.Curso;

import java.util.Scanner;

import Entidades.Curso;
import InterfaceGrafica.Menus.GerenciadorDeMenus;

public class ControleNovoCurso {
    public ControleNovoCurso() {
        
    }

    public Curso processarNovoCurso(Scanner scanner) {
        System.out.println("Digite o nome do curso:");
        String nome = scanner.nextLine();

        System.out.println("Digite a descrição do curso:");
        String descricao = scanner.nextLine();

        System.out.println("Digite a data de início do curso (formato: dd/MM/yyyy):");
        String dataInicio = scanner.nextLine();

        System.out.println("Digite a data de término do curso (formato: dd/MM/yyyy):");
        String dataFim = scanner.nextLine();

        Curso novoCurso = new Curso(nome, descricao, dataInicio, dataFim);
        return novoCurso;
    }
}
