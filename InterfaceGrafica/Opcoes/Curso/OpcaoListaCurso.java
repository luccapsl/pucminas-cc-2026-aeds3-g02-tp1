package InterfaceGrafica.Opcoes.Curso;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoListaCurso implements IOpcaoMenu {
    PAGINA_ANTERIOR("A", "Página anterior"),
    PROXIMA_PAGINA("B", "Próxima página"),
    RETORNAR("R", "Retornar ao menu anterior");

    private final String codigo;
    private final String descricao;

    OpcaoListaCurso(String codigo, String descricao) {
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
