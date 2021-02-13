package com.example.tresenraya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bdjuego.db";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EstructuraBD.SQL_CREATE_TABLA_PARTIDAS);
        db.execSQL(EstructuraBD.SQL_CREATE_TABLA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL(EstructuraBD.SQL_DELETE_TABLA_PARTIDAS);
        db.execSQL(EstructuraBD.SQL_DELETE_TABLA_USUARIOS);
        //Se crea la nueva versión de la tabla
        db.execSQL(EstructuraBD.SQL_CREATE_TABLA_PARTIDAS);
        db.execSQL(EstructuraBD.SQL_CREATE_TABLA_USUARIOS);
    }
}
