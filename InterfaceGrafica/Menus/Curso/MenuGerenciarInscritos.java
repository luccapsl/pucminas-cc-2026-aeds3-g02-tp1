package InterfaceGrafica.Menus.Curso;

import java.util.Scanner;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;

import CRUD.CrudCursoUsuario;
import CRUD.CrudUsuario;
import Entidades.Curso;
import Entidades.CursoUsuario;
import Entidades.Usuario;
import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.IMenu;
import InterfaceGrafica.Menus.MenuUtils;
import InterfaceGrafica.Opcoes.Curso.OpcaoGerenciarInscritos;

public class MenuGerenciarInscritos implements IMenu {

    private Curso curso;

    public MenuGerenciarInscritos(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String getTitulo() {
        return "Gerenciar Inscritos";
    }

    @Override
    public void show(GerenciadorDeMenus gerenciadorDeMenus, Scanner scanner) {

        System.out.println();

        try {
            CrudCursoUsuario crudCursoUsuario = new CrudCursoUsuario();
            CrudUsuario crudUsuario = new CrudUsuario();
            ArrayList<CursoUsuario> inscricoes = crudCursoUsuario.readAllByCurso(this.curso.getId());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (int i = 0; i < inscricoes.size(); i++) {
                CursoUsuario cu = inscricoes.get(i);
                Usuario u = crudUsuario.read(cu.getIdUsuario());
                if (u != null) {
                    System.out.println("(" + (i + 1) + ") " + u.getNome() + " (" + sdf.format(cu.getDataInscricao()) + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar inscritos: " + e.getMessage());
        }
        System.out.println();

        MenuUtils.exibir(OpcaoGerenciarInscritos.class);
        String entrada = scanner.nextLine().trim();

        try {
            int numero = Integer.parseInt(entrada);
            if (numero > 0) {
                CrudCursoUsuario crudCursoUsuarioVer = new CrudCursoUsuario();
                CrudUsuario crudUsuarioVer = new CrudUsuario();
                ArrayList<CursoUsuario> inscricoesVer = crudCursoUsuarioVer.readAllByCurso(this.curso.getId());
                
                if (numero <= inscricoesVer.size()) {
                    CursoUsuario cuVer = inscricoesVer.get(numero - 1);
                    Usuario uVer = crudUsuarioVer.read(cuVer.getIdUsuario());
                    if (uVer != null) {
                        gerenciadorDeMenus.irPara(new MenuVerDadosInscrito(uVer, cuVer));
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                } else {
                    System.out.println("Número de inscrito inválido.");
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
            return;
        } catch (NumberFormatException e) {
            // Não é um número, segue para interpretar como opção do menu
        } catch (Exception e) {
            System.out.println("Erro ao carregar inscrito: " + e.getMessage());
            return;
        }

        OpcaoGerenciarInscritos opcao = MenuUtils.interpretar(entrada, OpcaoGerenciarInscritos.class);

        if (opcao == null) {
            System.out.println("Opção inválida. Tente novamente.");
            return;
        }

        switch (opcao) {

            case EXPORTAR_INSCRITOS:
                System.out.println("Exportando inscritos...");

            try {
                CrudCursoUsuario crudCursoUsuario = new CrudCursoUsuario();
                CrudUsuario crudUsuario = new CrudUsuario();

                ArrayList<CursoUsuario> inscricoes =
                        crudCursoUsuario.readAllByCurso(this.curso.getId());

                String nomeArquivo = "inscritos_curso_" + this.curso.getId() + ".csv";

                BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo));

                writer.write("Nome,Email");
                writer.newLine();

                for (CursoUsuario cu : inscricoes) {
                    Usuario u = crudUsuario.read(cu.getIdUsuario());

                    if (u != null) {
                        writer.write(u.getNome() + "," + u.getEmail());
                        writer.newLine();
                    }
                }

                writer.close();

                System.out.println("Arquivo exportado com sucesso: " + nomeArquivo);

            } catch (Exception e) {
                System.out.println("Erro ao exportar inscritos: " + e.getMessage());
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