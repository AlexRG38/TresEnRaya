package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListarPartidas extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteHelper helper;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_partidas);

        lv = findViewById(R.id.lvUsers);

        helper = new SQLiteHelper(this);
        db = helper.getReadableDatabase();
        Cursor cursor =
                db.query(EstructuraBD.EstructuraTablas.TABLE_NAME_PARTIDA, null, null, null,
                        null, null, null);
        //adaptamos el cursor a nuestro ListView
        String[] from = {EstructuraBD.EstructuraTablas.COLUMN_NAME_JUGADOR1,
                EstructuraBD.EstructuraTablas.COLUMN_NAME_JUGADOR2,
                EstructuraBD.EstructuraTablas.COLUMN_NAME_DIFICULTAD,
                EstructuraBD.EstructuraTablas.COLUMN_NAME_RESULTADO};
        int[] to = {R.id.textJugador1, R.id.textJugador2, R.id.textDificultad, R.id.textResultado};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this,
                R.layout.layout_lista_partidas, cursor, from, to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);
        db.close();
        }
}
