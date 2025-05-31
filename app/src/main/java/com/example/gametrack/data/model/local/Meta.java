package com.example.gametrack.data.model.local;

public class Meta {

    private long id;
    private long bibliotecaId;
    private Tipo tipo;
    private String valorMeta;
    private String progressoAtual;
    private String dataLimite;
    private Prioridade prioridade;
    private String observacao;
    private Status status;

    public Meta() {
    }

    public Meta(long id, long bibliotecaId, Tipo tipo, String valorMeta, String progressoAtual, String dataLimite, Prioridade prioridade, String observacao, Status status) {
        this.id = id;
        this.bibliotecaId = bibliotecaId;
        this.tipo = tipo;
        this.valorMeta = valorMeta;
        this.progressoAtual = progressoAtual;
        this.dataLimite = dataLimite;
        this.prioridade = prioridade;
        this.observacao = observacao;
        this.status = status;
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

    public static enum Status{
        EM_ANDAMENTO("Em andamento"),
        CONCLUIDO("Concluido"),
        EXPIRADO("Expirado");

        private final String descricao;

        Status(String getDescricao){
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

    public long getBibliotecaId() {
        return bibliotecaId;
    }

    public void setBibliotecaId(long bibliotecaId) {
        this.bibliotecaId = bibliotecaId;
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

    public String getProgressoAtual() {
        return progressoAtual;
    }

    public void setProgressoAtual(String progressoAtual) {
        this.progressoAtual = progressoAtual;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "id=" + id +
                ", bibliotecaId=" + bibliotecaId +
                ", tipo=" + tipo +
                ", valorMeta='" + valorMeta + '\'' +
                ", progressoAtual='" + progressoAtual + '\'' +
                ", dataLimite='" + dataLimite + '\'' +
                ", prioridade=" + prioridade +
                ", observacao='" + observacao + '\'' +
                ", status=" + status +
                '}';
    }
}
