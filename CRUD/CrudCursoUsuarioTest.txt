package CRUD;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Entidades.Curso;
import Entidades.CursoUsuario;
import Entidades.Usuario;

/**
 * Testes JUnit 5 para o CrudCursoUsuario (relacionamento N:N).
 * 
 * Testa todas as operações CRUD e a integridade das duas árvores B+:
 *   - (idCurso, idCursoUsuario)
 *   - (idUsuario, idCursoUsuario)
 * 
 * Para rodar: mvn test -Dtest=CrudCursoUsuarioTest
 * Ou todos:   mvn test
 */
public class CrudCursoUsuarioTest {

    CrudUsuario crudUsuario;
    CrudCurso crudCurso;
    CrudCursoUsuario crudCursoUsuario;

    int idU1, idU2;
    int idC1, idC2, idC3;

    /**
     * Limpa os dados antigos e inicializa os CRUDs e dados base (usuários e cursos).
     * Cada teste começa com um estado limpo.
     */
    @BeforeEach
    void setUp() throws Exception {
        deleteDir(new File("." + File.separator + "dados"));

        crudUsuario = new CrudUsuario();
        crudCurso = new CrudCurso();
        crudCursoUsuario = new CrudCursoUsuario();

        // Criar usuários de teste
        Usuario u1 = new Usuario("Alice", "alice@teste.com", "senha123", "Cor?", "Azul");
        Usuario u2 = new Usuario("Bob", "bob@teste.com", "senha456", "Pet?", "Rex");
        idU1 = crudUsuario.create(u1);
        idU2 = crudUsuario.create(u2);

        // Criar cursos de teste
        Curso c1 = new Curso("Java Avançado", '0', new Date(), "Curso de Java", idU1);
        Curso c2 = new Curso("Python Básico", '0', new Date(), "Curso de Python", idU1);
        Curso c3 = new Curso("Banco de Dados", '0', new Date(), "Curso de BD", idU2);
        idC1 = crudCurso.create(c1);
        idC2 = crudCurso.create(c2);
        idC3 = crudCurso.create(c3);
    }

    // ============= Helpers =============

    /**
     * Cria as 5 inscrições padrão usadas na maioria dos testes.
     * Alice -> Java, Python, BD
     * Bob   -> Java, BD
     */
    private int[] criarInscricoesPadrao() throws Exception {
        int idCU1 = crudCursoUsuario.create(new CursoUsuario(idC1, idU1, new Date())); // Alice em Java
        int idCU2 = crudCursoUsuario.create(new CursoUsuario(idC2, idU1, new Date())); // Alice em Python
        int idCU3 = crudCursoUsuario.create(new CursoUsuario(idC1, idU2, new Date())); // Bob em Java
        int idCU4 = crudCursoUsuario.create(new CursoUsuario(idC3, idU2, new Date())); // Bob em BD
        int idCU5 = crudCursoUsuario.create(new CursoUsuario(idC3, idU1, new Date())); // Alice em BD
        return new int[]{idCU1, idCU2, idCU3, idCU4, idCU5};
    }

    // ==================== CREATE ====================

    @Test
    void testCreate_deveRetornarIdsPositivos() throws Exception {
        int[] ids = criarInscricoesPadrao();

        for (int i = 0; i < ids.length; i++) {
            assertTrue(ids[i] > 0, "ID da inscrição " + (i + 1) + " deve ser positivo");
        }
    }

    // ==================== READ ====================

    @Test
    void testRead_porIdExistente_deveRetornarRegistroCorreto() throws Exception {
        int[] ids = criarInscricoesPadrao();

        CursoUsuario lido = crudCursoUsuario.read(ids[0]); // Alice em Java

        assertNotNull(lido, "Registro deveria ser encontrado");
        assertEquals(idC1, lido.getIdCurso(), "idCurso deveria ser o do curso Java");
        assertEquals(idU1, lido.getIdUsuario(), "idUsuario deveria ser o da Alice");
    }

    @Test
    void testRead_porIdInexistente_deveRetornarNull() throws Exception {
        CursoUsuario lido = crudCursoUsuario.read(9999);
        assertNull(lido, "Registro inexistente deveria retornar null");
    }

    // ==================== READ ALL BY CURSO ====================

    @Test
    void testReadAllByCurso_cursoJavaComDoisInscritos() throws Exception {
        criarInscricoesPadrao();

        ArrayList<CursoUsuario> inscritos = crudCursoUsuario.readAllByCurso(idC1);

        assertEquals(2, inscritos.size(), "Java Avançado deveria ter 2 inscritos (Alice e Bob)");
    }

    @Test
    void testReadAllByCurso_cursoBDComDoisInscritos() throws Exception {
        criarInscricoesPadrao();

        ArrayList<CursoUsuario> inscritos = crudCursoUsuario.readAllByCurso(idC3);

        assertEquals(2, inscritos.size(), "Banco de Dados deveria ter 2 inscritos");
    }

    @Test
    void testReadAllByCurso_cursoInexistente_deveRetornarListaVazia() throws Exception {
        ArrayList<CursoUsuario> inscritos = crudCursoUsuario.readAllByCurso(9999);

        assertTrue(inscritos.isEmpty(), "Curso inexistente deveria retornar lista vazia");
    }

    // ==================== READ ALL BY USUARIO ====================

    @Test
    void testReadAllByUsuario_aliceComTresInscricoes() throws Exception {
        criarInscricoesPadrao();

        ArrayList<CursoUsuario> cursos = crudCursoUsuario.readAllByUsuario(idU1);

        assertEquals(3, cursos.size(), "Alice deveria estar inscrita em 3 cursos");
    }

    @Test
    void testReadAllByUsuario_bobComDuasInscricoes() throws Exception {
        criarInscricoesPadrao();

        ArrayList<CursoUsuario> cursos = crudCursoUsuario.readAllByUsuario(idU2);

        assertEquals(2, cursos.size(), "Bob deveria estar inscrito em 2 cursos");
    }

    @Test
    void testReadAllByUsuario_usuarioInexistente_deveRetornarListaVazia() throws Exception {
        ArrayList<CursoUsuario> cursos = crudCursoUsuario.readAllByUsuario(9999);

        assertTrue(cursos.isEmpty(), "Usuário inexistente deveria retornar lista vazia");
    }

    // ==================== UPDATE ====================

    @Test
    void testUpdate_deveAtualizarIdCursoEIndices() throws Exception {
        int[] ids = criarInscricoesPadrao();
        int idCU3 = ids[2]; // Bob em Java

        // Mover Bob de Java (idC1) para Python (idC2)
        CursoUsuario paraAtualizar = crudCursoUsuario.read(idCU3);
        assertNotNull(paraAtualizar);
        assertEquals(idC1, paraAtualizar.getIdCurso(), "Antes do update, deve ser Java");

        paraAtualizar.setIdCurso(idC2);
        boolean atualizado = crudCursoUsuario.update(paraAtualizar);
        assertTrue(atualizado, "Update deveria retornar true");

        // Verificar que o registro foi atualizado
        CursoUsuario aposUpdate = crudCursoUsuario.read(idCU3);
        assertEquals(idC2, aposUpdate.getIdCurso(), "Após update, deve ser Python");

        // Verificar que os índices da árvore B+ foram atualizados
        ArrayList<CursoUsuario> inscritosJava = crudCursoUsuario.readAllByCurso(idC1);
        assertEquals(1, inscritosJava.size(), "Java deveria ter 1 inscrito após update");

        ArrayList<CursoUsuario> inscritosPython = crudCursoUsuario.readAllByCurso(idC2);
        assertEquals(2, inscritosPython.size(), "Python deveria ter 2 inscritos após update");
    }

    // ==================== DELETE ====================

    @Test
    void testDelete_deveRemoverRegistroEAtualizarIndices() throws Exception {
        int[] ids = criarInscricoesPadrao();
        int idCU5 = ids[4]; // Alice em BD

        // Deletar inscrição
        boolean deletado = crudCursoUsuario.delete(idCU5);
        assertTrue(deletado, "Delete deveria retornar true");
        assertNull(crudCursoUsuario.read(idCU5), "Registro deveria ser null após delete");

        // Verificar que os índices foram atualizados
        ArrayList<CursoUsuario> cursosAlice = crudCursoUsuario.readAllByUsuario(idU1);
        assertEquals(2, cursosAlice.size(), "Alice deveria ter 2 inscrições após delete");

        ArrayList<CursoUsuario> inscritosBD = crudCursoUsuario.readAllByCurso(idC3);
        assertEquals(1, inscritosBD.size(), "BD deveria ter 1 inscrito após delete");
    }

    @Test
    void testDelete_idInexistente_deveRetornarFalse() throws Exception {
        boolean deletado = crudCursoUsuario.delete(9999);

        assertFalse(deletado, "Delete de ID inexistente deveria retornar false");
    }

    // ==================== Utilitários ====================

    /**
     * Remove recursivamente um diretório e todos os seus arquivos.
     */
    static void deleteDir(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteDir(f);
                    } else {
                        f.delete();
                    }
                }
            }
            dir.delete();
        }
    }
}
