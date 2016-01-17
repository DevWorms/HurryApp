package com.salvador.devworms.hurryapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by salvador on 03/01/2016.
 */
public class Registro extends AppCompatActivity{
    int year_x,mes_x,dia_x;
    TextView fecha;
    Button btnfecha;
    static final int DIALOG_ID=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro);

        fecha = (TextView) findViewById(R.id.txt_fecha);
        btnfecha = (Button) findViewById(R.id.btn_fecha);
        final Calendar c = Calendar.getInstance();
        year_x = c.get(Calendar.YEAR);
        mes_x = c.get(Calendar.MONTH);
        dia_x = c.get(Calendar.DAY_OF_MONTH);


        btnfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              showDialog(DIALOG_ID);


            }
        });


    }
    DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datePicker, int i, int j, int k) {

            year_x = i;
            mes_x = j;
            dia_x = k;
            fecha.setText(dia_x+"/"+mes_x+"/"+year_x);

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ID:
                return new DatePickerDialog(this, myDateSetListener, year_x, mes_x,
                        dia_x);
        }
        return null;
    }


}
