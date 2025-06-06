package com.example.gametrack.data.model.local;

import androidx.annotation.NonNull;

public class Meta {

    private long id;
    private Tipo tipo;
    private String valorMeta;
    private String dataLimite;
    private Prioridade prioridade;
    private String observacao;

    public Meta() {
    }

    public Meta(Tipo tipo, String valorMeta, String dataLimite, Prioridade prioridade, String observacao) {
        this.tipo = tipo;
        this.valorMeta = valorMeta;
        this.dataLimite = dataLimite;
        this.prioridade = prioridade;
        this.observacao = observacao;
    }

    public Meta(long id, Tipo tipo, String valorMeta, String dataLimite, Prioridade prioridade, String observacao) {
        this.id = id;
        this.tipo = tipo;
        this.valorMeta = valorMeta;
        this.dataLimite = dataLimite;
        this.prioridade = prioridade;
        this.observacao = observacao;
    }

    public static enum Tipo{
        TEMPO("Tempo"),
        CONCLUSAO_PRINCIPAL("Conclusão da historia principal"),
        CONLUSAO_TOTAL("Conclusao de 100%"),
        PLATINA("Conclusão de todas as conquistas");

        private final String descricao;
        Tipo(String descricao) { this.descricao = descricao; }
        public String getDescricao() { return descricao; }
    }

    public static enum Prioridade{
        BAIXA("Baixa"),
        MEDIA("Media"),
        ALTA("Alta");

        private final String descricao;

        Prioridade(String getDescricao){
            this.descricao = getDescricao;
        }
        public String getDescricao() {
            return descricao;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(String valorMeta) {
        this.valorMeta = valorMeta;
    }

    public String getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(String dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @NonNull
    @Override
    public String toString() {
        return "Meta{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", valorMeta='" + valorMeta + '\'' +
                ", dataLimite='" + dataLimite + '\'' +
                ", prioridade=" + prioridade +
                ", observacao='" + observacao +
                '}';
    }
}
