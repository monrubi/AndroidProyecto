package com.example.paquito.proyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Manejador {
    Conexion con;
    SQLiteDatabase db;

    public Manejador(Context context){
        con = new Conexion(context);
    }

    public void open () throws SQLiteException {
        db=con.getReadableDatabase();
    }

    public void close () throws SQLiteException{
        con.close();
    }

    //Agrega un nuevo torneo a la tabla con el nombre en nulo para modificarlo al cocnluir
    public boolean agregaTorneo (String id){
        //se guardan los valores (etiqueta, objeto) donde la etiqueta tiene el nombre de la columna de la tabla donde swerá agregado
        ContentValues vals= new ContentValues();
        boolean resp;
        open();
        vals.put("id", id);
        vals.put("Ganador", "_");
        try {
            db.insert("torneo", null, vals);
            resp = true;
        }catch(Exception ex){
            resp = false;
        }
        close();
        return resp;
    }


    //Cambia el nombre del equipo ganador al terminar el torneo, o de forma posterior
    //si se ha descalificado al ganador o seleccionado mal el radio button
    public boolean cambiaNombre (String ganador, String torneo){
        boolean resp;
        open();
        //query cob el valor a ser modificado
        String query = "update torneo set Ganador='"+ganador+"' where id='"+torneo+"';";
        try{
            //se ejecuta el query
            db.execSQL(query);
            resp = true;
        }catch (Exception e){
            resp=false;
        }
        close();
        return resp;
    }

    //Si se cancela un torneo debe eliminarse de la tabla
    public boolean eliminaTorneo(String id){
        boolean resp;
        open();
        //tras abrir la conexion se declara el query con el parametro que se buscara para eliminar el valor de la tabla
        String query = "delete from torneo where id='"+id+"';";
        try {
            db.execSQL(query);
            resp= true;
        }catch (Exception e){
            resp = false;
        }
        close();

        return resp;
    }

    public boolean lanzarMisil(){
        boolean resp;
        open();
        //se declaran los querys para eliminar la tabla y antes de cerrar la conexion debe reconstruirse vacía
        String query = "drop table torneo;";
        String query2 = "create table if not exists torneo (id String primary key, Ganador String);";
        try {
            db.execSQL(query);
            db.execSQL(query2);
            resp= true;
        }catch (Exception e){
            resp = false;
        }
        close();
        return resp;
    }


    //Obtiene el nombre del ganador del torneo seleccionado
    //no contiene try catch pues el cuidado de no ser llamado con la tabla vacia se realiza en el codigo de la interfaz
    public String getGanador(String idTorneo){
        String resp;
        open();
        //hacemos el query de la busqueda indicando la tabla y el parametro
        String query = "select Ganador from torneo where id ='"+idTorneo+"';";
        //se declara el cursor que funciona como un data reader en C#
        Cursor c = db.rawQuery(query,null);
        //mueve el cursor a la primera casilla de la lectura
        c.moveToNext();
        //el indice de columnas empieza en 0, si se ocuparan varias columnas se acceden con los index
        resp = c.getString(0);
        c.close();
        close();
        return resp;
    }

    //obtiene todos los torneos existentes para llenar la lista de seleccion "spinner" (drop down list)
    public List<String> getAllLabels() {
        //crea la lista que se regresa con las respuestas, necesaria para el spinner
        List<String> labels = new ArrayList<String>();

        //query que selecciona todos los torneos en la tabla
        String selectQuery = "SELECT id FROM torneo";


        open();
        Cursor c = db.rawQuery(selectQuery, null);

        // revisa primero que haya respuesta
        if (c.moveToFirst()) {
            //si la hay hace un loop que va guardando los renglones de una misma columna (0)
            do {
                labels.add(c.getString(0));
                //mueve el lector al siguiente renglon
            } while (c.moveToNext());
        }
        c.close();
        close();
        return labels;
    }


}
