package com.example.gametrack.dao;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gametrack.interfaces.iUsuario;
import com.example.gametrack.entidades.Usuario;
import com.example.gametrack.utils.ConexaoDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

public class UsuarioDao implements iUsuario {

    @Override
    public void salvarUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());

        SQLiteDatabase conexao = ConexaoDb.getInstance();
        conexao.insert("usuario", null, values);

    }
    @Override
    public void atualizarUsuario(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("id", usuario.getId());
        values.put("nome", usuario.getNome());
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        values.put("steamId", usuario.getSteamId());
        values.put("imagemPerfil", usuario.getImagemPerfil());

        SQLiteDatabase conexao = ConexaoDb.getInstance();
        conexao.update("usuario", values, "id = ?", new String[]{String.valueOf(usuario.getId())});
    }

    @Override
    public void excluirUsuario(long id) {
        ContentValues values = new ContentValues();

        SQLiteDatabase conexao = ConexaoDb.getInstance();
        conexao.delete("usuario", "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public Usuario buscarUsuarioPorId(long id) {
        SQLiteDatabase conexao = ConexaoDb.getInstance();

        String sql = "SELECT * FROM usuario WHERE id = ?";
        String[] args = {String.valueOf(id)};
        String[] colunas = {"id", "nome", "email", "senha", "steamId", "imagemPerfil"};

        Cursor cursor = conexao.query(
                "usuario",
                colunas,
                "id = ?",
                args,
                null,
                null,
                null
        );

        if (cursor != null){
            cursor.moveToFirst();
        }else {
            return null;
        }

        Usuario usuario = new Usuario(
                cursor.getLong(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("nome")),
                cursor.getString(cursor.getColumnIndex("steamId")),
                cursor.getString(cursor.getColumnIndex("imagemPerfil")),
                cursor.getString(cursor.getColumnIndex("senha")),
                cursor.getString(cursor.getColumnIndex("email"))

        );

        return usuario;
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        SQLiteDatabase conexao = ConexaoDb.getInstance();

        // Pequena observação: email já é String, String.valueOf(email) é redundante aqui.
        // Pode ser apenas: String[] args = {email};
        String[] args = {String.valueOf(email)};
        String[] colunas = {"id", "nome", "email", "senha", "steamId", "imagemPerfil"};

        Cursor cursor = null; // Declare fora para poder fechar no finally (melhor prática)
        Usuario usuario = null; // Inicializa como null

        try { // Bloco try para garantir o fechamento do cursor
            cursor = conexao.query(
                    "usuario",
                    colunas,
                    "email = ?", // Corrigido para buscar por email
                    args,
                    null,
                    null,
                    null
            );

            // A MUDANÇA MAIS IMPORTANTE ESTÁ AQUI:
            if (cursor != null && cursor.moveToFirst()) {
                // Se cursor não for nulo E moveToFirst() retornar true (ou seja, encontrou um registro),
                // então é seguro ler os dados.
                usuario = new Usuario(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nome")),
                        cursor.getString(cursor.getColumnIndex("steamId")),
                        cursor.getString(cursor.getColumnIndex("imagemPerfil")),
                        cursor.getString(cursor.getColumnIndex("senha")),
                        cursor.getString(cursor.getColumnIndex("email"))
                );
            }
            // Se cursor for nulo ou moveToFirst() retornar false, 'usuario' continuará null,
            // o que significa que nenhum usuário foi encontrado ou houve um problema.

        } catch (Exception e) {
            Log.e("UsuarioDao", "Erro ao buscar usuário por email: " + email, e);
            // 'usuario' permanecerá null em caso de erro.
        } finally {
            if (cursor != null) {
                cursor.close(); // ESSENCIAL: Sempre feche o cursor!
            }
            // Não feche 'conexao' aqui se ela for uma instância estática global
            // gerenciada pelo ConexaoDb.
        }

        return usuario; // Retorna o objeto Usuario ou null
    }

    @Override
    public List<Usuario> buscarTodosUsuarios() {

        SQLiteDatabase conexao = ConexaoDb.getInstance();

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        String[] colunas = {"id", "nome", "email", "senha", "steamId", "imagemPerfil"};

        Cursor cursor = conexao.rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();

           do{

                Usuario usuario = new Usuario(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nome")),
                        cursor.getString(cursor.getColumnIndex("steamId")),
                        cursor.getString(cursor.getColumnIndex("imagemPerfil")),
                        cursor.getString(cursor.getColumnIndex("senha")),
                        cursor.getString(cursor.getColumnIndex("email"))

                );

                usuarios.add(usuario);

            } while (cursor.moveToNext());
        }

        return usuarios;
    }

}



