package com.salvador.devworms.hurryapp;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by salvador on 15/01/2016.
 */
public class Historial extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_historial, container, false);



        String[] cosasPorHacer = new String[] { "Folio:01290201",
                "Folio:01340201","Folio:01299087"};


        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, cosasPorHacer);

        ListView miLista = (ListView) view.findViewById(R.id.list);


        miLista.setAdapter(arrayAdapter);

        miLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(getActivity());
                //Aqui haces que tu layout se muestre como dialog
                dialog.setContentView(R.layout.fragment_historial_info);
                dialog.setTitle("DATOS DE FOLIO");

                Button dismissButton = (Button) dialog.findViewById(R.id.btn_ok);
                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });

        return view;
    }
}
