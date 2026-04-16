package InterfaceGrafica.Menus.Curso;

import java.util.Scanner;

import Entidades.Curso;
import InterfaceGrafica.Controles.Curso.ControleCurso;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Curso.OpcaoCurso;

public class MenuCurso implements IMenu {
    private ControleCurso controleCurso;
    private Curso curso;

    public MenuCurso(Curso c) {
        this.controleCurso = new ControleCurso();
        this.curso = c;
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        System.out.println(this.curso.toStringSafe());

        OpcaoCurso opcaoCurso = MenuUtils.interacaoMenu(OpcaoCurso.class, scanner);
        
        switch (opcaoCurso) {
            case GERENCIAR_INSCRITOS:
                controleCurso.gerenciarInscritos(gerenciadorDeMenus, scanner);
                break;
            case CORRIGIR_DADOS:
                controleCurso.corrigirDados(gerenciadorDeMenus, scanner, this.curso);
                break;
            case ENCERRAR_INSCRICOES:
                controleCurso.encerrarInscricoes(gerenciadorDeMenus, scanner, this.curso);
                break;
            case CONCLUIR_CURSO:
                controleCurso.concluirCurso(gerenciadorDeMenus, scanner, this.curso);
                break;
            case CANCELAR_CURSO:
                controleCurso.cancelarCurso(gerenciadorDeMenus, scanner, this.curso);
                break;
            case RETORNAR:
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    @Override
    public String getTitulo() {
        return this.curso.getNome();
    }
}
