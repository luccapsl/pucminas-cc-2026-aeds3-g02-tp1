package InterfaceGrafica.Opcoes.Usuario;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoAuth implements IOpcaoMenu {
    LOGIN("A", "Login"),
    CADASTRO("B", "Cadastro"),
    VOLTAR("S", "Sair");

    private final String codigo;
    private final String descricao;

    OpcaoAuth(String codigo, String descricao) {
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
