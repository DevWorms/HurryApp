package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by salvador on 21/12/2015.
 */
public class Tiendas extends Fragment {
    ArrayList<HashMap<String, String>> tiendaList;
    String[] tiendasarray ;
    String[] idarray ;
    String[] dispoarray;
    String[] bnarray;
    String[] colorarray;

    ListViewAdapter adapter;
    private ProgressDialog pDialog;
    JSONArray tienda = null;
    String Apikey;
    ListView myListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tiendas, container, false);
         myListView = (ListView) rootView.findViewById(R.id.mytiendas);

        SharedPreferences sp =getActivity().getSharedPreferences("prefe", getActivity().MODE_PRIVATE);
        Apikey = sp.getString("APIkey","");

        idarray = new String[10];
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Log.d("tienda : ", "Color> " + colorarray[position] +" BLanco y negro>"+  bnarray[position]);
                if(colorarray[position].equals("0") &&  bnarray[position].equals("0")){
                    Toast.makeText(getActivity().getApplicationContext(),"Por el momento no contamos con impresiones en esta sucursal",// respsuesta,
                            Toast.LENGTH_SHORT).show();
                }else{
                    Calendar c1 = Calendar.getInstance();
                    Log.d("Hora del dia : ", "hora> "+c1.get(Calendar.HOUR_OF_DAY) );
                    if(c1.get(Calendar.HOUR_OF_DAY)> 21){
                        Toast.makeText(getActivity().getApplicationContext(),"Sus impresiones estaran al otro dia",// respsuesta,
                                Toast.LENGTH_SHORT).show();
                    }else if(c1.get(Calendar.HOUR_OF_DAY)< 6){
                        Toast.makeText(getActivity().getApplicationContext(),"Sus impresiones estaran encuanto abra la tienda",// respsuesta,
                                Toast.LENGTH_SHORT).show();
                    }
                    getFragmentManager().beginTransaction()
                            .replace(R.id.actividad, new Compra()).commit();
                    SharedPreferences  sp = getActivity().getSharedPreferences("prefe", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("idTienda",idarray[position] );
                    editor.commit();
                    ((Application) getActivity().getApplication()).sethBlancoNegro(bnarray[position]);
                    ((Application) getActivity().getApplication()).sethColor(colorarray[position]);


                    Compra fragment = new Compra();


                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();


                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.actividad, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
        new getTiendasAT().execute();


                return rootView;
    }
    class getTiendasAT extends AsyncTask<String, String, String> {

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
            tiendaList= new ArrayList<HashMap<String, String>>();

            JSONParser jsp= new JSONParser();
            String body= Apikey;
            String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/sucursales","GET",body,"");
           Log.d("Tiendas : ", "> " + respuesta);
            if(respuesta!="error"){
                try {
                    JSONObject json = new JSONObject(respuesta);

                    String datoUsuario = json.getString("sucursal");

                    tienda = new JSONArray(datoUsuario);
                    tiendasarray = new String[tienda.length()];
                    idarray = new String[tienda.length()];
                    dispoarray = new String[tienda.length()];
                    bnarray = new String[tienda.length()];
                    colorarray = new String[tienda.length()];

                    if (tienda.length() >=0) {

                        for (int i = 0; i < tienda.length(); i++) {

                            JSONObject c = tienda.getJSONObject(i);



                            String id = c.getString("id_tienda");

                            String name = c.getString("nombre_tienda");

                            String estado = c.getString("abierto");

                            String color = c.getString("color");

                            String blanNeg = c.getString("blanco_negro");

                            idarray[i]=id;
                            tiendasarray[i]=name;
                            dispoarray[i]=estado;
                            colorarray[i]=color;
                            bnarray[i]=blanNeg;




                        }
                        // JSONObject jsonUsuario = new JSONObject(datoUsuario);

                    }else{}

                    } catch (JSONException e){
                        e.printStackTrace();
                    }


            }else {}



            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            pDialog.dismiss();
            // updating UI from Background Thread
           getActivity().runOnUiThread(new Runnable() {
                public void run() {


                    adapter = new ListViewAdapter(getActivity(), tiendasarray,dispoarray,colorarray,bnarray );
                    myListView.setAdapter(adapter);



                }
            });

        }
    }



}
