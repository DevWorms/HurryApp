package com.salvador.devworms.hurryapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by salvador on 01/12/2015.
 */
public class Login extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login);
       Button btn=(Button)findViewById(R.id.boton1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login.this, MenuActivity.class);
                startActivity(intent);

            }
        });



    }



}
