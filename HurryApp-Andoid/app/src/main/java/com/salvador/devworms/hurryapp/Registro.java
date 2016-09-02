package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by salvador on 03/01/2016.
 */
public class Registro extends AppCompatActivity{
    private ProgressDialog pDialog;
    EditText celu;
    EditText pass,pass2;
    TextView txtNombre;
    String nom,pas,pas2,cel,resp;
    CallbackManager callbackManager;

    private Profile pendingUpdateForUser;

    private ProfileTracker profileTracker;
      private AccessTokenTracker accessTokenTracker;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                              setProfile(currentProfile);
                            }
                  };

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        Log.e("FB", String.valueOf(accessToken));
                        Profile profile = Profile.getCurrentProfile();

                        if (profile != null) {

                            Log.e("FB",  profile.getName());
                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        Profile.fetchProfileForCurrentAccessToken();
        setProfile(Profile.getCurrentProfile());

        SharedPreferences sp = getSharedPreferences("prefe", Activity.MODE_PRIVATE);
        String nombre = sp.getString("Nombre","");
        txtNombre= (TextView)findViewById(R.id.txtNomRegis);
        celu=(EditText)findViewById(R.id.edt_celular);
        pass=(EditText)findViewById(R.id.edt_Pas);
        pass2=(EditText)findViewById(R.id.edt_pass2);
        Button btnCanRegis=(Button)findViewById(R.id.btnCanRegis);

        Button btnRegis=(Button)findViewById(R.id.btn_registrar);
        btnCanRegis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginManager.getInstance().logOut();
                    SharedPreferences sp = getSharedPreferences("prefe", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("APIkey", "");
                    editor.putString("Nombre", "");
                    editor.putString("fbuserid", "");
                    editor.commit();

                    Intent intent = new Intent(Registro.this, Login.class);
                    startActivity(intent);
                    finish();
                }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nom= txtNombre.getText().toString();
                pas=pass.getText().toString();
                pas2=pass2.getText().toString();
                cel= celu.getText().toString();
                if( pas.equals("") || pas == null ||cel.equals("") || cel == null ) {
                    Toast.makeText(Registro.this,"Falta llenar campos.",Toast.LENGTH_SHORT).show();
                }else{
                    if(pas.equals(pas2)){
                        if(nom.equals("Nombre")){
                            Toast.makeText(Registro.this,"Hubo un error con su registro vuelva a intentarlo.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registro.this, Login.class);
                            startActivity(intent);
                            finish();
                        }else{
                            new getRegstroAT().execute();
                        }

                    }else{
                        Toast.makeText(Registro.this,"La contraseña no coincide",Toast.LENGTH_SHORT).show();
                    }
                }



            }

        });

    }
    private void setProfile(Profile profile) {

                 if (profile == null) {

                     } else {
                     Log.d("fb : ", "> " + profile.getCurrentProfile().getFirstName());
                     if (txtNombre != null) {
                         txtNombre.setVisibility(View.VISIBLE);
                         txtNombre.setText(profile.getCurrentProfile().getName());
                         SharedPreferences sp =
                                 getSharedPreferences("prefe", Activity.MODE_PRIVATE);
                         SharedPreferences.Editor editor = sp.edit();
                         editor.putString("fbtoken", AccessToken.getCurrentAccessToken().getToken());
                         editor.putString("fbuserid",  AccessToken.getCurrentAccessToken().getUserId());
                         editor.putString("Nombre",  profile.getCurrentProfile().getName().toString());
                         editor.commit();
                     }
                 }

            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(Profile.getCurrentProfile() != null) {
            setProfile(Profile.getCurrentProfile());
            Log.d("Resuult : ", "> " + Profile.getCurrentProfile().getFirstName());
        }
    }
    class getRegstroAT extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registro.this);
            pDialog.setMessage("Registrando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Albums JSON
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            //add your data

            SharedPreferences sp = getSharedPreferences("prefe", Activity.MODE_PRIVATE);
            String token = sp.getString("fbuserid","");
             Log.d("nom : ", "> " + nom);
            Log.d("pas : ", "> " + pas);
            Log.d("cel : ", "> " + cel);
            Log.d("token : ", "> " + token);

            String body= "{\n" +
                    "\"nombre\" : \""+nom+"\",\n" +
                    "\"contrasena\" : \""+pas+"\",\n" +
                    "\"telefono\" : \""+cel+"\",\n" +
                    "\"token\" : \""+token+"\"\n" +
                    "}";
            JSONParser jsp= new JSONParser();



            String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/usuarios/registro","POST",body,"");
            Log.d("Registro : ", "> " + respuesta);
            if(respuesta!="error"){
                try{ JSONObject json = new JSONObject(respuesta);

                    String apikey = json.getString("APIkey");
                    Log.d("Registroapikey : ", "> " + apikey);
                    sp = getSharedPreferences("prefe", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("APIkey", apikey);
                    editor.commit();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                resp="registro correcto";


            }else {
                resp="Usuario ya registrado, te registraste anteriormente";
            }



            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            Log.d("RegistroResp : ", "> " + resp);
            pDialog.dismiss();
            if(resp!="registro invalido"){
                Intent i = new Intent(Registro.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
            // updating UI from Background Thread
           runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(Registro.this,resp,Toast.LENGTH_SHORT).show();




                }
            });

        }
    }





}
