package InterfaceGrafica.Menus.Curso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import Entidades.Curso;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Curso.OpcaoListaCurso;

public class MenuListaCursos implements IMenu {
    private List<Curso> cursos;
    private int paginaAtual;
    private final int ITENS_POR_PAGINA = 10;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    public MenuListaCursos(List<Curso> cursos) throws Exception {
        try {
            this.cursos = cursos;
            this.paginaAtual = 0;
        } catch (Exception e) {
            System.out.println("[ERRO] - Não foi possível carregar os cursos.");
        }
    }


    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        try {
            if (this.cursos.isEmpty()) {
                System.out.println("Nenhum curso encontrado.");
                gerenciadorDeMenus.voltar();
            } else {
                int totalPaginas = (int) Math.ceil((double) this.cursos.size() / 10.0);
                int indiceInicial = this.paginaAtual * 10;
                int indiceFinal = Math.min(indiceInicial+10, this.cursos.size());
                int posNaPagina = 0;
                int tecla = 0;

                Curso c = null;

                System.out.println("Página "+(this.paginaAtual+1)+" de "+totalPaginas);

                for (int i = indiceInicial; i < indiceFinal; i++) {
                    posNaPagina = i - indiceInicial;
                    tecla = (posNaPagina + 1) % 10; 
                    c = this.cursos.get(i);
                
                    System.out.println("("+tecla+") " + c.getNome() + " - " + this.sdf.format(c.getDataInicio()));

                }
                System.out.println();

                MenuUtils.exibir(OpcaoListaCurso.class);
                String entrada = scanner.nextLine();

                OpcaoListaCurso opcao = MenuUtils.interpretar(entrada, OpcaoListaCurso.class);
                
                if (opcao != null) {
                    switch(opcao) {
                        case PROXIMA_PAGINA:
                            if (this.paginaAtual < totalPaginas - 1) {
                                this.paginaAtual++;
                            }
                            break;
                        case PAGINA_ANTERIOR:
                            if (this.paginaAtual > 0) {
                                this.paginaAtual--;
                            }
                            break;
                        case RETORNAR:
                            gerenciadorDeMenus.voltar();
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                } else {
                    try {
                        int opcaoNumerica = Integer.parseInt(entrada);
                        
                        if (opcaoNumerica == 0) {
                            opcaoNumerica = 10;
                        }

                        int indiceSelecionado = (this.paginaAtual * 10) + opcaoNumerica - 1;

                        if (opcaoNumerica >= 1 && opcaoNumerica <= (indiceFinal - indiceInicial) && indiceSelecionado < this.cursos.size()) {
                            Curso cursoSelecionado = this.cursos.get(indiceSelecionado);
                            gerenciadorDeMenus.irPara(new MenuDetalhesCurso(cursoSelecionado));
                        } else {
                            System.out.println("Opção numérica inválida. Tente novamente.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Por favor, digite uma opção válida.");
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("[ERRO] - Não foi possível exibir a lista de cursos.");
        }
    }

    @Override
    public String getTitulo() {
        return "Lista de Cursos";
    }

}
