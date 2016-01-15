package com.salvador.devworms.hurryapp;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

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
    public TextView txtRuta;
    TextView txtContenido;
    Switch swColor;
    Switch swBlayneg;
    Switch swCarcolor;
    Switch swAmblad;
    Switch swCarta;
    Switch swOficio;
    EditText edtxnohojas;
   public String ubicacion;
   public String nombre;
    ConecInternet conectado= new ConecInternet();
    int fir=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!conectado.verificaConexion(getActivity().getApplicationContext())) {
           conectado.dialgo(getActivity());

        }

    }
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_compra, container, false);

        btnBuscar=(Button)view.findViewById(R.id.btnBuscar);
        edtxnohojas=(EditText)view.findViewById(R.id.edx_numhoj);

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
        swBlayneg.setChecked(true);
        swBlayneg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    swColor.setChecked(false);

                } else {
                    swColor.setChecked(true);
                }
            }
        });
        swColor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    swBlayneg.setChecked(false);
                    swCarcolor.setChecked(false);

                }else{
                    swBlayneg.setChecked(true);
                }
            }
        });
        swCarcolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    swColor.setChecked(false);
                    swBlayneg.setChecked(true);
                }
            }
        });
        swCarta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    swOficio.setChecked(false);

                }else{
                    swOficio.setChecked(true);
                }
            }
        });
        swOficio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    swCarta.setChecked(false);

                }else{
                    swCarta.setChecked(true);
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ubicacion= "";
                nombre="";
                txtRuta.setText("");
            }
        });
        btnMandar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!conectado.verificaConexion(getActivity().getApplicationContext())) {
                    conectado.dialgo(getActivity());


                }else {
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
                    } else {


                        new Thread() {
                            public void run() {

                                try {


                                    SyncHttpClient client = new SyncHttpClient();
                                    RequestParams params = new RequestParams();
                                    params.put("numero", "1");
                                    params.put("documento", new File(ubicacion));


                                    client.post("http://hurryapp.devworms.com/subir.php", params, new TextHttpResponseHandler() {

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

                            }

                        }.start();
                        new Thread() {
                            @Override
                            public void run() {
                                //while ( respsuesta!="") {
                                int contador = 1;

                                Message msg = new Message();
                                msg.obj = contador;
                                if (respsuesta == "")
                                    puente.sendMessage(msg);

                            }
                            // }
                        }.start();
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

            Bundle bundle = this.getArguments();

            ubicacion= bundle.getString("ubicacion");
            nombre= bundle.getString("nombrearch");

            //ubicacion = getArguments().getString("ubicacion");
            //nombre= getArguments().getString("nombrearch");
            // Intent i=getIntent();
            //  ubicacion=i.getExtras().getString("ubicacion");
        }catch (Exception e){

        }
        if(nombre!= null )
            txtRuta.setText(nombre);
        return view;
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
