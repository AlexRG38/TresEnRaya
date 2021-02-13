package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class RankingUsuarios extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_usuarios);

        lv = findViewById(R.id.lvUsers);

        helper = new SQLiteHelper(this);
        db = helper.getReadableDatabase();
        Cursor cursor =
                db.query(EstructuraBD.EstructuraTablas.TABLE_NAME_USUARIO, null, null, null
                        , null, null, EstructuraBD.EstructuraTablas.COLUMN_NAME_PUNTOS+" DESC");
        //adaptamos el cursor a nuestro ListView
        String[] from = {EstructuraBD.EstructuraTablas.COLUMN_NAME_USERNAME,
                EstructuraBD.EstructuraTablas.COLUMN_NAME_NUM_PARTIDA,
                EstructuraBD.EstructuraTablas.COLUMN_NAME_PUNTOS};
        int[] to = {R.id.textUsername, R.id.textNumPart, R.id.textPuntos};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,
                R.layout.layout_ranking_usuarios, cursor, from, to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);
        db.close();
    }
}