package InterfaceGrafica.Opcoes.Curso;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoCurso implements IOpcaoMenu {
    /*
    (A) Gerenciar inscritos no curso
    (B) Corrigir dados do curso
    (C) Encerrar inscrições
    (D) Concluir curso
    (E) Cancelar curso

    (R) Retornar ao menu anterior 
    */
    GERENCIAR_INSCRITOS("A", "Gerenciar inscritos no curso"),
    CORRIGIR_DADOS("B", "Corrigir dados do curso"),
    ENCERRAR_INSCRICOES("C", "Encerrar inscrições"),
    CONCLUIR_CURSO("D", "Concluir curso"),
    CANCELAR_CURSO("E", "Cancelar curso"),
    RETORNAR("R", "Retornar ao menu anterior");

    private final String codigo;
    private final String descricao;

    OpcaoCurso(String codigo, String descricao) {
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