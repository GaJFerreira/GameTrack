package com.example.gametrack.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.interfaces.iMeta;
import com.example.gametrack.data.local.ConexaoDb;

import java.util.ArrayList;
import java.util.List;

public class MetaDao implements iMeta {

    private final SQLiteDatabase conexao;

    public MetaDao(Context context) {
        this.conexao = ConexaoDb.getInstance(context);
    }

    @Override
    public void salvarMeta(Meta meta) {
        ContentValues values = new ContentValues();
        values.put("tipo", meta.getTipo().name());
        values.put("valorMeta", meta.getValorMeta());
        values.put("dataFim", meta.getDataLimite());
        values.put("prioridade", meta.getPrioridade().name());
        values.put("observacao", meta.getObservacao());

        conexao.insert("meta", null, values);
    }

    @Override
    public void atualizarMeta(Meta meta) {
        ContentValues values = new ContentValues();
        values.put("tipo", meta.getTipo().name());
        values.put("valorMeta", meta.getValorMeta());
        values.put("dataFim", meta.getDataLimite());
        values.put("prioridade", meta.getPrioridade().name());
        values.put("observacao", meta.getObservacao());

        conexao.update("meta", values, "id = ?", new String[]{String.valueOf(meta.getId())});
    }

    @Override
    public void excluirMeta(long id) {
        conexao.delete("meta", "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public Meta biscarPorId(long id) {
        Meta meta = null;
        String[] args = {String.valueOf(id)};
        String[] colunas = {"id", "tipo", "valorMeta", "dataFim", "prioridade", "observacao"};

        try (Cursor cursor = conexao.query("meta", colunas, "id = ?", args, null, null, null)) {

            if (cursor.moveToFirst()) {
                long idMeta = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                Meta.Tipo tipo = Meta.Tipo.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
                String valorMeta = cursor.getString(cursor.getColumnIndexOrThrow("valorMeta"));
                String dataLimite = cursor.getString(cursor.getColumnIndexOrThrow("dataFim"));
                Meta.Prioridade prioridade = Meta.Prioridade.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("prioridade")));
                String observacao = cursor.getString(cursor.getColumnIndexOrThrow("observacao"));

                meta = new Meta(idMeta, tipo, valorMeta, dataLimite, prioridade, observacao);
            }
        } catch (Exception e) {
            Log.e("MetaDao", "Erro ao buscar meta por ID: " + id, e);
        }

        return meta;
    }

    @Override
    public List<Meta> listarMetas() {
        List<Meta> metas = new ArrayList<>();
        String sql = "SELECT * FROM meta";

        Cursor cursor = conexao.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                long idMeta = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                Meta.Tipo tipo = Meta.Tipo.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
                String valorMeta = cursor.getString(cursor.getColumnIndexOrThrow("valorMeta"));
                String dataLimite = cursor.getString(cursor.getColumnIndexOrThrow("dataFim"));
                Meta.Prioridade prioridade = Meta.Prioridade.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("prioridade")));
                String observacao = cursor.getString(cursor.getColumnIndexOrThrow("observacao"));

                Meta meta = new Meta(idMeta, tipo, valorMeta, dataLimite, prioridade, observacao);
                metas.add(meta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return metas;
    }
}
