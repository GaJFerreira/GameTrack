package com.example.gametrack.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ConexaoDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "gametrack.db";
    private static final int DB_VERSION = 2;

    private static SQLiteDatabase instance;

    public static SQLiteDatabase getInstance(){
        return instance;
    }

    public ConexaoDb(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        instance = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabela Usuario
        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(255)," +
                "steamId VARCHAR(255)," +
                "email VARCHAR(255) UNIQUE," + // Adicionar UNIQUE para email é uma boa prática
                "senha VARCHAR(255)," +
                "imagemPerfil VARCHAR(255)" +
                ")";
        db.execSQL(sqlUsuario);

        // Tabela Jogo (adapte os campos conforme sua entidade Jogo)
        String sqlJogo = "CREATE TABLE IF NOT EXISTS jogo(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "appSteamId VARCHAR(255) UNIQUE," + // UNIQUE se for um identificador único
                "titulo VARCHAR(255)," +
                "urlImagemCapa VARCHAR(255)" +
                // Adicione outros campos do seu modelo Jogo
                ")";
        db.execSQL(sqlJogo);

        // Tabela Meta (adapte os campos e tipos conforme sua entidade Meta)
        String sqlMeta = "CREATE TABLE IF NOT EXISTS meta(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bibliotecaId INTEGER," + // Supondo que seja um ID de Jogo ou Usuario
                "tipo TEXT," + // Armazenando enum.name() como TEXT
                "valorMeta TEXT," +
                "progressoAtual TEXT," +
                "dataFim TEXT," + // Nome da coluna que você usou no MetaDao (dataLimite no modelo)
                "prioridade TEXT," + // Armazenando enum.name() como TEXT
                "observacao TEXT," +
                "status TEXT," + // Armazenando enum.name() como TEXT
                "FOREIGN KEY(bibliotecaId) REFERENCES jogo(id) ON DELETE CASCADE" + // Exemplo de FK se bibliotecaId for de Jogo
                ")";
        db.execSQL(sqlMeta);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
