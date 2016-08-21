package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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
    double impBlaNeg=1;
    double impColor=5;
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
    String respCom,permitirColor,permitirBlaNeg;
    String [] FolderArray,EngargoArray;
    String type;
    Button btnVisuaizar;
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
        ((Application) getActivity().getApplication()).setnavFragment("compra");
        btnBuscar=(Button)view.findViewById(R.id.btnBuscar);
        edtxnohojas=(EditText)view.findViewById(R.id.edt_numhoj);
        edtInterval=(EditText)view.findViewById(R.id.edt_hoj);
        edtJuegosImp=(EditText)view.findViewById(R.id.edt_juegos);
        if(((Application) getActivity().getApplication()).getnumeroHojas().equals("0")){

        }else{
            edtxnohojas.setText(((Application) getActivity().getApplication()).getnumeroHojas());
        }
        if(((Application) getActivity().getApplication()).getnumeroJuegos().equals("0")){

        }else{
            edtJuegosImp.setText(((Application) getActivity().getApplication()).getnumeroJuegos());
        }


        blaNeg="1";
        tamCarta="1";
        colorE="0";
        caratula="0";
        lados="0";
        tamOfi="0";
        permitirColor=((Application) getActivity().getApplication()).gethColor();
        permitirBlaNeg=((Application) getActivity().getApplication()).gethBlancoNegro();
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
        btnVisuaizar=(Button)view.findViewById(R.id.btnVisua);

        btnVisuaizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                String newName= nombre.replace(".",",");

                String []nomArch = newName.split(",");
                if(nomArch[nomArch.length-1].equals("pdf")){
                    type ="application/pdf";
                }else {

                    type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                }
                Uri uri = Uri.parse("file://"+ubicacion);
                intent.setDataAndType(uri, type);
                startActivity(intent);
            }

        });

        Button btnFolder=(Button)view.findViewById(R.id.btnFolder);

        btnFolder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((Application) getActivity().getApplication()).setnumeroHojas(edtxnohojas.getText().toString());
                ((Application) getActivity().getApplication()).setnumeroJuegos(edtJuegosImp.getText().toString());
                getFragmentManager().beginTransaction()
                        .add(R.id.actividad, new Folder()).addToBackStack("compra").commit();
            }

        });

        Button btnEnga=(Button)view.findViewById(R.id.btnEngar);

        btnEnga.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String numeroHojas;
                String numeroJuegos;
                if(edtxnohojas.getText().toString().equals("")){
                    numeroHojas="0";
                }else{
                    numeroHojas= edtxnohojas.getText().toString();
                }
                if(edtJuegosImp.getText().toString().equals("")){
                    numeroJuegos="0";
                }else{
                    numeroJuegos= edtJuegosImp.getText().toString();
                }
                ((Application) getActivity().getApplication()).setnumeroHojas(numeroHojas);
                ((Application) getActivity().getApplication()).setnumeroJuegos(numeroJuegos);
                getFragmentManager().beginTransaction()
                        .add(R.id.actividad, new Engargolados()).commit();
            }

        });

       if(permitirBlaNeg.equals("1")){
           swBlayneg.setChecked(true);
           blaNeg="1";
       }else{
           swBlayneg.setEnabled(false);
           swColor.setEnabled(false);
           swColor.setChecked(true);
           blaNeg="0";
       }

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
        if(permitirColor.equals("1") && permitirBlaNeg.equals("0")){
            swColor.setChecked(true);
            colorE="1";
        }else{
            swBlayneg.setChecked(true);
            colorE="0";
        }
        if(permitirColor.equals("0")){
            swColor.setEnabled(false);
            swBlayneg.setEnabled(false);
        }
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
                btnVisuaizar.setVisibility(View.INVISIBLE);
                ((Application) getActivity().getApplication()).setnumeroHojas("0");
                ((Application) getActivity().getApplication()).setnumeroJuegos("0");
                txtRuta.setText("Archivo");

            }
        });
        btnMandar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!conectado.verificaConexion(getActivity().getApplicationContext())) {
                    conectado.dialgo(getActivity());


                }else {

                    double costoporhoja;
                    juegos= edtJuegosImp.getText().toString();
                    numHojas=edtxnohojas.getText().toString();
                    interval=edtInterval.getText().toString();
                    try {
                        nhojas = Integer.parseInt(edtxnohojas.getText().toString());
                    } catch (Exception e) {
                        nhojas = 0;
                    }
                    if (ubicacion == "" || ubicacion == null) {
                        Toast.makeText(getActivity().getApplicationContext(), "Por favor seleccione un archivo",
                                Toast.LENGTH_SHORT).show();
                    } else if (nhojas == 0 || edtxnohojas.getText().toString() == "") {
                        Toast.makeText(getActivity().getApplicationContext(), "Por favor ingresa el número de hojas a imprimir",
                                Toast.LENGTH_SHORT).show();
                    } else if(juegos.equals("")||edtJuegosImp.getText().toString()==""){

                        Toast.makeText(getActivity().getApplicationContext(), "Por favor ingresa el número de juegos a imprimir",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        if(swBlayneg.isChecked()==true){
                            costo=Double.parseDouble(numHojas) * impBlaNeg *Double.parseDouble(juegos);
                            costoporhoja=costo;
                            costo=costo + ((Application) getActivity().getApplication()).getCostoFolder() + ((Application) getActivity().getApplication()).getCostoEngargolado();
                        }else{


                            costo=Double.parseDouble(numHojas) * impColor *Double.parseDouble(juegos);
                            costoporhoja=costo;
                            costo=costo + ((Application) getActivity().getApplication()).getCostoFolder() +((Application) getActivity().getApplication()).getCostoEngargolado();
                        }


                        Log.d("costo : ", "> "+ costo);
                        Log.d("costo folder : ", "> "+ ((Application) getActivity().getApplication()).getCostoFolder());
                        Log.d("costo engargo : ", "> "+ ((Application) getActivity().getApplication()).getCostoEngargolado());
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Resumen de compra")
                                .setMessage("Tu impresión total sera: $"+costo+"\n"+"   ·Costo de impresión: $"+costoporhoja+"\n"+"   ·Costo por folders: $"+((Application) getActivity().getApplication()).getCostoFolder()+"\n"+"   ·Costo por engargolados: $"+((Application) getActivity().getApplication()).getCostoEngargolado())
                                .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override public void onClick(DialogInterface dialog, int which) {
                                        // do the acknowledged action, beware, this is run on UI thread
                                        new postCompraAT().execute();
                                    }
                                })
                                .create()
                                .show();
                           //
                        // new postCompraAT().execute();







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

            nombre = ((Application) getActivity().getApplication()).getArchivo();
            ubicacion = ((Application) getActivity().getApplication()).getUbicacion();



        if(nombre!= null ) {
            txtRuta.setText(nombre);
        }

        if (txtRuta.getText().toString().equals("Archivo")||(txtRuta.getText().toString().equals(""))) {
            scr.setVisibility(View.INVISIBLE);
            btnVisuaizar.setVisibility(View.INVISIBLE);
        }else{
            scr.setVisibility(View.VISIBLE);
            btnVisuaizar.setVisibility(View.VISIBLE);
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
            pDialog.setMessage("Enviando... si desea cancelar presione fuera ");
            pDialog.setIndeterminate(false);
            //pDialog.setCancelable(false);
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
                FolderArray= ((Application) getActivity().getApplication()).getFolderArray();
                EngargoArray=((Application) getActivity().getApplication()).getEngarArray();

                Log.d("totalimpresion : ", "> "  + costo);

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

                //Folders

                params.put("FolderBeige" , FolderArray[0]);
                params.put("FolderAzul" , FolderArray[1]);
                params.put("FolderRosa" , FolderArray[4]);
                params.put("FolderVerde" , FolderArray[3]);
                params.put("FolderGuinda" , FolderArray[6]);
                params.put("FolderAzulIntenso" , FolderArray[7]);
                params.put("FolderRojo" , FolderArray[2]);
                params.put("FolderNegro" , FolderArray[5]);
                params.put("FolderMorado" , FolderArray[8]);
                //Engargolados
                params.put("pBlanco" , EngargoArray[0]);
                params.put("pAzul" , EngargoArray[1]);
                params.put("pRojo" , EngargoArray[2]);
                params.put("pVerde" , EngargoArray[3]);
                params.put("pAmarillo" , EngargoArray[4]);
                params.put("pNegro" , EngargoArray[5]);
                params.put("pGuinda" , EngargoArray[6]);
                params.put("pAzulFuerte" , EngargoArray[7]);
                params.put("pGris" , EngargoArray[8]);
                params.put("pMorado" , EngargoArray[9]);


                client.post("http://hurryprint.devworms.com/class/SubirMovil.php", params, new TextHttpResponseHandler() {


                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                        respsuesta = responseString;
                        Message mesa = new Message();
                        vistaHandler.sendMessage(mesa);
                        respCom="1";
                    }


                });


            } catch (Exception ex) {
                Log.d("Error : ", "> error");
                Message mesa = new Message();
                respsuesta = ex.toString();
                vistaHandler.sendMessage(mesa);
                respCom="Error al enviar, vuelva a intendarlo";
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
                    for(int i=0;i<=9;i++) {
                        Log.d("borrar array : ", ">"+i);
                        ((Application) getActivity().getApplication()).setFolderArray("0", i);
                        FolderArray[i]="0";
                        ((Application) getActivity().getApplication()).setEngarArray("0", i);
                        EngargoArray[i]="0";
                    }
                    ((Application) getActivity().getApplication()).setArchivo("");
                    ((Application) getActivity().getApplication()).setUbicacion("");
                    ((Application) getActivity().getApplication()).setCostoFolder(0);
                    ((Application) getActivity().getApplication()).setCostoEngargolado(0);
                    ((Application) getActivity().getApplication()).setnumeroHojas("0");
                    ((Application) getActivity().getApplication()).setnumeroJuegos("0");

                }
            });

        }
    }





    Handler vistaHandler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if(respCom.equals("1")){
                JSONObject json = new JSONObject(respsuesta);
                    Log.d("respsuesta: ", ">"+respsuesta);
                Toast.makeText(getActivity().getApplicationContext(),"Lánzate a la sucursal por tus impresiones",// respsuesta,
                        Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),respCom,// respsuesta,
                            Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }




            ubicacion="";
            nombre="";
            txtRuta.setText(nombre);
            btnVisuaizar.setVisibility(View.INVISIBLE);


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


}
