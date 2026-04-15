package InterfaceGrafica.Opcoes.Usuario;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoMeusCursos implements IOpcaoMenu {
    NOVO_CURSO("A", "Novo curso"),
    RETORNAR("R", "Retornar ao menu anterior");

    private final String codigo;
    private final String descricao;

    OpcaoMeusCursos(String codigo, String descricao) {
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
