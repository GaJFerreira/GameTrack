package com.example.gametrack.entidades;

public class Jogo {

 private long id;
 private String titulo;
 private String plataforma;
 private String appSteamId;
 private String genero;
 private String desenvolvedor;
 private String publisher;
 private String descricao;
 private String capa;//url
    private String icone;//url
    private String dtLancamento;
    private int conquistasTotais;

    public Jogo(){

    }

    public Jogo(String appSteamId, String capa, int conquistasTotais, String descricao, String desenvolvedor, String dtLancamento, String genero, String icone, long id, String plataforma, String publisher, String titulo) {
        this.appSteamId = appSteamId;
        this.capa = capa;
        this.conquistasTotais = conquistasTotais;
        this.descricao = descricao;
        this.desenvolvedor = desenvolvedor;
        this.dtLancamento = dtLancamento;
        this.genero = genero;
        this.icone = icone;
        this.id = id;
        this.plataforma = plataforma;
        this.publisher = publisher;
        this.titulo = titulo;
    }

    public String getAppSteamId() {
        return appSteamId;
    }

    public void setAppSteamId(String appSteamId) {
        this.appSteamId = appSteamId;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public int getConquistasTotais() {
        return conquistasTotais;
    }

    public void setConquistasTotais(int conquistasTotais) {
        this.conquistasTotais = conquistasTotais;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(String desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public String getDtLancamento() {
        return dtLancamento;
    }

    public void setDtLancamento(String dtLancamento) {
        this.dtLancamento = dtLancamento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Jogo{" +
                "appSteamId='" + appSteamId + '\'' +
                ", id=" + id +
                ", titulo='" + titulo + '\'' +
                ", plataforma='" + plataforma + '\'' +
                ", genero='" + genero + '\'' +
                ", desenvolvedor='" + desenvolvedor + '\'' +
                ", publisher='" + publisher + '\'' +
                ", descricao='" + descricao + '\'' +
                ", capa='" + capa + '\'' +
                ", icone='" + icone + '\'' +
                ", dtLancamento='" + dtLancamento + '\'' +
                ", conquistasTotais=" + conquistasTotais +
                '}';
    }
}
