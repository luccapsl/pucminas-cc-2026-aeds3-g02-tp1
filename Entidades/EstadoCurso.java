package Entidades;

public enum EstadoCurso {
    ATIVO_INSCRICOES('0', "Ativo - Recebendo inscrições"),
    ATIVO_SEM_INSCRICOES('1', "Ativo - Sem novas inscrições"),
    CONCLUIDO('2', "Concluído"),
    CANCELADO('3', "Cancelado");

    private final char codigo;
    private final String descricao;

    EstadoCurso(char codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public char getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o EstadoCurso correspondente ao código char.
     * Se não encontrar, retorna null.
     */
    public static EstadoCurso fromCodigo(char codigo) {
        for (EstadoCurso e : values()) {
            if (e.codigo == codigo) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
