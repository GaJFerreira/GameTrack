package com.example.gametrack.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gametrack.data.interfaces.iUsuario;
import com.example.gametrack.data.local.ConexaoDb;
import com.example.gametrack.data.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements iUsuario {

    private final SQLiteDatabase conexao;
    public UsuarioDao(Context context) {
        this.conexao = ConexaoDb.getInstance(context);
    }

    @Override
    public void salvarUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("steamId", usuario.getSteamId());
        values.put("imagemPerfil", "f"); // ou usuario.getImagemPerfil() se preferir

        conexao.insert("usuario", null, values);
    }

    @Override
    public void atualizarUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("steamId", usuario.getSteamId());
        values.put("imagemPerfil", usuario.getImagemPerfil());

        conexao.update("usuario", values, "id = ?", new String[]{String.valueOf(usuario.getId())});
    }

    @Override
    public void excluirUsuario(long id) {
        conexao.delete("usuario", "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public Usuario buscarUsuarioPorId(long id) {
        Cursor cursor = conexao.query(
                "usuario",
                new String[]{"id", "nome", "steamId", "imagemPerfil"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            usuario = new Usuario(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                    cursor.getString(cursor.getColumnIndexOrThrow("steamId")),
                    cursor.getString(cursor.getColumnIndexOrThrow("imagemPerfil"))
            );
        }
        cursor.close();
        return usuario;
    }

    @Override
    public List<Usuario> buscarTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        Cursor cursor = conexao.query(
                "usuario",
                new String[]{"id", "nome", "steamId", "imagemPerfil"},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        cursor.getString(cursor.getColumnIndexOrThrow("steamId")),
                        cursor.getString(cursor.getColumnIndexOrThrow("imagemPerfil"))
                );
                usuarios.add(usuario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return usuarios;
    }
}
