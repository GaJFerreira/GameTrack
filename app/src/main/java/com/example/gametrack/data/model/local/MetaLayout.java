package com.example.gametrack.data.model.local;

public class MetaLayout {
    private String titulo;
    private String descricao;
    private String iconeJogo;
    private long idMeta;

    public MetaLayout(String titulo, String descricao, String iconeJogo, long idMeta) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.iconeJogo = iconeJogo;
        this.idMeta = idMeta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIconeJogo() {
        return iconeJogo;
    }

    public void setIconeJogo(String iconeJogo) {
        this.iconeJogo = iconeJogo;
    }

    public long getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(long idMeta) {
        this.idMeta = idMeta;
    }
}
