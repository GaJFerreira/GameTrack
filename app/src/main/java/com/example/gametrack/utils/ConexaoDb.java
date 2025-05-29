package com.example.gametrack.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ConexaoDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "gametrack.db";
    private static final int DB_VERSION = 1;

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

        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(255)," +
                "steamId VARCHAR(255)," +
                "imagemPerfil VARCHAR(255)," +
                "senha VARCHAR(255)," +
                "email VARCHAR(255)" +
                ")";

        db.execSQL(sqlUsuario);

        String sqlMeta = "CREATE TABLE IF NOT EXISTS meta(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bibliotecaId INTEGER NOT NULL," +        // FK para biblioteca.id (vínculo principal)
                "tipo TEXT," +                            // Armazenará o nome do enum Meta.Tipo
                "valorMeta TEXT," +
                "progressoAtual TEXT," +
                "dataLimite TEXT," +                      // Formato YYYY-MM-DD
                "prioridade TEXT," +                      // Armazenará o nome do enum Meta.Prioridade
                "observacao TEXT," +
                "status TEXT," +                          // Armazenará o nome do enum Meta.Status
                "FOREIGN KEY (bibliotecaId) REFERENCES biblioteca(id) ON DELETE CASCADE" +
                ")";
        db.execSQL(sqlMeta);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
