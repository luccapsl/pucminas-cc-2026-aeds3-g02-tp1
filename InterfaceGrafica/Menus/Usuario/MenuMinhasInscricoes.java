package InterfaceGrafica.Menus.Usuario;

import java.util.Scanner;
import java.util.ArrayList;

import InterfaceGrafica.Controles.Usuario.ControleMinhasInscricoes;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Usuario.OpcaoMinhasInscricoes;

import Entidades.Curso;

public class MenuMinhasInscricoes implements IMenu {
    private ControleMinhasInscricoes controleMinhasInscricoes;

    public MenuMinhasInscricoes() {
        this.controleMinhasInscricoes = new ControleMinhasInscricoes();
    }

    @Override
    public String getTitulo() {
        return "Minhas Inscrições";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {
        this.controleMinhasInscricoes.exibirMinhasInscricoes(gerenciadorDeMenus.getUsuarioLogado());
        OpcaoMinhasInscricoes opcaoMinhasInscricoes = MenuUtils.interacaoMenu(OpcaoMinhasInscricoes.class, scanner);
        if (opcaoMinhasInscricoes == null) return;
        switch (opcaoMinhasInscricoes) {
            case BUSCAR:
                try {
                    controleMinhasInscricoes.buscarPorCodigo(scanner, gerenciadorDeMenus);
                } catch (Exception e) {
                    System.out.println("Erro ao buscar curso: " + e.getMessage());
                }
                break;
            case LISTAR_CURSOS:
                try {
                    controleMinhasInscricoes.listarTodosCursos(gerenciadorDeMenus);
                } catch (Exception e) {
                    System.out.println("Erro ao listar cursos: " + e.getMessage());
                }
                break;
            case INSCREVER:
                try {
                    ArrayList<Curso> cursosDisponiveis = controleMinhasInscricoes.obterCursosDisponiveis(gerenciadorDeMenus.getUsuarioLogado());
                    
                    if (cursosDisponiveis == null || cursosDisponiveis.isEmpty()) {
                        System.out.println("Não há nenhum curso disponível.");
                        break;
                    }

                    System.out.println("==== CURSOS DISPONÍVEIS ====");
                    System.out.println("ID - NOME");
                    for (int i = 0; i < cursosDisponiveis.size(); i++) {
                        Curso cursoAtual = cursosDisponiveis.get(i);

                        System.out.printf("(%d) - %s\n", cursoAtual.getId(), cursoAtual.getNome());
                    }
                    System.out.println("============================");
                    System.err.println("Digite o ID do curso para se inscrever (ou 0 para voltar): ");
                    String idCursoString = scanner.nextLine();
                    int idCurso = Integer.parseInt(idCursoString);
                    if (idCurso != 0) {
                        controleMinhasInscricoes.efetivarInscricao(idCurso, gerenciadorDeMenus.getUsuarioLogado());
                    }

                } catch (Exception e) {
                    System.out.println("Erro ao buscar cursos: " + e.getMessage());
                }
                break;
            case CANCELAR:
                if (!controleMinhasInscricoes.possuiInscricoes(gerenciadorDeMenus.getUsuarioLogado())) {
                    System.out.println("Você ainda não possuí inscrições para cancelar.");
                    break;
                }

                try {
                    System.out.print("Digite o ID do curso que deseja cancelar: ");
                    String idCursoString = scanner.nextLine();
                    int idCurso = Integer.parseInt(idCursoString);
                    controleMinhasInscricoes.cancelarInscricao(idCurso, gerenciadorDeMenus.getUsuarioLogado());
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Por favor, digite um número de ID válido. ");
                } catch (Exception e) {
                    System.out.println("Erro ao cancelar curso: " + e.getMessage());
                }
                break;
            case RETORNAR:
                gerenciadorDeMenus.voltar();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }
}