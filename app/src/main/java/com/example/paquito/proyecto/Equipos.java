package com.example.paquito.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Equipos extends AppCompatActivity {

    RadioGroup rg;
    ArrayList<String> equipos ;
    EditText etTeam;
    int cont=0;
    Button btStart,btAgrega;
    RadioButton rb2,rb4,rb8;
    int num=0;
    String torneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);

        //se conectan los elementos a los de la vista por id
        rg = (RadioGroup) findViewById(R.id.rgNumEquipos);
        etTeam = (EditText) findViewById(R.id.etEquipo);
        btStart = (Button) findViewById(R.id.btJugar);
        //jugar inicia bloqueado hasta no tener los equipos necesarios
        btStart.setEnabled(false);
        rb2 = (RadioButton) findViewById(R.id.rb2e);
        rb4 = (RadioButton) findViewById(R.id.rb24e);
        rb8 = (RadioButton) findViewById(R.id.rb8e);
        btAgrega = (Button) findViewById(R.id.btAgrega);

        //lista donde se guardan los nombres de los equipos a jugar
        equipos = new ArrayList<String>();

        //se obtiene el torneo del bundle enviado de la ventana anterior y se saca de el el elemento deseado  mediante su etiqueta
        torneo = getIntent().getExtras().getString("nombreTorneo");
    }

    public void cancelar(View v){
        //el metodo de cancelar elimina el torneo que no se completo y regresa a la ventana inicial
        Manejador maniwis = new Manejador(this.getApplicationContext());
        maniwis.eliminaTorneo(torneo);
        Intent chaPaTras = new Intent(this, Inicio.class);
        startActivity(chaPaTras);
    }

    public void iniciar(View view){
        Intent intent;
        Bundle bundle = new Bundle();
        equipos.add(torneo);
        bundle.putStringArrayList("listaEquipos",equipos);

        //dependiendo de el numero de equipos el intent redirige a diferentes ventanas
        if(num==8)
            intent = new Intent(this,Torneo.class)  ;
        else if(num==4)
            intent = new Intent(this,torneo4.class)  ;
        else
            intent = new Intent(this,granFinal.class)  ;
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void agregar(View view){

        //se bloquea el radio buton para que no se cambie el numero de juygadores y evitar errores
        //se va llenando con los nombres hasta obtener los necesarios y se bloquea el boton de agregar y desbloquea el de jugar
        String team = etTeam.getText().toString();
        equipos.add(team);
        cont ++;
        etTeam.setText("");
        Toast.makeText(Equipos.this,"Equipo  "+team+"  agregado al Torneo ",Toast.LENGTH_SHORT).show();
        if(cont>= num){
            btStart.setEnabled(true);
            etTeam.setEnabled(false);
            btAgrega.setEnabled(false);
        }
    }

    //los siguientes metodos son escuchas una vez seleccionado un radio button para bloquear
    //se construyeron por separado para diferenciar el numero contador
    public void RadioButon2(View view){
        num=2;
        bloquear();
    }

    public void RadioButon4(View view){
        num=4;
        bloquear();
    }

    public void RadioButon8(View view){
        num=8;
        bloquear();
    }

    public void bloquear(){
        rb2.setEnabled(false);
        rb4.setEnabled(false);
        rb8.setEnabled(false);
    }

}
