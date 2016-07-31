package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by salvador on 02/12/2015.
 */
public class Cuenta extends Fragment {
    TextView name;
    String nom;
    private ProgressDialog pDialog;
    String fot;
    Button logout;
    ProfilePictureView fotoper;
    MenuItem agreTar;
    TextView saldo,saldoR;
    String Apikey;
    String Saldo ;
    String SaldoRegalo;
    String idUser;
    ConecInternet conectado= new ConecInternet();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cuenta, container, false);
        if (!conectado.verificaConexion(getActivity().getApplicationContext())) {

            conectado.dialgo(getActivity());

        }else {
            name = (TextView) view.findViewById(R.id.cuentaNombre);
            saldoR = (TextView) view.findViewById(R.id.cuentaSaldoReg);
            logout = (Button) view.findViewById(R.id.logout);
            saldo = (TextView) view.findViewById(R.id.cuentaSaldo);
            ImageView imageView = (ImageView) view.findViewById(R.id.imvFotoPerfil);
            ((Application) getActivity().getApplication()).setnavFragment("cuenta");

            fotoper = (ProfilePictureView) view.findViewById(R.id.profilePicture);
            SharedPreferences sp = getActivity().getSharedPreferences("prefe", Activity.MODE_PRIVATE);
            Apikey = sp.getString("APIkey", "");
            idUser = sp.getString("fbuserid", "");
            String nombre = sp.getString("Nombre", "");
            name.setText(nombre);

            fotoper.setProfileId(idUser);
            fotoper.setDrawingCacheEnabled(true);

      /*  ImageView fbImage = ( ( ImageView)fotoper.getChildAt(0));
        Bitmap    originalBitmap  = ( ( BitmapDrawable) fbImage.getDrawable()).getBitmap();


           //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        imageView.setImageDrawable(roundedDrawable);*/

            new getSaldoAT().execute();
       /*Bundle args = this.getActivity().getExtras();
         name= args.getString("nombre");
         fotoper= args.getString("foto");
*/
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSharedPreferences("prefe", 0).edit().clear().commit();

                    LoginManager.getInstance().logOut();
                    Intent salida = new Intent(Intent.ACTION_MAIN); //Llamando a la activity principal
                    getActivity().finish(); // La cerramos.


                }

            });
        }
    return view;
    }
    class getSaldoAT extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cargando...");
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
                    getActivity().runOnUiThread(new Runnable() {
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
            pDialog.dismiss();
            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    saldo.setText(Saldo);
                    saldoR.setText(SaldoRegalo);

                }
            });

        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        if( getActivity().getMenuInflater()== null) {
            getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        }
       // agreTar = menu.add("agregar tarjeta").setIcon(R.drawable.icn_tar);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ade:
                new getSaldoAT().execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
       /* super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_cuenta);*/




}