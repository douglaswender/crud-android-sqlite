package com.example.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String nameDb = "banco.db";
    private static final int versionDb = 2;

    public Conexao(Context context) {
        super(context, nameDb, null, versionDb);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //criando tabela
        db.execSQL("create table pessoa(id integer primary key autoincrement," +
                "nome varchar(50), idade varchar(50), endereco varchar(50)) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pessoa");
        onCreate(db);
    }
}