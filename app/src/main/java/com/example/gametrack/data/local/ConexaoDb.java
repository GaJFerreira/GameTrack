package com.example.gametrack.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoDb extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "gametrack.db";
    private static final int VERSAO_BANCO = 2;

    private static ConexaoDb instancia;
    private static SQLiteDatabase conexao;

    private ConexaoDb(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    public static SQLiteDatabase getInstance(Context context) {
        if (instancia == null) {
            instancia = new ConexaoDb(context.getApplicationContext());
            conexao = instancia.getWritableDatabase();
        }
        return conexao;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome VARCHAR(255)," +
                "steamId VARCHAR(255)," +
                "email VARCHAR(255) UNIQUE," +
                "imagemPerfil VARCHAR(255))");

        db.execSQL("CREATE TABLE IF NOT EXISTS jogo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "appSteamId INTEGER," +
                "titulo VARCHAR(255)," +
                "icone VARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS jogo");
        onCreate(db);
    }
}
