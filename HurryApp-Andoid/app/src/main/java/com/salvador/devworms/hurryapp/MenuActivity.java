package com.salvador.devworms.hurryapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView name;
   public TextView txtSaldo;
   public TextView txtSaldoReg;
    ProfilePictureView fotoper;
   public String inifbnombre;
   public String inifbfoto;
    String pantall;
    String Apikey;
    String Saldo ;
    String SaldoRegalo;
    String idUser;
    Bitmap bitmapProfile;
    ImageView imageViewFacebookPicture;
    ConecInternet conectado= new ConecInternet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!conectado.verificaConexion(getApplicationContext())) {
            conectado.dialgo(MenuActivity.this);
        }
        setContentView(R.layout.activity_menu);
        txtSaldo=(TextView)findViewById(R.id.saldo);
        txtSaldoReg=(TextView)findViewById(R.id.saldoRegalo);
        name=(TextView)findViewById(R.id.nombreperfil);
        imageViewFacebookPicture=(ImageView)findViewById(R.id.imageViewFacebookPicture);
        //fotoper=(ProfilePictureView)findViewById(R.id.profilePicture);
        SharedPreferences sp = getSharedPreferences("prefe", Activity.MODE_PRIVATE);
        Apikey = sp.getString("APIkey","");
        idUser = sp.getString("fbuserid","");
        name.setText(sp.getString("Nombre","nombre"));

        Log.d("idUser : ", "> "+idUser);
        new getFacebookPicture().execute();
       // fotoper.setProfileId(idUser);

        ///***************Barra***************************************************
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        ///***************Barra***************************************************

        ///***************Menu***************************************************
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ///***************Menu***************************************************

        ///***************Fragment***************************************************
        getFragmentManager().beginTransaction()
                .replace(R.id.actividad, new Tiendas()).commit();
        getFragmentManager().beginTransaction().addToBackStack(null);
        ///***************Fragment***************************************************


    }

    class getFacebookPicture extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        protected String doInBackground(String... args) {
            // Building Parameters
            //add your data


            try {
                bitmapProfile = getFacebookProfilePicture(idUser);
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            Log.d("Entro final : ", "> SI");

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    Bitmap originalBitmap  = bitmapProfile;
                    //creamos el drawable redondeado
                    RoundedBitmapDrawable roundedDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

                    //asignamos el CornerRadius
                    roundedDrawable.setCornerRadius(originalBitmap.getHeight());
                    imageViewFacebookPicture.setImageDrawable(roundedDrawable);

                }
            });

        }
    }
    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
        URL imageUrl = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        Bitmap bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());

        return bitmap;
    }
    class getSaldoAT extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting Albums JSON
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            //add your data
            Log.d("Entro : ", "> SI");
            JSONParser jsp= new JSONParser();

            String body= Apikey;
            Log.d("Apikey : ", "> " + Apikey);
            String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/saldo","GET",body,"");
            Log.d("Respuesta : ", "> " + respuesta);
            if(respuesta!="error"){
                try {
                    JSONObject json = new JSONObject(respuesta);

                    String datoUsuario = json.getString("saldo");

                    JSONObject jsonUsuario = new JSONObject(datoUsuario);
                    Saldo = jsonUsuario.getString("Saldo");
                    SaldoRegalo = jsonUsuario.getString("SaldoRegalo");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{


            }


            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            Log.d("Entro final : ", "> SI");
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    txtSaldo.setText(Saldo);
                    txtSaldoReg.setText(SaldoRegalo);

                }
            });

        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.e("key pressed","Star");

    }
    public void onResume(){
        super.onResume();
        Log.e("key pressed","Resumen");
        if (!conectado.verificaConexion(getApplicationContext())) {
            conectado.dialgo(MenuActivity.this);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        Log.e("key pressed","Save");
    }

    @Override
    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
        Log.e("key pressed","Restore");
    }

    public void onBackPressed() {
        Log.e("key pressed","Back");
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            /*if (getFragmentManager().getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry first = getFragmentManager().getBackStackEntryAt(0);
                getFragmentManager().popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }*/
            getFragmentManager().popBackStack();
        }

/*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);

       // MenuItem item = menu.getItem(0);


        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();

    }
    @SuppressLint("SetTextI18n")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.e("key pressed","actualiza saldo");
        new getSaldoAT().execute();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("back key pressed","Back key pressed");
            String fragmentActual=((Application) this.getApplication()).getnavFragment();
            if(fragmentActual.equals("compra")){
                getFragmentManager().beginTransaction()
                        .replace(R.id.actividad, new Tiendas()).commit();
            }else if (fragmentActual.equals("folder") ||fragmentActual.equals("engargolado") ||fragmentActual.equals("busca")  ){
                getFragmentManager().beginTransaction()
                        .replace(R.id.actividad, new Compra()).commit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recarga) {
            item.setTitle("Recarga");
           /*  getFragmentManager().beginTransaction()
                    .replace(R.id.actividad, new Recarga()).commit();*/
            Toast.makeText(getApplicationContext(), "Próximamente",// respsuesta,
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_imprime) {
            item.setTitle("Imprimir");
            getFragmentManager().beginTransaction()
                    .replace(R.id.actividad, new Tiendas()).commit();

        } else if (id == R.id.nav_cuenta) {
            item.setTitle("Perfil");
            getFragmentManager().beginTransaction()
                    .replace(R.id.actividad, new Cuenta()).commit();

        }  else if (id == R.id.nav_historial) {
            item.setTitle("Historial");
            getFragmentManager().beginTransaction()
                    .replace(R.id.actividad, new Historial()).commit();

        } else if (id == R.id.nav_acercade) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.actividad, new AcercaDe()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
