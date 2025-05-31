package com.example.gametrack.data.interfaces;

import com.example.gametrack.data.model.Usuario;

import java.util.List;

public interface iUsuario {

    void salvarUsuario(Usuario usuario);

    void atualizarUsuario(Usuario usuario);

    void excluirUsuario(long id);

    Usuario buscarUsuarioPorId(long id);

    List<Usuario> buscarTodosUsuarios();
}
