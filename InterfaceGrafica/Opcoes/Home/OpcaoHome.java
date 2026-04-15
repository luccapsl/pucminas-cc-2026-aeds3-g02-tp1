package InterfaceGrafica.Opcoes.Home;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoHome implements IOpcaoMenu {
    MEUS_DADOS("A", "Meus dados"),
    MEUS_CURSOS("B", "Meus cursos"),
    MINHAS_INSCRICOES("C", "Minhas inscrições"), 
    SAIR("S", "Sair");

    private final String codigo;
    private final String descricao;

    OpcaoHome(String codigo, String descricao) {
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

