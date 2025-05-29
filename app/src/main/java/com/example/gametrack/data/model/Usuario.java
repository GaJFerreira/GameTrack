package com.example.gametrack.data.model;

public class Usuario {

    private long id;
    private String email;
    private String senha;
    private String nome;
    private String steamId;
    private String imagemPerfil; //url

    public Usuario() {
    }

    public Usuario(long id, String nome, String steamId, String imagemPerfil, String senha, String email) {
        this.id = id;
        this.nome = nome;
        this.steamId = steamId;
        this.imagemPerfil = imagemPerfil;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", steamId=" + steamId +
                ", imagemPerfil='" + imagemPerfil + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
