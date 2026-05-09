package InterfaceGrafica.Opcoes.Usuario;

import InterfaceGrafica.Opcoes.IOpcaoMenu;

public enum OpcaoMinhasInscricoes implements IOpcaoMenu {
    BUSCAR("A", "Buscar curso por código"),
    //BUSCAR_PALAVRAS_CHAVE("B", "Buscar curso por palavras-chave"), 
    LISTAR_CURSOS("C", "Listar todos os cursos"),
    INSCREVER("I", "Inscrever-se em um novo curso"),
    RETORNAR("R", "Retornar ao menu anterior"),
    CANCELAR("X", "Cancelar uma inscrição");

    private final String codigo;
    private final String descricao;

    OpcaoMinhasInscricoes(String codigo, String descricao) {
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