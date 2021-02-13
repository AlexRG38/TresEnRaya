package com.example.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    MediaPlayer media;
    //creamos un campo de clase para almacenar cuantos jugadores hay
    private int jugadores;
    //para guardar la casilla pulsada
    private int[] CASILLAS;
    private Partida partida;
    SQLiteDatabase db;
    SQLiteHelper helper;
    String j1, j2, dificultadS, resultado;
    int puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new SQLiteHelper(this);
        db = helper.getReadableDatabase();

        //Inicializamos el array con cada casilla del tablero
        CASILLAS= new int[9];
        CASILLAS[0]=R.id.c1;
        CASILLAS[1]=R.id.c2;
        CASILLAS[2]=R.id.c3;
        CASILLAS[3]=R.id.c4;
        CASILLAS[4]=R.id.c5;
        CASILLAS[5]=R.id.c6;
        CASILLAS[6]=R.id.c7;
        CASILLAS[7]=R.id.c8;
        CASILLAS[8]=R.id.c9;

    }

    public void Jugar(View v){

        //reseteamos el tablero
        ImageView imagen;

        for (int casilla:CASILLAS){
            imagen=(ImageView)findViewById(casilla);

            imagen.setImageResource(R.drawable.casilla);
        }

        //establecemos los jugadores que van a jugar (1 o 2 jugadores)
        jugadores=1;
        userDialog().show();
        j2 = "Maquina";
        //el metodo Jugar será llamado tanto en el botón de un jugador como en el de dos
        //por eso comprobamos la vista que entra como parámetro
        if(v.getId()==R.id.dosjugadores){
            jugadores=2;
            j2 = "user2";
        }

        //evaluamos la dificultad
        RadioGroup configDificultad=(RadioGroup)findViewById(R.id.grupoDificultad);

        int id=configDificultad.getCheckedRadioButtonId();

        int dificultad=0;

        if(id==R.id.facil){
            dificultad=1;
            dificultadS = "Facil";
        }else if(id==R.id.dificil){
            dificultad=2;
            dificultadS = "Dificil";
        } else if (id==R.id.imposible){
            dificultadS = "Extremo";
            dificultad = 3;
        }

        puntos = dificultad;

        partida=new Partida(dificultad);

        //deshabilitamos los botones del tablero
        ((Button)findViewById(R.id.unjugador)).setEnabled(false);
        ((Button)findViewById(R.id.dosjugadores)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.grupoDificultad)).setAlpha(0);

    }

    //creamos el método que se lanza al pulsar cada casilla
    public void toqueCasilla(View v){


        //hacemos que sólo se ejecute cuando la variable partida no sea null
        if(partida==null){
            return;
        }else{
            int casilla=0;
            //recorremos el array donde tenemos almacenada cada casilla
            for(int i=0;i<9;i++){
                if(CASILLAS[i]==v.getId()){
                    casilla=i;
                    break;
                }
            }

            //creamos un mensaje toast
           /* Toast mensaje= Toast.makeText(this,"has pulsado la casilla "+ casilla,Toast.LENGTH_LONG);
            mensaje.setGravity(Gravity.CENTER,0,0);
            mensaje.show();*/

            //si la casilla pulsada ya está ocupada salimos del método
            if(partida.casilla_libre(casilla)==false){
                return;
            }
            //llamamos al método para marcar la casilla que se ha tocado
            marcaCasilla(casilla);


            int resultado=partida.turno();

            if(resultado>0){
                terminar_partida(resultado);
                return;
            }

            //realizamos el marcado de la casilla que elige el programa
            casilla=partida.ia();

            while (partida.casilla_libre(casilla)!=true){
                casilla=partida.ia();
            }

            marcaCasilla(casilla);

            resultado=partida.turno();

            if(resultado>0){
                terminar_partida(resultado);
            }

        }
    }

    private void terminar_partida(int res){

        String mensaje;

        if(res==1){
            mensaje="Han ganado los círculos";
            media = MediaPlayer.create(this, R.raw.victory);
            media.start();
            resultado = j1;
        }

        else if(res==2) {
            mensaje="Han ganado las aspas";
            media = MediaPlayer.create(this, R.raw.victory);
            media.start();
            resultado = j2;
            puntos = 0;
        }  else{
            mensaje="Empate";
            resultado = "Empate";
            puntos = 1;
        }

        Toast toast= Toast.makeText(this,mensaje,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        if (!userExiste(j1)){
            insertUser(j1, puntos);
        } else {
            updateUser(j1, puntos);
        }

        insertarPartidos(j1, j2, dificultadS, resultado);

        //terminamos el juego
        partida=null;

        //habilitamos los botones del tablero
        ((Button)findViewById(R.id.unjugador)).setEnabled(true);
        ((Button)findViewById(R.id.dosjugadores)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.grupoDificultad)).setAlpha(1);

    }

    //metodo para marcar las casillas
    private void marcaCasilla(int casilla){
        ImageView imagen;
        imagen=(ImageView)findViewById(CASILLAS[casilla]);

        if(partida.jugador==1){
            media = MediaPlayer.create(this, R.raw.click);
            media.start();
            imagen.setImageResource(R.drawable.circulo);
        }else{
            media = MediaPlayer.create(this, R.raw.click);
            media.start();
            imagen.setImageResource(R.drawable.aspa);
        }

    }

    public AlertDialog userDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.layout_dialogo, null);

        builder.setView(v);

        EditText et = v.findViewById(R.id.username);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                j1 = et.getText().toString();
            }
        });
        return builder.create();
    }

    public void insertarPartidos(String j1, String j2, String dificultad, String resultado){
        ContentValues values = new ContentValues();
        values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_JUGADOR1, j1);
        values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_JUGADOR2, j2);
        values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_DIFICULTAD, dificultad);
        values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_RESULTADO, resultado);
        db.insert(EstructuraBD.EstructuraTablas.TABLE_NAME_PARTIDA,null, values);
    }

    public void insertUser(String j1, int puntos){
        ContentValues values = new ContentValues();
        values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_USERNAME, j1);
        values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_PUNTOS, puntos);
        values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_NUM_PARTIDA, 1);
        db.insert(EstructuraBD.EstructuraTablas.TABLE_NAME_USUARIO, null, values);
    }

    public void updateUser(String j1, int puntos){
        Cursor cursor = db.query(EstructuraBD.EstructuraTablas.TABLE_NAME_USUARIO,null,null,null,null,null,null);

        cursor.moveToFirst();

        int nfilas= cursor.getCount();

        for (int i=0;i<nfilas;i++){
            for (int j =1; j <2; j++){
                if (j1.equals(cursor.getString(j))){
                    ContentValues values = new ContentValues();
                    values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_USERNAME, j1);
                    values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_PUNTOS, cursor.getInt(3) + puntos);
                    values.put(EstructuraBD.EstructuraTablas.COLUMN_NAME_NUM_PARTIDA, cursor.getInt(2) + 1);
                    db.update(EstructuraBD.EstructuraTablas.TABLE_NAME_USUARIO, values, EstructuraBD.EstructuraTablas.COLUMN_NAME_USERNAME +" = " +"'" +j1 + "'", null);
                }
                cursor.moveToNext();
            }
        }


    }

    public boolean userExiste(String j1){
        Cursor cursor = db.query(EstructuraBD.EstructuraTablas.TABLE_NAME_USUARIO,null,null,null,null,null,null);

        cursor.moveToFirst();

        int nfilas= cursor.getCount();

        for (int i=0;i<nfilas;i++){
            for (int j =1; j <2; j++){
                if (j1.equals(cursor.getString(j))){
                    return true;
                }
                cursor.moveToNext();
            }
        }
        return false;
    }

    public void irRanking(View view){
        Intent intent = new Intent(this, RankingUsuarios.class);
        startActivity(intent);
    }

    public void irUsuarios(View view){
        Intent intent = new Intent(this, ListarPartidas.class);
        startActivity(intent);
    }




}