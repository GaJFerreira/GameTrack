package com.example.gametrack.entidades;

public class Meta {

    private long id;
    private long bibliotecaId;
    private String valorMeta;

    private String progressoAtual;


    public static enum Tipo{
        TEMPO("Tempo"),
        CONCLUSAO_PRINCIPAL("Conclusão da historia principal"),
        CONLUSAO_TOTAO("Conclusao de 100%"),
        PLATINA("Conclusão de todas as conquistas");

        private final String descricao;
        Tipo(String descricao) { this.descricao = descricao; }
        public String getDescricao() { return descricao; }
    }

}
