package com.salvador.devworms.hurryapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by salvador on 21/12/2015.
 */
public class Tiendas extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tiendas, container, false);

        // A. Creamos el arreglo de Strings para llenar la lista
        String[] cosasPorHacer = new String[] { "Eart edificio de graduados",
                "Eart calle canela","Eart edificio pesados"};

        // B. Creamos un nuevo ArrayAdapter con nuestra lista de cosasPorHacer
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, cosasPorHacer);

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
        });

        return rootView;
    }

}
