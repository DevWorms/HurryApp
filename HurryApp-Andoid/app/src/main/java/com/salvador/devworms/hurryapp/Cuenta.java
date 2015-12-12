package com.salvador.devworms.hurryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by salvador on 02/12/2015.
 */
public class Cuenta extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_cuenta);
        Button btn = (Button) findViewById(R.id.btnMetPag);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Cuenta.this, MetPago.class);
                startActivity(intent);

            }
        });
        Button btnImp = (Button) findViewById(R.id.btnImp);
        btnImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Cuenta.this, Compra.class);
                startActivity(intent);

            }
        });

    }
}