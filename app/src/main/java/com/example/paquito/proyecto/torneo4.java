package com.example.paquito.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class torneo4 extends AppCompatActivity {

    ArrayList<String> equipos;
    RadioButton a1, a2, b1, b2;
    String torneo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torneo4);

        //se toma del bundle la lista de euipos
        equipos = getIntent().getExtras().getStringArrayList("listaEquipos");
        a1 = (RadioButton)findViewById(R.id.rb1as);
        a2 = (RadioButton)findViewById(R.id.rb1bs);
        b1 = (RadioButton)findViewById(R.id.rb2as);
        b2 = (RadioButton)findViewById(R.id.rb2bs);

        //se toma el nombre del torneo anexado en la lista en la ultima posicion
        torneo = equipos.get(4);

        //se llenan las etiquetas
        llenarLabels();
    }

    public void llenarLabels(){

        //metodo para llenar al azar los radiobutons, llena en una lista numeros ingresados en desorden
        Random rng = new Random();
        ArrayList<Integer> turnos = new ArrayList<>();
        while (turnos.size() < 4) {
            Integer next = rng.nextInt(4);
            if(!turnos.contains(next)){
                turnos.add(next);
            }
        }
        a1.setText(equipos.get(turnos.get(0)));
        a2.setText(equipos.get(turnos.get(1)));
        b1.setText(equipos.get(turnos.get(2)));
        b2.setText(equipos.get(turnos.get(3)));
    }

    public void jugarFinal(View v){
        //guarda los ganadores y lo suma a un bundle para enviarlos a la siguiente etapa
        validarSeleccion();
        equipos.add(torneo);
        Intent granFinal = new Intent(this, granFinal.class);
        Bundle wundle = new Bundle();
        wundle.putStringArrayList("listaEquipos", equipos);
        granFinal.putExtras(wundle);
        startActivity(granFinal);
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
    }

    public void cancelar(View v){
        //el metodo de cancelar elimina el torneo que no se completo y regresa a la ventana inicial
        Manejador maniwis = new Manejador(this.getApplicationContext());
        maniwis.eliminaTorneo(torneo);
        Intent chaPaTras = new Intent(this, Inicio.class);
        startActivity(chaPaTras);
    }

}
