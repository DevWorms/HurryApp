package com.salvador.devworms.hurryapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    String respsuesta;
    Button btnBuscar;
    Button btnMandar;
    TextView txtRuta;
    TextView txtContenido;
    String ubicacion;
    String nombre;
    int fir=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_compra, container, false);

        btnBuscar=(Button)view.findViewById(R.id.btnBuscar);
        txtRuta=(TextView)view.findViewById(R.id.ruta);
        btnMandar=(Button)view.findViewById(R.id.mandar);
        btnMandar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(ubicacion==""||ubicacion==null){
                    Toast.makeText(getActivity().getApplicationContext(), "Debe de seleccionar un archivo",
                            Toast.LENGTH_SHORT).show();

                }else{
                    new Thread()
                    {
                        public void run()
                        {
                            try
                            {


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
                                        respsuesta=responseString;
                                        Message mesa=new Message();
                                        vistaHandler.sendMessage(mesa);
                                    }


                                });


                            }
                            catch (Exception ex)
                            {
                                Message mesa=new Message();
                                respsuesta=ex.toString();
                                vistaHandler.sendMessage(mesa);
                            }
                        }

                    }.start();
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
            Bundle args = getActivity().getIntent().getExtras();
            ubicacion= args.getString("ubicacion");
            nombre= args.getString("nombre");
          // Intent i=getIntent();
          //  ubicacion=i.getExtras().getString("ubicacion");
        }catch (Exception e){

        }
        if(ubicacion!= null )
            txtRuta.setText(nombre);

        return view;
    }
    Handler vistaHandler = new Handler() {
        public void handleMessage(Message msg) {
            Toast.makeText(getActivity().getApplicationContext(), respsuesta,
                    Toast.LENGTH_SHORT).show();
            ubicacion="";
            nombre="";
            txtRuta.setText(nombre);


        }

    };
    public void irExplorador(){
        Intent myIntent = new Intent(getActivity(), Exp.class);
        getActivity().startActivity(myIntent);
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
