package com.example.gametrack.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gametrack.data.model.local.Jogo;
import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.model.local.Usuario;
import com.example.gametrack.data.interfaces.iMeta;
import com.example.gametrack.data.local.ConexaoDb;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        values.put("id_jogo", meta.getJogo() != null ? meta.getJogo().getId() : null);
        values.put("id_usuario", meta.getUsuario() != null ? meta.getUsuario().getId() : null);

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
        values.put("id_jogo", meta.getJogo() != null ? meta.getJogo().getId() : null);
        values.put("id_usuario", meta.getUsuario() != null ? meta.getUsuario().getId() : null);

        conexao.update("meta", values, "id = ?", new String[]{String.valueOf(meta.getId())});
    }

    @Override
    public void excluirMeta(long id) {
        conexao.delete("meta", "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public Map<String, List<Meta>> listarMetasPorUsuarioAgrupadasPorDia(long id) {
        Map<String, List<Meta>> metasPorDia = new LinkedHashMap<>();

        String sql = "SELECT m.id AS meta_id, m.tipo, m.valorMeta, m.dataFim AS dataLimite, m.prioridade, m.observacao, " +
                "m.id_jogo, m.id_usuario, " +
                "j.id AS jogo_id, j.titulo, j.appSteamId, j.icone, " +
                "u.id AS usuario_id, u.nome, u.steamId, u.email, u.imagemPerfil " +
                "FROM meta m " +
                "JOIN jogo j ON m.id_jogo = j.id " +
                "JOIN usuario u ON m.id_usuario = u.id " +
                "WHERE m.id_usuario = ? " +
                "ORDER BY m.dataFim ASC";

        try (Cursor cursor = conexao.rawQuery(sql, new String[]{String.valueOf(id)})) {
            while (cursor.moveToNext()) {
                long idMeta = cursor.getLong(cursor.getColumnIndexOrThrow("meta_id"));

                String tipoStr = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
                Meta.Tipo tipo = null;
                try {
                    tipo = Meta.Tipo.valueOf(tipoStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    Log.e("MetaDao", "Tipo inválido no banco: " + tipoStr, e);
                }

                String valorMeta = cursor.getString(cursor.getColumnIndexOrThrow("valorMeta"));
                String dataLimite = cursor.getString(cursor.getColumnIndexOrThrow("dataLimite")); // formato esperado: yyyy-MM-dd ou similar

                String prioridadeStr = cursor.getString(cursor.getColumnIndexOrThrow("prioridade"));
                Meta.Prioridade prioridade = null;
                try {
                    prioridade = Meta.Prioridade.valueOf(prioridadeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    Log.e("MetaDao", "Prioridade inválida no banco: " + prioridadeStr, e);
                }

                String observacao = cursor.getString(cursor.getColumnIndexOrThrow("observacao"));

                // Preenchendo Jogo
                long jogoId = cursor.getLong(cursor.getColumnIndexOrThrow("jogo_id"));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                long appSteamId = cursor.getLong(cursor.getColumnIndexOrThrow("appSteamId"));
                String icone = cursor.getString(cursor.getColumnIndexOrThrow("icone"));
                Jogo jogo = new Jogo(jogoId, titulo, appSteamId, icone);

                // Preenchendo Usuario
                long usuarioId = cursor.getLong(cursor.getColumnIndexOrThrow("usuario_id"));
                String nomeUsuario = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
                String steamId = cursor.getString(cursor.getColumnIndexOrThrow("steamId"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String imagemPerfil = cursor.getString(cursor.getColumnIndexOrThrow("imagemPerfil"));
                Usuario usuario = new Usuario(usuarioId, nomeUsuario, steamId, email, imagemPerfil);

                Meta meta = new Meta(idMeta, tipo, valorMeta, dataLimite, prioridade, observacao, jogo, usuario);

                // Agrupar por dataLimite (dia)
                // Se a dataLimite tem hora, você pode querer extrair só a parte do dia (ex: yyyy-MM-dd)
                String dia = dataLimite;
                if (dataLimite.length() > 10) {
                    dia = dataLimite.substring(0, 10);
                }

                if (!metasPorDia.containsKey(dia)) {
                    metasPorDia.put(dia, new ArrayList<>());
                }
                metasPorDia.get(dia).add(meta);
            }
        } catch (Exception e) {
            Log.e("MetaDao", "Erro ao listar metas por usuário agrupadas por dia", e);
        }

        return metasPorDia;
    }

}
