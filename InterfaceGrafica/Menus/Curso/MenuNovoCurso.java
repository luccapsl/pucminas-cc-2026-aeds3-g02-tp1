package InterfaceGrafica.Menus.Curso;

import java.util.Scanner;

import Entidades.Curso;
import InterfaceGrafica.Controles.Curso.ControleNovoCurso;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;

public class MenuNovoCurso implements IMenu {
    private ControleNovoCurso controleNovoCurso;

    public MenuNovoCurso() {
        this.controleNovoCurso = new ControleNovoCurso();
    }

    @Override
    public String getTitulo() {
        return "NovoCurso";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        Curso novoCurso = controleNovoCurso.processarNovoCurso(scanner);
        if (novoCurso == null) {
            System.out.println("Falha ao criar o curso. Tente novamente.");
        } else {
            System.out.println("Curso criado com sucesso! Redirecionando para a tela anterior...");
        }
        gerenciadorDeMenus.voltar();
    }
}
