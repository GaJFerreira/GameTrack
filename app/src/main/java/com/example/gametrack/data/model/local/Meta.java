package com.example.gametrack.data.model.local;

import androidx.annotation.NonNull;

public class Meta {

    private long id;
    private Jogo jogo;
    private Usuario usuario;
    private Tipo tipo;
    private String valorMeta;
    private String dataLimite;
    private Prioridade prioridade;
    private String observacao;

    public Meta() {
    }

    public Meta(Tipo tipo, String valorMeta, String dataLimite, Prioridade prioridade, String observacao, Jogo jogo, Usuario usuario) {
        this.tipo = tipo;
        this.valorMeta = valorMeta;
        this.dataLimite = dataLimite;
        this.prioridade = prioridade;
        this.observacao = observacao;
        this.jogo = jogo;
        this.usuario = usuario;
    }

    public Meta(long id, Tipo tipo, String valorMeta, String dataLimite, Prioridade prioridade, String observacao, Jogo jogo, Usuario usuario) {
        this.id = id;
        this.tipo = tipo;
        this.valorMeta = valorMeta;
        this.dataLimite = dataLimite;
        this.prioridade = prioridade;
        this.observacao = observacao;
        this.jogo = jogo;
        this.usuario = usuario;
    }

    public enum Tipo {
        TEMPO("Tempo"),
        CONCLUSAO_PRINCIPAL("Conclusão da historia principal"),
        CONLUSAO_TOTAL("Conclusao de 100%"),
        PLATINA("Conclusão de todas as conquistas");

        private final String descricao;
        Tipo(String descricao) {
            this.descricao = descricao;
        }
        public String getDescricao() {
            return descricao;
        }
    }

    public enum Prioridade {
        BAIXA("Baixa"),
        MEDIA("Media"),
        ALTA("Alta");

        private final String descricao;

        Prioridade(String getDescricao) {
            this.descricao = getDescricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    // Getters e setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Jogo getJogo() {
        return jogo;
    }
    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
                ", jogo=" + (jogo != null ? jogo.toString() : "null") +
                ", usuario=" + (usuario != null ? usuario.toString() : "null") +
                ", tipo=" + tipo +
                ", valorMeta='" + valorMeta + '\'' +
                ", dataLimite='" + dataLimite + '\'' +
                ", prioridade=" + prioridade +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
