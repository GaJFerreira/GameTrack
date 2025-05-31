package com.example.gametrack.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gametrack.data.interfaces.iJogo;
import com.example.gametrack.data.local.ConexaoDb;
import com.example.gametrack.data.model.local.Jogo;

import java.util.ArrayList;
import java.util.List;

public class JogoDao implements iJogo {

    private final SQLiteDatabase conexao;

    public JogoDao(Context context) {
        this.conexao = ConexaoDb.getInstance(context);
    }

    @Override
    public void salvarJogo(Jogo jogo) {
        ContentValues values = new ContentValues();
        values.put("appSteamId", jogo.getAppSteamId());
        values.put("titulo", jogo.getTitulo());
        values.put("icone", jogo.getIcone());

        conexao.insert("jogo", null, values);
    }

    @Override
    public void atualizarJogo(Jogo jogo) {
        ContentValues values = new ContentValues();
        values.put("appSteamId", jogo.getAppSteamId());
        values.put("titulo", jogo.getTitulo());
        values.put("icone", jogo.getIcone());

        conexao.update("jogo", values, "id = ?", new String[]{String.valueOf(jogo.getId())});
    }

    @Override
    public void excluirJogo(long id) {
        conexao.delete("jogo", "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public Jogo buscarPorId(long id) {
        Cursor cursor = conexao.query(
                "jogo",
                new String[]{"id", "appSteamId", "titulo", "icone"},
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        Jogo jogo = null;
        if (cursor.moveToFirst()) {
            jogo = new Jogo(
                    cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                    cursor.getLong(cursor.getColumnIndexOrThrow("appSteamId")),
                    cursor.getString(cursor.getColumnIndexOrThrow("icone"))
            );
        }
        cursor.close();
        return jogo;
    }

    @Override
    public List<Jogo> listarJogos() {
        List<Jogo> jogos = new ArrayList<>();

        Cursor cursor = conexao.query(
                "jogo",
                new String[]{"id", "appSteamId", "titulo", "icone"},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Jogo jogo = new Jogo(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("appSteamId")),
                        cursor.getString(cursor.getColumnIndexOrThrow("icone"))
                );
                jogos.add(jogo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jogos;
    }
}
