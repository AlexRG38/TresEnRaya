package com.example.tresenraya;

import android.provider.BaseColumns;

public class EstructuraBD {
    public static final String SQL_CREATE_TABLA_PARTIDAS =
            "CREATE TABLE IF NOT EXISTS "+ EstructuraTablas.TABLE_NAME_PARTIDA +
                    "(" + EstructuraTablas._ID + " integer PRIMARY KEY, "
                    + EstructuraTablas.COLUMN_NAME_JUGADOR1 + " text, "
                    + EstructuraTablas.COLUMN_NAME_JUGADOR2 + " text, "
                    + EstructuraTablas.COLUMN_NAME_DIFICULTAD + " text, "
                    + EstructuraTablas.COLUMN_NAME_RESULTADO + " text); ";

    public static final String SQL_CREATE_TABLA_USUARIOS =
            "CREATE TABLE IF NOT EXISTS "+ EstructuraTablas.TABLE_NAME_USUARIO +
                    "(" + EstructuraTablas._ID + " integer PRIMARY KEY, "
                    + EstructuraTablas.COLUMN_NAME_USERNAME + " text, "
                    + EstructuraTablas.COLUMN_NAME_NUM_PARTIDA + " integer, "
                    + EstructuraTablas.COLUMN_NAME_PUNTOS + " integer);";

    public static final String SQL_DELETE_TABLA_PARTIDAS =
            "DROP TABLE IF EXISTS  " + EstructuraTablas.TABLE_NAME_PARTIDA;

    public static final String SQL_DELETE_TABLA_USUARIOS =
            "DROP TABLE IF EXISTS  " + EstructuraTablas.TABLE_NAME_USUARIO;

    /* Clase interna que define la estructura de la tabla de cantantes */
    public static class EstructuraTablas implements BaseColumns {
        public static final String TABLE_NAME_PARTIDA = "partida";
        public static final String COLUMN_NAME_JUGADOR1 = "jugador1";
        public static final String COLUMN_NAME_JUGADOR2 = "jugador2";
        public static final String COLUMN_NAME_DIFICULTAD = "dificultad";
        public static final String COLUMN_NAME_RESULTADO = "resultado";
        public static final String TABLE_NAME_USUARIO = "usuario";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PUNTOS = "puntos";
        public static final String COLUMN_NAME_NUM_PARTIDA = "partidas";

    }

    private EstructuraBD() {}
}
