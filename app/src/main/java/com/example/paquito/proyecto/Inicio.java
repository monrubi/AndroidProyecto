package com.example.paquito.proyecto;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class Inicio extends AppCompatActivity  {

    Spinner ddlist;
    EditText et, goHome;
    List<String> torneos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        //se llaman a los elementos de la ventana por medio de sus etiquetas para poder controlarlos
        ddlist = (Spinner)findViewById(R.id.dlTorneos);
        et = (EditText)findViewById(R.id.lbGanador);
        goHome = (EditText)findViewById(R.id.lbNombre);
        //llena el spinner o drop down list
        loadSpinnerData();

        //agrega listeners para el spinner para saber si se cambio la selección
        ddlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //si se selecciona algo se recarga la etiqueta de ganador
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadLabel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        //se llena, en caso de tener torneo ya existente, el ganador para cambiarlo
        loadLabel();
    }

    //pone en la etiqueta de ganador el que corresponde al torneo seleccionado, en caso de no haber ninguno deja el precargado "sin torneos"
    public void loadLabel(){
        if(!torneos.isEmpty()){
            Manejador man = new Manejador(this.getApplicationContext());
            et.setText(man.getGanador(ddlist.getSelectedItem().toString()));
        }

    }

    //permite cambiar el ganador del torneo en caso de descalificación a criterio del usuario
    //regresa un mensaje adecuado si se cambio, si no existen torneos y si se pudo cambiar correctamente
    public void cambiarNombre(View v){
        String mensaje = "Nombre cambiado correctamente";
        //revisa si existen torneos mediante la misma bolsa con la que se llena el spinner
        if (!torneos.isEmpty()) {
            Manejador man = new Manejador(this.getApplicationContext());
            boolean resp = man.cambiaNombre(et.getText().toString(), ddlist.getSelectedItem().toString());
            if (!resp)
                    mensaje = "No pudo cambiarse el nombre";
        }else
            mensaje = "No hay torneos existentes";
        Toast.makeText(Inicio.this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void eliminaTorneo(View v){
        //indica con un toast si pudo eliminarse correctamente
        String mensaje = "No pudo eliminarse";

        //revisa que haya torneos guardados
        if(!torneos.isEmpty()){
            //se crea un manejador y se ejecurta su método de eliminar
            Manejador man = new Manejador(this.getApplicationContext());
            boolean resp = man.eliminaTorneo(ddlist.getSelectedItem().toString());
            if(resp) {
                mensaje = "Torneo eliminado";
                loadSpinnerData();
                //revisa si tras eliminar el torneo quedo vacia la tabla y pone texto predeterminado
                if(torneos.isEmpty())
                    et.setText("No hay torneos");

            }

        }
        else
            mensaje = "No hay torneos existentes";
        Toast.makeText(Inicio.this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void crear(View v){
        //si no existen torneos y el nombre no se repite instancia un manejador que ejecuta el agregar
        if(torneos.isEmpty() || !torneos.contains(goHome.getText().toString())) {

            //se crea un manejador y se llama su metodo para crear un nuevo torneo con un nombre en blanco que se cambia al termianr
            Manejador man = new Manejador(this.getApplicationContext());
            man.agregaTorneo(goHome.getText().toString());

            //se crean el intent (con la ventana destino) y el bundle para enviar a la siguiente ventana el nombre del torneo y poder poner su ganador
            Intent intent = new Intent(this, Equipos.class);
            Bundle wundle = new Bundle();

            //se le agregan elementos al bundle y el bundle se adhiere al intent
            wundle.putString("nombreTorneo", goHome.getText().toString());

            //se agrega el bundle al intent y se envia a la siguiente ventana
            intent.putExtras(wundle);
            startActivity(intent);
        }else{
            //si ya existe da aviso y pone el mismo nombre con un 2
            Toast.makeText(Inicio.this, "Torneo jugado, escoja otro nombre", Toast.LENGTH_LONG).show();
            goHome.setText(goHome.getText().toString()+"2");
        }
    }

    //metodo para eliminar la tabla y reconstruirla
    public void terminate(View view) {
        //lanza un mensaje de alerta con opción de cancelar o continuar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡ADVERTENCIA!");
        builder.setMessage("Está borrando todos los torneos jugados, ¿desea continuar?");

        //se declararon final pues, de otro modo, no pueden ser usados en los listeners
        final Manejador maniwis = new Manejador(this.getApplicationContext());
        final Intent reboot = new Intent(this, Inicio.class);

        // se agregan los botones y sus listeners
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                maniwis.lanzarMisil();
                startActivity(reboot);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // se crea y lanza el dialogo definido
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void loadSpinnerData(){
        Manejador manejador = new Manejador(this.getApplicationContext());

        // mediante un manejador se consiguen los nombres de los torneos
        torneos = manejador.getAllLabels();

        // el string se convierte en un adaptador
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, torneos);

        // estilo del spinner
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //se le da al spinner la fuente de sus datos
        ddlist.setAdapter(dataAdapter);
    }


}
