package com.salvador.devworms.hurryapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by salvador on 01/12/2015.
 */
public class Login extends AppCompatActivity {
 ConecInternet conectado= new ConecInternet();
    AlertDialog alertDialog;
    HttpClient httpclient ;
    HttpPost httppost ;
    TextView txtTel;
    TextView txtPass;
    TextView txtError;
    String encontro;
    JSONParser jsonParser = new JSONParser();
    private ArrayList<NameValuePair> nameValuePairs;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("prefe", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nombrearch", "");
        editor.putString("ubicacion", "");
        editor.commit();
        TextView texto=(TextView)findViewById(R.id.txt_registro);
        txtTel=(TextView)findViewById(R.id.Tel);
        txtPass=(TextView)findViewById(R.id.Pass);


        String myStriValue = sp.getString("APIkey","");
        Log.d("Preference : ", "> " + myStriValue);
        if (myStriValue!=""){
            Intent intent = new Intent(Login.this, MenuActivity.class);

            startActivity(intent);
            finish();

        }
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
        findViewById(R.id.btn_acep).setOnClickListener(new ActionButton1());



    }

    class ActionButton1 implements View.OnClickListener {
        public void onClick(View v) {


            new Thread() {
                public void run() {

                        //add your data
                    JSONParser jsp= new JSONParser();
                    String body= "{\r\n\"contrasena\": \""+txtPass.getText()+"\",\r\n\"telefono\": \""+txtTel.getText()+"\"\r\n}\r\n";
                    String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/usuarios/login","POST",body,"");
                   // Log.d("Respuesta : ", "> " + respuesta);
                    if(respuesta!="error"){
                        try {
                            JSONObject json = new JSONObject(respuesta);

                            String datoUsuario = json.getString("usuario");

                            JSONObject jsonUsuario = new JSONObject(datoUsuario);
                            String apikey = jsonUsuario.getString("APIkey");
                            String nombre = jsonUsuario.getString("Nombre");
                            String idUser= jsonUsuario.getString("Token");
                            SharedPreferences sp = getSharedPreferences("prefe", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("APIkey", apikey);
                            editor.putString("Nombre", nombre);
                            editor.putString("fbuserid", idUser);
                            editor.commit();
                            Intent intent = new Intent(Login.this, MenuActivity.class);

                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), "Usuario o contraseña incorrectos",
                                        Toast.LENGTH_SHORT).show();


                            }
                        });

                    }



                }

            }.start();


        }
    }



}
