package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by salvador on 07/12/2015.
 */
public class Compra extends Fragment {
    String respsuesta="";
    Button btnBuscar;
    Button btnMandar;
    Button btnCancelar;
    int nhojas;
    String Saldo ;
    String SaldoRegalo;
    double saldototal;
    double costo;
    double impBlaNeg=1.1;
    double impColor=5.1;
    String numHojas,interval,juegos,blaNeg,colorE,caratula,lados,tamCarta,tamOfi;
    public TextView txtRuta;
    TextView txtContenido;
    Switch swColor;
    Switch swBlayneg;
    Switch swCarcolor;
    Switch swAmblad;
    Switch swCarta;
    Switch swOficio;
    EditText edtxnohojas;
    EditText edtInterval;
    EditText edtJuegosImp;
    private ProgressDialog pDialog;
   public String ubicacion;
    ScrollView scr;
   public String nombre,idTienda;
    ConecInternet conectado= new ConecInternet();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!conectado.verificaConexion(getActivity().getApplicationContext())) {
           conectado.dialgo(getActivity());

        }

    }
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_compra, container, false);
        new getSaldoAT().execute();
        btnBuscar=(Button)view.findViewById(R.id.btnBuscar);
        edtxnohojas=(EditText)view.findViewById(R.id.edt_numhoj);
        edtInterval=(EditText)view.findViewById(R.id.edt_hoj);
        edtJuegosImp=(EditText)view.findViewById(R.id.edt_juegos);

        blaNeg="1";
        tamCarta="1";
        colorE="0";
        caratula="0";
        lados="0";
        tamOfi="0";

        txtRuta=(TextView)view.findViewById(R.id.ruta);
        btnMandar=(Button)view.findViewById(R.id.mandar);
        btnCancelar=(Button)view.findViewById(R.id.btnCancelar);
        swColor=(Switch)view.findViewById(R.id.sw_color);
        swBlayneg=(Switch)view.findViewById(R.id.sw_blayneg);
        swAmblad=(Switch)view.findViewById(R.id.sw_amlad);
        swCarcolor=(Switch)view.findViewById(R.id.sw_carcolor);
        swCarta=(Switch)view.findViewById(R.id.sw_carta);
        swCarta.setChecked(true);
        swOficio=(Switch)view.findViewById(R.id.sw_oficio);
        swBlayneg=(Switch)view.findViewById(R.id.sw_blayneg);
        scr= (ScrollView) view.findViewById(R.id.scrCompra);
        Button btnFolder=(Button)view.findViewById(R.id.btnFolder);

        btnFolder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                getFragmentManager().beginTransaction()
                        .add(R.id.actividad, new Folder()).addToBackStack("compra").commit();
            }

        });

        Button btnEnga=(Button)view.findViewById(R.id.btnEngar);

        btnEnga.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                getFragmentManager().beginTransaction()
                        .add(R.id.actividad, new Engargolados()).commit();
            }

        });
        Log.d("ubicacion : ", "> " + txtRuta.getText().toString());

        swBlayneg.setChecked(true);
        swAmblad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {

                    lados="1";

                } else {
                    lados="0";
                }
            }
        });

        swBlayneg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    swColor.setChecked(false);
                    blaNeg="1";

                } else {
                    swColor.setChecked(true);
                    blaNeg="0";
                }
            }
        });
        swColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    swBlayneg.setChecked(false);
                    swCarcolor.setChecked(false);
                    colorE="1";

                }else{
                    swBlayneg.setChecked(true);
                    colorE="0";
                }
            }
        });
        swCarcolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    swColor.setChecked(false);
                    swBlayneg.setChecked(true);
                    caratula="1";
                }else{
                    caratula="0";
                }
            }
        });
        swCarta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    swOficio.setChecked(false);
                    tamCarta="1";

                }else{
                    swOficio.setChecked(true);
                    tamCarta="0";
                }
            }
        });
        swOficio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    swCarta.setChecked(false);
                    tamOfi="1";
                }else{
                    swCarta.setChecked(true);
                    tamOfi="0";
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scr.setVisibility(View.INVISIBLE);
                ubicacion= "";
                nombre="";
                txtRuta.setText("Archivo");
            }
        });
        btnMandar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!conectado.verificaConexion(getActivity().getApplicationContext())) {
                    conectado.dialgo(getActivity());


                }else {
                    juegos= edtJuegosImp.getText().toString();
                    numHojas=edtxnohojas.getText().toString();
                    interval=edtInterval.getText().toString();
                    try {
                        nhojas = Integer.parseInt(edtxnohojas.getText().toString());
                    } catch (Exception e) {
                        nhojas = 0;
                    }
                    if (ubicacion == "" || ubicacion == null) {
                        Toast.makeText(getActivity().getApplicationContext(), "Debe de seleccionar un archivo",
                                Toast.LENGTH_SHORT).show();
                    } else if (nhojas == 0 || edtxnohojas.getText().toString() == "") {
                        Toast.makeText(getActivity().getApplicationContext(), "Debe de poner numero de hojas a imprimir",
                                Toast.LENGTH_SHORT).show();
                    } else if(juegos.equals("")||edtJuegosImp.getText().toString()==""){

                        Toast.makeText(getActivity().getApplicationContext(), "Debe de poner numero de juegos a imprimir",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        if(swBlayneg.isChecked()==true){
                            costo=Double.parseDouble(numHojas) * impBlaNeg *Double.parseDouble(juegos);
                        }else{


                            costo=Double.parseDouble(numHojas) * impColor *Double.parseDouble(juegos);
                        }
                        new getSaldoAT().execute();
                        Log.d("saldototal : ", "> "+ saldototal);
                        Log.d("costo : ", "> "+ costo);
                        if(costo<=saldototal){
                            new postCompraAT().execute();
                        }else{

                            Toast.makeText(getActivity().getApplicationContext(), "Saldo insuficiente",
                                    Toast.LENGTH_SHORT).show();
                        }





                    }
                }
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                irExplorador();
            }

        });
        try {

            // Exp exp=new Exp();

           // Bundle bundle = this.getArguments();
            SharedPreferences sp = getActivity().getSharedPreferences("prefe", Activity.MODE_PRIVATE);
            nombre = sp.getString("nombrearch","");
            ubicacion = sp.getString("ubicacion","");
            //ubicacion= bundle.getString("ubicacion");
            //nombre= bundle.getString("nombrearch");

            //ubicacion = getArguments().getString("ubicacion");
            //nombre= getArguments().getString("nombrearch");
            // Intent i=getIntent();
            //  ubicacion=i.getExtras().getString("ubicacion");
        }catch (Exception e){

        }
        Log.d("nombre : ", "> "+ nombre);
        if(nombre!= null||nombre.equals("") ) {
            txtRuta.setText(nombre);
        }
        Log.d("txtRuta : ", "> "+ txtRuta.getText().toString());
        if (txtRuta.getText().toString().equals("Archivo")||(txtRuta.getText().toString().equals(""))) {
            scr.setVisibility(View.INVISIBLE);
        }else{
            scr.setVisibility(View.VISIBLE);
        }


        return view;
    }

    class postCompraAT extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Enviando... ");
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


            try {


                SharedPreferences sp =getActivity().getSharedPreferences("prefe", getActivity().MODE_PRIVATE);
                String Apikey = sp.getString("APIkey","");
                idTienda=sp.getString("idTienda","");
                SyncHttpClient client = new SyncHttpClient();
                RequestParams params = new RequestParams();


                Log.d("sucursal : ", "> " + idTienda);
                Log.d("juegos : ", "> "  + juegos);
                Log.d("hojas : ", "> " + numHojas);
                Log.d("intervalo : ", "> " + interval);
                Log.d("totalimpresion : ", "> "  + costo);
                Log.d("blanconegro : ", "> " + blaNeg);
                Log.d("carta : ", "> " + tamCarta);
                Log.d("color : ", "> "  + colorE);
                Log.d("caratula : ", "> "  + caratula);
                Log.d("lados : ", "> "  + lados);
                Log.d("tamOfi : ", "> "  + tamOfi);

                params.put("documento", new File(ubicacion));
                params.put("llave", Apikey);
                params.put("sucursal", idTienda);
                params.put("juegos", juegos);
                params.put("hojas", numHojas);
                params.put("intervalo", interval);
                params.put("totalimpresion", costo);
                params.put("blanconegro", blaNeg);
                params.put("carta", tamCarta);
                params.put("color",colorE );
                params.put("caratula", caratula);
                params.put("lados", lados);
                params.put("oficio", tamOfi);




                client.post("http://hurryprint.devworms.com/class/SubirMovil.php", params, new TextHttpResponseHandler() {


                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                        respsuesta = responseString;
                        Message mesa = new Message();
                        vistaHandler.sendMessage(mesa);
                    }


                });


            } catch (Exception ex) {
                Message mesa = new Message();
                respsuesta = ex.toString();
                vistaHandler.sendMessage(mesa);
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
                    txtRuta.setText("Archivo");
                    scr.setVisibility(View.INVISIBLE);
                }
            });

        }
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
            SharedPreferences sp = getActivity().getSharedPreferences("prefe", Activity.MODE_PRIVATE);
            String body = sp.getString("APIkey","");


            String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/saldo","GET",body,"");
            Log.d("Respuesta : ", "> " + respuesta);
            if(respuesta!="error"){
                try {
                    JSONObject json = new JSONObject(respuesta);

                    String datoUsuario = json.getString("saldo");

                    JSONObject jsonUsuario = new JSONObject(datoUsuario);
                    Saldo = jsonUsuario.getString("Saldo");
                    SaldoRegalo = jsonUsuario.getString("SaldoRegalo");


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
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    saldototal= Double.parseDouble(Saldo)+ Double.parseDouble(SaldoRegalo);

                }
            });

        }
    }


    private Handler puente = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getActivity().getApplicationContext(), "Enviando...",
                    Toast.LENGTH_SHORT).show();
        }
    };


    Handler vistaHandler = new Handler() {
        public void handleMessage(Message msg) {
            Toast.makeText(getActivity().getApplicationContext(),"Enviado correctamente",// respsuesta,
                   Toast.LENGTH_SHORT).show();
            ubicacion="";
            nombre="";
            txtRuta.setText(nombre);


        }

    };
    public void irExplorador(){
       // Intent myIntent = new Intent(getActivity(), Exp.class);
        // getActivity().startActivity(myIntent);
        getFragmentManager().beginTransaction()
                .replace(R.id.actividad, new Exp()).commit();
        //Intent i= new Intent(Compra.this,Exp.class);
        //startActivity(i);

    }

    private void abrirArchivo(){
        try{
            txtRuta.setText(nombre);
            File f= new File(ubicacion);
            if(f==null)
                txtContenido.setText("archivo no valido");


        }catch (Exception e){}
    }
}
