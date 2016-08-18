package com.salvador.devworms.hurryapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    CallbackManager callbackManager;
    List<String> list= Arrays.asList("public_profile") ;
    JSONParser jsonParser = new JSONParser();
    private ArrayList<NameValuePair> nameValuePairs;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SharedPreferences sp = getSharedPreferences("prefe", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nombrearch", "");
        editor.putString("ubicacion", "");
        editor.commit();

        txtTel=(TextView)findViewById(R.id.Tel);
        txtPass=(TextView)findViewById(R.id.Pass);


        String myStriValue = sp.getString("APIkey","");
        Log.d("Preference : ", "> " + myStriValue);
        if (myStriValue!=""){
            Intent intent = new Intent(Login.this, MenuActivity.class);

            startActivity(intent);
            finish();

        }

        findViewById(R.id.btn_acep).setOnClickListener(new ActionButton1());

        findViewById(R.id.btnFb).setOnClickListener(new ActionButton2());


    }
    class ActionButton2 implements View.OnClickListener {
        public void onClick(View v) {


            Intent intent = new Intent(Login.this, Registro.class);
            startActivity(intent);
            finish();


        }
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
