package com.example.paquito.proyecto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper
{
    //solo se requirio una base de datos por lo que hay un único string
    String createTabTorneo = "create table if not exists torneo (id String primary key, Ganador String);";


    public Conexion(Context context){

        super(context,"YaSeArm.bd",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTabTorneo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String updateTorneo = "drop table if exists torneo;";
        db.execSQL(updateTorneo);
        onCreate(db);
    }
}
