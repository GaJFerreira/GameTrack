package com.example.gametrack.data.model.local;

public class Biblioteca {

    private long id;
    private long usuarioID;
    private long jogoID;
    private String plataforma;
    private String dataCompra;
    private String loja;
    private Status status;
    private TipoCadastro tipoCadastro;
    private float notaPessoal;
    private float horasJogadas;
    private int conquistasObtidas;
    private NivelInteresse nivelInteresse;

    public static enum Status {
        NAO_INICIADO("Não Iniciado"),
        JOGANDO("Jogando"),
        PAUSADO("Pausado"),
        FINALIZADO("Finalizado"),
        ABANDONADO("Abandonado");

        private final String descricao;
        Status(String descricao) { this.descricao = descricao; }
        public String getDescricao() { return descricao; }
    }

    public static enum TipoCadastro {
        STEAM("Steam"),
        MANUAL("Manual");

        private final String descricao;
        TipoCadastro(String descricao) { this.descricao = descricao; }
        public String getDescricao() { return descricao; }
    }

    public static enum NivelInteresse {
        ALTO("Alto"),
        MEDIO("Médio"),
        BAIXO("Baixo"),
        NENHUM("Nenhum");

        private final String descricao;
        NivelInteresse(String descricao) { this.descricao = descricao; }
        public String getDescricao() { return descricao; }
    }

    public Biblioteca() {
    }

    public Biblioteca(int conquistasObtidas, String dataCompra, float horasJogadas, long id, long jogoID, String loja, NivelInteresse nivelInteresse, float notaPessoal, String plataforma, Status status, TipoCadastro tipoCadastro, long usuarioID) {
        this.conquistasObtidas = conquistasObtidas;
        this.dataCompra = dataCompra;
        this.horasJogadas = horasJogadas;
        this.id = id;
        this.jogoID = jogoID;
        this.loja = loja;
        this.nivelInteresse = nivelInteresse;
        this.notaPessoal = notaPessoal;
        this.plataforma = plataforma;
        this.status = status;
        this.tipoCadastro = tipoCadastro;
        this.usuarioID = usuarioID;
    }

    public int getConquistasObtidas() {
        return conquistasObtidas;
    }

    public void setConquistasObtidas(int conquistasObtidas) {
        this.conquistasObtidas = conquistasObtidas;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public float getHorasJogadas() {
        return horasJogadas;
    }

    public void setHorasJogadas(float horasJogadas) {
        this.horasJogadas = horasJogadas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getJogoID() {
        return jogoID;
    }

    public void setJogoID(long jogoID) {
        this.jogoID = jogoID;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public NivelInteresse getNivelInteresse() {
        return nivelInteresse;
    }

    public void setNivelInteresse(NivelInteresse nivelInteresse) {
        this.nivelInteresse = nivelInteresse;
    }

    public float getNotaPessoal() {
        return notaPessoal;
    }

    public void setNotaPessoal(float notaPessoal) {
        this.notaPessoal = notaPessoal;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TipoCadastro getTipoCadastro() {
        return tipoCadastro;
    }

    public void setTipoCadastro(TipoCadastro tipoCadastro) {
        this.tipoCadastro = tipoCadastro;
    }

    public long getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(long usuarioID) {
        this.usuarioID = usuarioID;
    }
}
