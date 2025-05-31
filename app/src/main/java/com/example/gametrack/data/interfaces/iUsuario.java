package com.example.gametrack.data.interfaces;

import com.example.gametrack.data.model.local.Usuario;

import java.util.List;

public interface iUsuario {

    void salvarUsuario(Usuario usuario);

    void atualizarUsuario(Usuario usuario);

    void excluirUsuario(long id);

    Usuario buscarUsuarioPorEmail(String email);

    List<Usuario> buscarTodosUsuarios();
}
