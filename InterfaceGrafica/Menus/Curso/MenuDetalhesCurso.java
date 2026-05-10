package InterfaceGrafica.Menus.Curso;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import CRUD.CrudUsuario;
import Entidades.Curso;
import Entidades.Usuario;
import InterfaceGrafica.Controles.Curso.ControleCurso;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Curso.OpcaoDetalhesCurso;

public class MenuDetalhesCurso implements IMenu {
    private ControleCurso controleCurso;
    private CrudUsuario crudUsuario;
    private Usuario usuario;
    private Curso curso;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public MenuDetalhesCurso(Curso c) throws Exception {
        this.controleCurso = new ControleCurso();
        this.crudUsuario = new CrudUsuario();
        this.usuario = new Usuario();

        this.curso = c;

        try {
            this.usuario = crudUsuario.read(c.getIdUsuario());
            if (this.usuario != null) {
                this.curso.setAutor(this.usuario.getNome());
            } else {
                this.curso.setAutor("Autor desconhecido");
            }
        } catch (Exception e) {
            System.out.println("[ERRO] - Não foi possível carregar o autor do curso."); 
        }
    }


    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        System.out.println("CÓDIGO.........: " + this.curso.getCodigo());
        System.out.println("CURSO..........: " + this.curso.getNome());
        System.out.println("AUTOR..........: " + this.curso.getAutor());
        System.out.println("DESCRIÇÃO......: " + this.curso.getDescricao());
        System.out.println("DATA INÍCIO....: " + this.sdf.format(this.curso.getDataInicio()));

        OpcaoDetalhesCurso opcaoDetalhesCurso = MenuUtils.interacaoMenu(OpcaoDetalhesCurso.class, scanner);
        if (opcaoDetalhesCurso == null) return;
        switch (opcaoDetalhesCurso) {
            case INSCRICAO:
                InterfaceGrafica.Controles.Usuario.ControleMinhasInscricoes controleInscricoes = new InterfaceGrafica.Controles.Usuario.ControleMinhasInscricoes();
                controleInscricoes.efetivarInscricao(this.curso.getId(), gerenciadorDeMenus.getUsuarioLogado());
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
