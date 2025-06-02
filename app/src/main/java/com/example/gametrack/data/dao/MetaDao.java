package com.example.gametrack.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.interfaces.iMeta;
//import com.example.gametrack.utils.ConexaoDb;
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
        values.put("jogoId", meta.getBibliotecaId());
        values.put("tipo", meta.getTipo().name());
        values.put("valorMeta", meta.getValorMeta());
        values.put("progressoAtual", meta.getProgressoAtual());
        values.put("dataFim", meta.getDataLimite());
        values.put("prioridade", meta.getPrioridade().name());
        values.put("observacao", meta.getObservacao());
        values.put("status", meta.getStatus().name());

        conexao.insert("meta", null, values);
    }

    @Override
    public void atualizarMeta(Meta meta) {
        ContentValues values = new ContentValues();
        values.put("id", meta.getId());
        values.put("jogoId", meta.getBibliotecaId());
        values.put("tipo", meta.getTipo().name());
        values.put("valorMeta", meta.getValorMeta());
        values.put("progressoAtual", meta.getProgressoAtual());
        values.put("dataFim", meta.getDataLimite());
        values.put("prioridade", meta.getPrioridade().name());
        values.put("observacao", meta.getObservacao());
        values.put("status", meta.getStatus().name());

        conexao.update("meta", values, "id = ?", new String[]{String.valueOf(meta.getId())});

    }

    @Override
    public void excluirMeta(long id) {
        ContentValues values = new ContentValues();

       // SQLiteDatabase conexao = ConexaoDb.getInstance();
        conexao.delete("meta", "id = ?", new String[]{String.valueOf(id)});

    }

    @Override
    public Meta biscarPorId(long id) {

        Meta meta = null;
        String[] args = {String.valueOf(id)};
        String[] colunas = {"id", "jogoId", "tipo", "valorMeta", "progressoAtual", "dataFim", "prioridade", "observacao", "status"};

        Cursor cursor = null;


        try {
            if (conexao == null) {
                Log.e("MetaDao", "conexao nula");
                return null;
            }

            cursor = conexao.query(
                    "meta",
                    colunas,
                    "id = ?",
                    args,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor == null || cursor.getCount() == 0) {
                int idCol = cursor.getColumnIndexOrThrow("id");
                int jogoIdCol = cursor.getColumnIndexOrThrow("jogoId");
                int tipoCol = cursor.getColumnIndexOrThrow("tipo");
                int valorMetaCol = cursor.getColumnIndexOrThrow("valorMeta");
                int progressoAtualCol = cursor.getColumnIndexOrThrow("progressoAtual");
                int dataLimiteCol = cursor.getColumnIndexOrThrow("dataLimite"); // Nome consistente
                int prioridadeCol = cursor.getColumnIndexOrThrow("prioridade");
                int observacaoCol = cursor.getColumnIndexOrThrow("observacao");
                int statusCol = cursor.getColumnIndexOrThrow("status");

                // Converter Strings para Enums (assumindo que você salvou enum.name())
                Meta.Tipo tipoEnum = Meta.Tipo.valueOf(cursor.getString(tipoCol));
                Meta.Prioridade prioridadeEnum = Meta.Prioridade.valueOf(cursor.getString(prioridadeCol));
                Meta.Status statusEnum = Meta.Status.valueOf(cursor.getString(statusCol));

                meta = new Meta(
                        cursor.getLong(idCol),
                        cursor.getLong(jogoIdCol),
                        tipoEnum,
                        cursor.getString(valorMetaCol),
                        cursor.getString(progressoAtualCol),
                        cursor.getString(dataLimiteCol),
                        prioridadeEnum,
                        cursor.getString(observacaoCol),
                        statusEnum
                );

            }

        } catch (IllegalArgumentException e) {
            Log.e("MetaDao", "Erro ao converter string para enum: " + e.getMessage(), e);
            meta = null;
        } catch (Exception e) {
            Log.e("MetaDao", "Erro ao buscar meta por ID: " + id, e);
            meta = null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return meta;
    }

    @Override
    public List<Meta> listarMetas() {

        List<Meta> metas = new ArrayList<>();

        String slq = "SELECT * FROM meta";
        String colunas[] = {"id", "jogoId", "tipo", "valorMeta", "progressoAtual", "dataFim", "prioridade", "observacao", "status"};

        Cursor cursor = conexao.rawQuery(slq, null);

        if (cursor.moveToFirst()) {
            do {
                Meta meta = new Meta(
                        cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                        cursor.getLong(cursor.getColumnIndexOrThrow("jogoId")),
                        Meta.Tipo.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("tipo"))),
                        cursor.getString(cursor.getColumnIndexOrThrow("valorMeta")),
                        cursor.getString(cursor.getColumnIndexOrThrow("progressoAtual")),
                        cursor.getString(cursor.getColumnIndexOrThrow("dataFim")),
                        Meta.Prioridade.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("prioridade"))),
                        cursor.getString(cursor.getColumnIndexOrThrow("observacao")),
                        Meta.Status.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("status")))


                );

                metas.add(meta);

            } while (cursor.moveToNext());
        }
        return metas;
    }
}