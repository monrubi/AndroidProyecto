package com.example.paquito.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Torneo extends AppCompatActivity {

    ArrayList<String> equipos;
    RadioButton a1, a2, b1, b2, c1, c2, d1, d2;
    String torneo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torneo);

        //toma del bundle la lista de equipos
        equipos = getIntent().getExtras().getStringArrayList("listaEquipos");

        //se conecta a los botones de la viusta
        a1 = (RadioButton)findViewById(R.id.rb1a);
        a2 = (RadioButton)findViewById(R.id.rb1b);
        b1 = (RadioButton)findViewById(R.id.rb2a);
        b2 = (RadioButton)findViewById(R.id.rb2b);
        c1 = (RadioButton)findViewById(R.id.rb3a);
        c2 = (RadioButton)findViewById(R.id.rb3b);
        d1 = (RadioButton)findViewById(R.id.rb4a);
        d2 = (RadioButton)findViewById(R.id.rb4b);

        //se toma el nombre del torneo anexado al final de la lista
        torneo = equipos.get(8);

        //llena los radiobutons
        llenarLabels();
    }

    public void llenarLabels(){
        //metodo para llenar al azar los radiobutons, llena en una lista numeros ingresados en desorden
        Random rng = new Random();
        ArrayList<Integer> turnos = new ArrayList<>();
        while (turnos.size() < 8) {
            Integer next = rng.nextInt(8);
            if(!turnos.contains(next)){
                turnos.add(next);
            }
        }

        a1.setText(equipos.get(turnos.get(0)));
        a2.setText(equipos.get(turnos.get(1)));
        b1.setText(equipos.get(turnos.get(2)));
        b2.setText(equipos.get(turnos.get(3)));
        c1.setText(equipos.get(turnos.get(4)));
        c2.setText(equipos.get(turnos.get(5)));
        d1.setText(equipos.get(turnos.get(6)));
        d2.setText(equipos.get(turnos.get(7)));
    }

    public void semifinal(View v){

        //guarda los ganadores y lo suma a un bundle para enviarlos a la siguiente etapa
        validarSeleccion();
        equipos.add(torneo);
        Intent semi = new Intent(this, torneo4.class);
        Bundle wundle = new Bundle();
        wundle.putStringArrayList("listaEquipos", equipos);
        semi.putExtras(wundle);
        startActivity(semi);
    }

    public void validarSeleccion(){

        //dado que los radio buttons no se encuentran en un solo grupo sino en 4 se tuvo que revisar los 4 por fuera de un for el cual solo iteraria 2 veces
        //se encuentran preseleccionados en una opcion para evitar errores si el usuario no elige alguno
        equipos.clear();
        if(a1.isChecked())
            equipos.add(a1.getText().toString());
        else
            equipos.add(a2.getText().toString());
        if(b1.isChecked())
            equipos.add(b1.getText().toString());
        else
            equipos.add(b2.getText().toString());
        if(c1.isChecked())
            equipos.add(c1.getText().toString());
        else
            equipos.add(c2.getText().toString());
        if(d1.isChecked())
            equipos.add(d1.getText().toString());
        else
            equipos.add(d2.getText().toString());
    }

    public void cancelar(View v){
        //el metodo de cancelar elimina el torneo que no se completo y regresa a la ventana inicial
        Manejador maniwis = new Manejador(this.getApplicationContext());
        maniwis.eliminaTorneo(torneo);
        Intent chaPaTras = new Intent(this, Inicio.class);
        startActivity(chaPaTras);
    }
}
