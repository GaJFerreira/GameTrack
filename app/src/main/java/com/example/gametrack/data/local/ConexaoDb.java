package com.example.gametrack.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoDb extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "gametrack.db";
    private static final int VERSAO_BANCO = 5;

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


//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS usuario (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "nome VARCHAR(255)," +
//                "steamId VARCHAR(255)," +
//                "email VARCHAR(255) UNIQUE," +
//                "imagemPerfil VARCHAR(255))");
//
//        db.execSQL("CREATE TABLE IF NOT EXISTS jogo (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "appSteamId INTEGER," +
//                "titulo VARCHAR(255)," +
//                "icone VARCHAR(255))");
//    }

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
                "appSteamId VARCHAR(255) UNIQUE," +
                "titulo VARCHAR(255)," +
                "icone VARCHAR(255)" +
                ")";
        db.execSQL(sqlJogo);

        // Tabela Meta (adapte os campos e tipos conforme sua entidade Meta)
        String sqlMeta = "CREATE TABLE IF NOT EXISTS meta(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "jogoId INTEGER," + // Supondo que seja um ID de Jogo ou Usuario
                "tipo TEXT," + // Armazenando enum.name() como TEXT
                "valorMeta TEXT," +
                "progressoAtual TEXT," +
                "dataFim TEXT," + // Nome da coluna que você usou no MetaDao (dataLimite no modelo)
                "prioridade TEXT," + // Armazenando enum.name() como TEXT
                "observacao TEXT," +
                "status TEXT," + // Armazenando enum.name() como TEXT
                "FOREIGN KEY(jogoId) REFERENCES jogo(id) ON DELETE CASCADE" + // Exemplo de FK se bibliotecaId for de Jogo
                ")";
        db.execSQL(sqlMeta);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS jogo");
        onCreate(db);
    }
}
