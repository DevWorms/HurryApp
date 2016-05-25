package com.salvador.devworms.hurryapp;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by salvador on 15/01/2016.
 */
public class Historial extends Fragment {
    private ProgressDialog pDialog;
    JSONArray folios = null;
    JSONArray infofolios = null;
    String[] nombreArch ;
    String[] folio ;
    String[] esatus;
    ListView miLista;
    String numfolio;
    String nomfolio;
    String costfolio;
    String fechfolio;
    String sucufolio;
    String stafolio;
    Button dismissButton ;
    TextView txtfol;
    TextView txtnom;
    TextView txtcost;
    TextView txtfecha;
    TextView txttienda;
    TextView txtstatus;

    FoliosAdapter adapter;
    int pot;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_historial, container, false);




         miLista = (ListView) view.findViewById(R.id.list);

        new getHistorialAT().execute();



        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_historial_info);
                dismissButton = (Button) dialog.findViewById(R.id.btn_ok);
                txtfol=(TextView)dialog.findViewById(R.id.txt_histo_info_folio);
                txtnom=(TextView)dialog.findViewById(R.id.txt_histo_info_namedocu);
                txtcost=(TextView)dialog.findViewById(R.id.txt_histo_info_precio);
                txtfecha=(TextView)dialog.findViewById(R.id.txt_histo_info_fecha);
                txttienda=(TextView)dialog.findViewById(R.id.txt_histo_info_sucur);
                txtstatus=(TextView)dialog.findViewById(R.id.txt_histo_info_hora);

                new getFolioEspAT().execute();
                //Aqui haces que tu layout se muestre como dialog


                dialog.setTitle("DATOS DE FOLIO");
                pot=position;
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        txtfol.setText("");
                        txtnom.setText("");
                        txtcost.setText("");
                        txtfecha.setText("");
                        txttienda.setText("");
                        txtstatus.setText("");
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });

        return view;
    }
    class getHistorialAT extends AsyncTask<String, String, String> {

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


            JSONParser jsp= new JSONParser();
            SharedPreferences sp = getActivity().getSharedPreferences("prefe", getActivity().MODE_PRIVATE);
            String body= sp.getString("APIkey","");
            String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/folios","GET",body,"");
            //Log.d("RespuestaFol : ", "> " + respuesta);
            if(respuesta!="error"){
                try {
                    JSONObject json = new JSONObject(respuesta);

                    String datoUsuario = json.getString("folios");

                    folios = new JSONArray(datoUsuario);
                    nombreArch = new String[folios.length()];
                    folio = new String[folios.length()];
                    esatus = new String[folios.length()];
                    Log.d("Folios : ", "> " + folios.toString());
                    if (folios.length() >=0) {

                        for (int i = 0; i < folios.length(); i++) {
                            Log.d("Entradas : ", "> " + i);
                            JSONObject c = folios.getJSONObject(i);



                            String numfol = c.getString("folio_documento");

                            String nomdoc = c.getString("nombre_documento");

                            String sta = c.getString("descripcion");

                            nombreArch[i]=nomdoc;
                            folio[i]=numfol;
                            esatus[i]=sta;



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

                    if(folio!=null) {
                        adapter = new FoliosAdapter(getActivity(), nombreArch, folio, esatus);
                        miLista.setAdapter(adapter);
                    }



                }
            });

        }
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
        // agreTar = menu.add("agregar tarjeta").setIcon(R.drawable.icn_tar);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ade:
                new getHistorialAT().execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    class getFolioEspAT extends AsyncTask<String, String, String> {

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


            JSONParser jsp= new JSONParser();
            SharedPreferences sp =getActivity().getSharedPreferences("prefe", getActivity().MODE_PRIVATE);
            String body= sp.getString("APIkey","");

            String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/folio","GETF",body,folio[pot]);
           // Log.d("infofolio : ", "> " + respuesta);
            if(respuesta!="error"){
                try {
                    JSONObject json = new JSONObject(respuesta);

                    String datoUsuario = json.getString("descripcion");


                    JSONObject jsonUsuario = new JSONObject(datoUsuario);
                    numfolio = jsonUsuario.getString("Folio");
                    nomfolio = jsonUsuario.getString("Nombre");
                    costfolio = jsonUsuario.getString("Costo");
                    fechfolio = jsonUsuario.getString("Fecha");
                    sucufolio = jsonUsuario.getString("Sucursal");
                    stafolio = jsonUsuario.getString("Status");




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

                    txtfol.setText(numfolio);
                    txtnom.setText(nomfolio);
                    txtcost.setText(costfolio);
                    txtfecha.setText(fechfolio);
                    txttienda.setText(sucufolio);
                    txtstatus.setText(stafolio);



                }
            });

        }
    }
}
