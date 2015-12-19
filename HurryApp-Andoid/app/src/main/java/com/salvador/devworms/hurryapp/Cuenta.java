package com.salvador.devworms.hurryapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by salvador on 02/12/2015.
 */
public class Cuenta extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cuenta, container, false);
        Button btn  = (Button)view.findViewById(R.id.btnMetPag);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        Button btnImp = (Button)view.findViewById(R.id.btnImp);
        btnImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    return view;
    }

       /* super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_cuenta);*/




}