package InterfaceGrafica.Opcoes.Curso;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoVerDadosInscrito implements IOpcaoMenu {

    CANCELAR_INSCRICAO("C", "Cancelar inscrição do aluno"),
    RETORNAR("R", "Retornar ao menu anterior");

    private final String codigo;
    private final String descricao;

    OpcaoVerDadosInscrito(String codigo, String descricao) {
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
