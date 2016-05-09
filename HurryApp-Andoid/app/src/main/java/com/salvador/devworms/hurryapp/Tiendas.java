package com.salvador.devworms.hurryapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        // A. Creamos el arreglo de Strings para llenar la lista
        /*String[] cosasPorHacer = new String[] { "Eart edificio de graduados",
                "Eart calle canela","Eart edificio pesados"};*/

        // B. Creamos un nuevo ArrayAdapter con nuestra lista de cosasPorHacer
        SharedPreferences sp =getActivity().getSharedPreferences("prefe", getActivity().MODE_PRIVATE);
        Apikey = sp.getString("APIkey","");
       new getTiendasAT().execute();
        ;
        //enables filtering for the contents of the given ListView
       /* listView.setTextFilterEnabled(true);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.formato_tiendas, cosasPorHacer);

        // C. Seleccionamos la lista de nuestro layout
        ListView miLista = (ListView) rootView.findViewById(R.id.mytiendas);

        // D. Asignamos el adaptador a nuestra lista
        miLista.setAdapter(arrayAdapter);

        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.actividad, new Compra()).commit();

            }
        });*/

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
            String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/sucursales","GET",body);
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
                    Log.d("tienda : ", "> " + tienda.toString());
                    if (tienda.length() >=0) {

                        for (int i = 0; i < tienda.length(); i++) {
                            Log.d("Entradas : ", "> " + i);
                            JSONObject c = tienda.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<String, String>();

                            String id = c.getString("id_tienda");
                            Log.d("id : ", "> " + id);
                            String name = c.getString("nombre_tienda");
                            Log.d("name : ", "> " + name);
                            String estado = c.getString("abierto");
                            Log.d("estado : ", "> " + estado);
                            String color = c.getString("color");
                            Log.d("color : ", "> " + color);
                            String blanNeg = c.getString("blanco_negro");
                            Log.d("bn : ", "> " + blanNeg);
                            idarray[i]=id;
                            tiendasarray[i]=name;
                            dispoarray[i]=estado;
                            colorarray[i]=color;
                            bnarray[i]=blanNeg;

                            map.put("id", id);
                            map.put("name", name);
                            map.put("estado", estado);
                            map.put("color", color);
                            map.put("bn", blanNeg);
                            Log.d("map : ", "> " + map.toString());
                            // adding HashList to ArrayList
                            tiendaList.add(map);


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

                   /* ListAdapter adapter = new ArrayAdapter<String>( getActivity(),
                            R.layout.formato_tiendas,R.id.txtNomTienda);
                    // updating listview

                    myListView.setAdapter(adapter);*/

                }
            });

        }
    }
    public void getTiendas(){
        new Thread() {
            public void run() {

                //add your data
                JSONParser jsp= new JSONParser();
                String body= Apikey;
                String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/sucursales","GET",body);
                Log.d("Tiendas : ", "> " + respuesta);
                if(respuesta!="error"){
                    try {
                        JSONObject json = new JSONObject(respuesta);

                        String datoUsuario = json.getString("sucursal");

                       // JSONObject jsonUsuario = new JSONObject(datoUsuario);




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{


                }



            }

        }.start();
    }



}
