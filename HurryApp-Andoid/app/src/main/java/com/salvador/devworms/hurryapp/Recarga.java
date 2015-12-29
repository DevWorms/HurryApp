package com.salvador.devworms.hurryapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by salvador on 22/12/2015.
 */
public class Recarga extends Fragment {
    Button btnpaypal;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recarga, container, false);
        btnpaypal=(Button)view.findViewById(R.id.btn_paypal);
        btnpaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction()
                        .replace(R.id.actividad, new Cantidad_Recarga()).commit();
            }

        });

        return view;



    }
}
