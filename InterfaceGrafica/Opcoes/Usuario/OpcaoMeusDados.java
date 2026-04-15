package InterfaceGrafica.Opcoes.Usuario;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoMeusDados implements IOpcaoMenu {
    ALTERAR_NOME("A", "Alterar nome"),
    ALTERAR_EMAIL("B", "Alterar email"),
    ALTERAR_SENHA("C", "Alterar senha"),
    RETORNAR("R", "Retornar ao menu anterior");

    private final String codigo;
    private final String descricao;

    OpcaoMeusDados(String codigo, String descricao) {
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
