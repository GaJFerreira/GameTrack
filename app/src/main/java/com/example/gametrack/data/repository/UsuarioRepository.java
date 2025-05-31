package com.example.gametrack.data.repository;

import android.content.Context;

import com.example.gametrack.data.dao.UsuarioDao;
import com.example.gametrack.data.interfaces.iUsuario;
import com.example.gametrack.data.model.local.Usuario;

import java.util.List;

public class UsuarioRepository {

    private final iUsuario usuarioDao;

    public UsuarioRepository(Context context) {
        this.usuarioDao = new UsuarioDao(context);
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioDao.salvarUsuario(usuario);
    }

    public void atualizarUsuario(Usuario usuario) {
        usuarioDao.atualizarUsuario(usuario);
    }

    public void excluirUsuario(long id) {
        usuarioDao.excluirUsuario(id);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioDao.buscarUsuarioPorEmail(email);
    }

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioDao.buscarTodosUsuarios();
    }
}
