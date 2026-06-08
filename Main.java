import java.util.Scanner;

import InterfaceGrafica.Menus.GerenciadorDeMenus;
import InterfaceGrafica.Menus.Usuario.MenuAuth;

/* */
import CRUD.CrudCurso;
import CRUD.CrudUsuario;
import Entidades.Curso;
import Entidades.Usuario;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GerenciadorDeMenus gerenciador = new GerenciadorDeMenus(new MenuAuth());

        try {
            gerenciador.iniciar(scanner);
        } catch (Exception e) {
            System.err.println("Erro crítico no sistema: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // public static void main(String[] args) {
    // // === TESTE TF-IDF ===
    // try {
    //     CrudUsuario crudUsuario = new CrudUsuario();
    //     CrudCurso crudCurso = new CrudCurso();
        
    //     int idUser = crudUsuario.create(new Usuario("Teste", "teste@teste.com", "123", "Perg?", "Resp"));
        
    //     crudCurso.create(new Curso("Inteligência Artificial e a mente", '0', new Date(), "", idUser));
    //     crudCurso.create(new Curso("Curso básico", '0', new Date(), "", idUser));
    //     crudCurso.create(new Curso("A Inteligência nas coisas", '0', new Date(), "", idUser));
        
    //     System.out.println("\nBuscando por: 'Inteligência Artificial'");
    //     ArrayList<Curso> resultados = crudCurso.buscarPorPalavrasChave("Inteligência Artificial");
        
    //     int posicao = 1;
    //     for (Curso c : resultados) {
    //         System.out.println(posicao + "º lugar: " + c.getNome());
    //         posicao++;
    //     }
        
    // } catch (Exception e) {
    //     e.printStackTrace();
    // }
    // // === FIM DO TESTE ===
    // }
}