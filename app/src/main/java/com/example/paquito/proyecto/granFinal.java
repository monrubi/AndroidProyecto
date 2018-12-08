package com.example.paquito.proyecto;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class granFinal extends AppCompatActivity {

    ArrayList<String> equipos;
    RadioButton a1, a2;
    String torneo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gran_final);

        //recibe del bundle la lista de euipos
        equipos = getIntent().getExtras().getStringArrayList("listaEquipos");

        //se conecta a los elementos de la vista
        a1 = (RadioButton)findViewById(R.id.rb1af);
        a2 = (RadioButton)findViewById(R.id.rb1bf);

        //se obtiene el nombre del torneo anexado al final en la lista
        torneo = equipos.get(2);

        //llenba las etiquetas de los botones
        llenarLabels();
    }

    public void llenarLabels(){
        //se guardan las unicas dos opciones, el orden no es relevante
        a1.setText(equipos.get(0));
        a2.setText(equipos.get(1));
    }

    public void finalizar(View v){
        Manejador maniwis = new Manejador(this.getApplicationContext());
        String ganador;

        //se encuentran preseleccionados en una opcion para evitar errores si el usuario no elige alguno
        if(a1.isChecked())
            ganador = a1.getText().toString();
        else
            ganador = a2.getText().toString();

        //se cambia el nombre del ganador del torneo mediante un manejador
        maniwis.cambiaNombre(ganador, torneo);

        //se construye un mensaje de aviso con el ganador y su listener para enviar a la pantalla de inicio tras notificar al usuario
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡FELICIDADES!");
        builder.setMessage("Equipo "+ganador+", haz ganado");

        //se declara en final para poder ser llamnado en el listener
        final Intent reiniciar = new Intent(this, Inicio.class);
        // add a button
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(reiniciar);
            }
        });

        //crea y lanza el dfialogo configurado
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void cancelar(View v){
        //el metodo de cancelar elimina el torneo que no se completo y regresa a la ventana inicial
        Manejador maniwis = new Manejador(this.getApplicationContext());
        maniwis.eliminaTorneo(torneo);
        Intent chaPaTras = new Intent(this, Inicio.class);
        startActivity(chaPaTras);
    }
}
