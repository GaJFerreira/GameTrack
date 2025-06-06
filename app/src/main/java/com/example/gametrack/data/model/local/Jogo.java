package com.example.gametrack.data.model.local;

import androidx.annotation.NonNull;

public class Jogo {
    private long id;
    private String titulo;
    private long appSteamId;
    private String icone;

    public Jogo(long id, String titulo,  long appSteamId, String icone) {
        this.id = id;
        this.titulo = titulo;
        this.appSteamId = appSteamId;
        this.icone = icone;
    }

    public Jogo(String titulo, long appSteamId, String icone) {
        this.titulo = titulo;
        this.appSteamId = appSteamId;
        this.icone = icone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getAppSteamId() {
        return appSteamId;
    }

    public void setAppSteamId(long appSteamId) {
        this.appSteamId = appSteamId;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    @NonNull
    @Override
    public String toString() {
        return "Jogo{" +
                "appSteamId='" + appSteamId + '\'' +
                ", id=" + id +
                ", titulo='" + titulo + '\'' +
                ", icone='" + icone +
                '}';
    }
}
