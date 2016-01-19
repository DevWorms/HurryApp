package com.salvador.devworms.hurryapp;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by salvador on 01/12/2015.
 */
public class Login extends AppCompatActivity {
 ConecInternet conectado= new ConecInternet();
    AlertDialog alertDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

       Button btn=(Button)findViewById(R.id.btn_acep);
        TextView texto=(TextView)findViewById(R.id.txt_registro);
        texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!conectado.verificaConexion(getApplicationContext())) {

                    conectado.dialgo(Login.this);

                }else {
                Intent intent = new Intent(Login.this, Registro.class);

                    startActivity(intent);
                    finish();

                }

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!conectado.verificaConexion(getApplicationContext())) {
                    conectado.dialgo(Login.this);

                }else {
                    Intent intent = new Intent(Login.this, MenuActivity.class);
                    startActivity(intent);
                }
            }
        });



    }

  /*  public void openDialog() {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle("hola");
        dialog.show();
    }*/


}
