package com.example.gametrack.data.model.local;

public class JogoLayout {
    private final long appSteamId;
    private final String titulo;
    private final String imagemUrl;

    public JogoLayout(long id, String titulo, String imagemUrl) {
        this.appSteamId = id;
        this.titulo = titulo;
        this.imagemUrl = imagemUrl;
    }

    public long getAppSteamId() {
        return appSteamId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
}
