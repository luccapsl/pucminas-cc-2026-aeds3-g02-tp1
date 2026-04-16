package InterfaceGrafica.Opcoes.Curso;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoAlterarCurso implements IOpcaoMenu {
    ALTERAR_NOME("A", "Alterar nome do curso"),
    ALTERAR_DESCRICAO("B", "Alterar descrição do curso"),
    ALTERAR_DATA_INICIO("C", "Alterar data de início"),
    RETORNAR("R", "Retornar ao menu anterior");

    private final String codigo;
    private final String descricao;

    OpcaoAlterarCurso(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }
}
