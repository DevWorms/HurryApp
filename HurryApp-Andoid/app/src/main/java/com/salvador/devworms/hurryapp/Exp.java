package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by salvador on 11/12/2015.
 */
public class Exp extends Fragment {
    private List<String> listaNombresArchivos;
    private List<String>listaRutasArchivos;
    private ArrayAdapter<String> adaptador;
    private String directprioRaiz;
    private TextView carpetaActual;
    public String ubicacion;
    private ListView list;
    public String nombrearchi;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_buscar, container, false);


        carpetaActual=(TextView)view.findViewById(R.id.rutaAcual);
        list=(ListView)view.findViewById(R.id.list);
        directprioRaiz= Environment.getExternalStorageDirectory().getPath();

        verArchivosDirectorio(directprioRaiz);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File archivo=new File(listaRutasArchivos.get(position));
                if(archivo.isFile()){

                    nombrearchi=archivo.getName();
                    ubicacion=archivo.getPath();
                    String newName= nombrearchi.replace(".",",");

                  String []nomArch = newName.split(",");
                    Log.d("TipiArch : ", "> "+ nombrearchi);


                   if(nomArch[1].equals("pdf")|| nomArch[1].equals("doc")|| nomArch[1].equals("docx")) {

                        Compra fragment = new Compra();

                        Bundle parametro = new Bundle();


                  /*  fragment.setArguments(parametro);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.actividad, new Compra()).commit();*/


                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                       ((Application) getActivity().getApplication()).setArchivo(nombrearchi);
                       ((Application) getActivity().getApplication()).setUbicacion(ubicacion);

                        parametro.putString("nombrearch", nombrearchi);
                        parametro.putString("ubicacion", ubicacion);
                        fragment.setArguments(parametro);

                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.actividad, fragment);
                        fragmentTransaction.commit();

                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Solo puede elegir Archivos Pdf, Doc o Docx",
                                Toast.LENGTH_SHORT).show();
                    }


           /* Intent i= new Intent(this, MenuActivity.class);
            i.putExtra("ubicacion",ubicacion);
            i.putExtra("nombre", nombrearchi);
            i.putExtra("pantalla", "busca");

            startActivity(i);
            finish();*/

                }else{

                    verArchivosDirectorio(listaRutasArchivos.get(position));
                }

            }
        });
        return view;
    }
    public interface OnArticleSelectedListener {
        public void onArticleSelected();
    }
    public void verArchivosDirectorio(String rutaDrectorio){
        carpetaActual.setText("Esta en: " + rutaDrectorio);
        carpetaActual.setTextSize(20f);
        listaNombresArchivos= new ArrayList<String>();
        listaRutasArchivos= new ArrayList<String>();
        File directorioActual=new File(rutaDrectorio);
        File[] listaArchivos= directorioActual.listFiles();
        int x=0;
        if(!rutaDrectorio.equals(directprioRaiz)){
            listaNombresArchivos.add("../");
            listaRutasArchivos.add(directorioActual.getParent());
            x=1;
        }
        for(File archivo : listaArchivos){
            listaRutasArchivos.add(archivo.getPath());
        }
        Collections.sort(listaRutasArchivos, String.CASE_INSENSITIVE_ORDER);

        for(int i=x;i<listaRutasArchivos.size();i++){
            File archivo=new File(listaRutasArchivos.get(i));
            if(archivo.isFile()){
                listaNombresArchivos.add(archivo.getName());
            }else {
                listaNombresArchivos.add("/"+archivo.getName());
            }

        }
        if(listaArchivos.length<1){
            listaNombresArchivos.add("No hay archivos");
            listaRutasArchivos.add(rutaDrectorio);
        }
        adaptador= new ArrayAdapter<String>(getActivity(),R.layout.activity_buscar,R.id.empty,listaNombresArchivos);
       list.setAdapter(adaptador);
    }


    public void onListItemClick(ListView l,View v,int position, long id){
        File archivo=new File(listaRutasArchivos.get(position));
        if(archivo.isFile()){

            nombrearchi=archivo.getName();
            ubicacion=archivo.getPath();

            Compra fragment = new Compra();

            Bundle parametro = new Bundle();

            parametro.putString("nombrearch",nombrearchi);
            parametro.putString("ubicacion", ubicacion);

            fragment.setArguments(parametro);
            getFragmentManager().beginTransaction()
                    .replace(R.id.actividad, new Compra()).commit();



           /* Intent i= new Intent(this, MenuActivity.class);
            i.putExtra("ubicacion",ubicacion);
            i.putExtra("nombre", nombrearchi);
            i.putExtra("pantalla", "busca");

            startActivity(i);
            finish();*/

        }else{

            verArchivosDirectorio(listaRutasArchivos.get(position));
        }


    }


}
