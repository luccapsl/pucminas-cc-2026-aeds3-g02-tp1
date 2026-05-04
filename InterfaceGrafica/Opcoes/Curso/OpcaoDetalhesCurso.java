package InterfaceGrafica.Opcoes.Curso;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoDetalhesCurso implements IOpcaoMenu {
    INSCRICAO("A", "Fazer minha inscrição no curso"),
    RETORNAR("R", "Retornar ao menu anterior");

    private final String codigo;
    private final String descricao;

    OpcaoDetalhesCurso(String codigo, String descricao) {
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
