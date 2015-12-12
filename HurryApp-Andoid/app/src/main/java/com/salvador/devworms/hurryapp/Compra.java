package com.salvador.devworms.hurryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by salvador on 07/12/2015.
 */
public class Compra extends AppCompatActivity {

    Button btnBuscar;
    TextView txtRuta;
    TextView txtContenido;
    String ubicacion;
    int fir=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_compra);

        btnBuscar=(Button)findViewById(R.id.btnBuscar);
        txtRuta=(TextView)findViewById(R.id.ruta);


        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                irExplorador();
            }

        });
        try {
            Intent i=getIntent();
            ubicacion=i.getExtras().getString("ubicacion");
        }catch (Exception e){

        }
        if(ubicacion!="")

        selectArchivo();


    }
    public void irExplorador(){
        Intent i= new Intent(Compra.this,Exp.class);
        startActivity(i);

    }
    private void selectArchivo(){
        try {
            if(ubicacion!=null)
                txtRuta.setText(ubicacion);

            File f= new File(ubicacion);
            if(f==null)
                txtContenido.setText("archivo no valido");

            Toast.makeText(getApplicationContext(), "Archivo cargado.",
                    Toast.LENGTH_SHORT).show();
            // abrirArchivo();

        }catch (Exception e){}

    }
    private void abrirArchivo(){
        try{
            txtRuta.setText(ubicacion);
            File f= new File(ubicacion);
            if(f==null)
                txtContenido.setText("archivo no valido");
            FileReader fr = new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            String texto=br.readLine();
            String aux="";
            while(texto!=null){
                aux=aux+texto;
                texto=br.readLine();

            }
            txtContenido.setText(aux);
            br.close();


        }catch (Exception e){}
    }
}
