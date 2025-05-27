package com.example.gametrack.entidades;

public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private String steamId;

    public Usuario() {

    }

    public Usuario(String email, long id, String nome, String senha, String steamId) {
        this.email = email;
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.steamId = steamId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    @Override
    public String toString() {
        return "usuario{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", steamId='" + steamId + '\'' +
                '}';
    }
}
