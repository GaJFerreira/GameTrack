package com.example.gametrack.data.model;

import androidx.annotation.NonNull;

public class Usuario {

    private long id;
    private String nome;
    private String steamId;
    private String imagemPerfil; //url

    public Usuario(long id, String nome, String steamId, String imagemPerfil) {
        this.id = id;
        this.nome = nome;
        this.steamId = steamId;
        this.imagemPerfil = imagemPerfil;
    }

    public Usuario(String nome, String steamId, String imagemPerfil) {
        this.nome = nome;
        this.steamId = steamId;
        this.imagemPerfil = imagemPerfil;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }

    @NonNull
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", steamId=" + steamId +
                ", imagemPerfil='" + imagemPerfil + '\'' +
                '}';
    }
}
