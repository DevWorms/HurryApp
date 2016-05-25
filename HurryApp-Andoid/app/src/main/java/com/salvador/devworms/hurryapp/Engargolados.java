package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by salva on 24/05/2016.
 */
public class Engargolados extends Fragment{
    TextView txtFBla,txtFAz,txtFRed,txtFVer,txtFAma,txtFMor,txtFGri,txtFGin,txtFAzf,txtFNeg;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_engarglado, container, false);
        txtFBla=(TextView)view.findViewById(R.id.txtFolBlan);
        txtFAz=(TextView)view.findViewById(R.id.txtFolAzul);
        txtFRed=(TextView)view.findViewById(R.id.txtFolRed);
        txtFVer=(TextView)view.findViewById(R.id.txtFolVer);
        txtFAma=(TextView)view.findViewById(R.id.txtFolAma);
        txtFNeg=(TextView)view.findViewById(R.id.txtFolNeg);
        txtFAzf=(TextView)view.findViewById(R.id.txtFolAzF);
        txtFGin=(TextView)view.findViewById(R.id.txtFolGin);
        txtFGri=(TextView)view.findViewById(R.id.txtFolGri);
        txtFMor=(TextView)view.findViewById(R.id.txtFolMor);

        Button btnMasFbl=(Button)view.findViewById(R.id.btnMasBlan);
        btnMasFbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "bla");
                txtFBla.setText(String.valueOf(sumarEnga(txtFBla.getText().toString())));
            }

        });

        Button bntMenFblr=(Button)view.findViewById(R.id.btnMenBlan);
        bntMenFblr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "bla");
                txtFBla.setText(String.valueOf(restarEnga(txtFBla.getText().toString())));
            }

        });

        Button btnMasFaz=(Button)view.findViewById(R.id.btnMasAzul);
        btnMasFaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azul");
                txtFAz.setText(String.valueOf(sumarEnga(txtFAz.getText().toString())));
            }

        });

        Button bntMenFaz=(Button)view.findViewById(R.id.btnMenAzul);
        bntMenFaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azul");
                txtFAz.setText(String.valueOf(restarEnga(txtFAz.getText().toString())));
            }

        });
        Button btnMasFred=(Button)view.findViewById(R.id.btnMasRed);
        btnMasFred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "red");
                txtFRed.setText(String.valueOf(sumarEnga(txtFRed.getText().toString())));
            }

        });

        Button bntMenFred=(Button)view.findViewById(R.id.btnMenRed);
        bntMenFred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "red");
                txtFRed.setText(String.valueOf(restarEnga(txtFRed.getText().toString())));
            }

        });
        Button btnMasFved=(Button)view.findViewById(R.id.btnMasVer);
        btnMasFved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "ver");
                txtFVer.setText(String.valueOf(sumarEnga(txtFVer.getText().toString())));
            }

        });

        Button bntMenFved=(Button)view.findViewById(R.id.btnMenVer);
        bntMenFved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "ver");
                txtFVer.setText(String.valueOf(restarEnga(txtFVer.getText().toString())));
            }

        });
        Button btnMasFama=(Button)view.findViewById(R.id.btnMasAma);
        btnMasFama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "ama");
                txtFAma.setText(String.valueOf(sumarEnga(txtFAma.getText().toString())));
            }

        });

        Button bntMenFama=(Button)view.findViewById(R.id.btnMenAma);
        bntMenFama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azul");
                txtFAma.setText(String.valueOf(restarEnga(txtFAma.getText().toString())));
            }

        });

        Button btnMasFneg=(Button)view.findViewById(R.id.btnMasNeg);
        btnMasFneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "neg");
                txtFNeg.setText(String.valueOf(sumarEnga(txtFNeg.getText().toString())));
            }

        });

        Button bntMenFneg=(Button)view.findViewById(R.id.btnMenNeg);
        bntMenFneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "neg");
                txtFNeg.setText(String.valueOf(restarEnga(txtFNeg.getText().toString())));
            }

        });

        Button btnMasFazf=(Button)view.findViewById(R.id.btnMasAzF);
        btnMasFazf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azulf");
                txtFAzf.setText(String.valueOf(sumarEnga(txtFAzf.getText().toString())));
            }

        });

        Button bntMenFazf=(Button)view.findViewById(R.id.btnMenAzF);
        bntMenFazf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azulf");
                txtFAzf.setText(String.valueOf(restarEnga(txtFAzf.getText().toString())));
            }

        });
        Button btnMasFgin=(Button)view.findViewById(R.id.btnMasGin);
        btnMasFgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "gin");
                txtFGin.setText(String.valueOf(sumarEnga(txtFGin.getText().toString())));
            }

        });

        Button bntMenFgin=(Button)view.findViewById(R.id.btnMenGin);
        bntMenFgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "gin");
                txtFGin.setText(String.valueOf(restarEnga(txtFGin.getText().toString())));
            }

        });
        Button btnMasFgri=(Button)view.findViewById(R.id.btnMasGri);
        btnMasFgri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "gri");
                txtFGri.setText(String.valueOf(sumarEnga(txtFGri.getText().toString())));
            }

        });

        Button bntMenFgri=(Button)view.findViewById(R.id.btnMenGri);
        bntMenFgri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "gri");
                txtFGri.setText(String.valueOf(restarEnga(txtFGri.getText().toString())));
            }

        });
        Button btnMasFmor=(Button)view.findViewById(R.id.btnMasMor);
        btnMasFmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "mor");
                txtFMor.setText(String.valueOf(sumarEnga(txtFMor.getText().toString())));
            }

        });

        Button bntMenFmor=(Button)view.findViewById(R.id.btnMenMor);
        bntMenFmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "mor");
                txtFMor.setText(String.valueOf(restarEnga(txtFMor.getText().toString())));
            }

        });




        Button btnAcepEnga=(Button)view.findViewById(R.id.btnAceptEnga);
        btnAcepEnga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getFragmentManager().beginTransaction()
                        .replace(R.id.actividad, new Compra()).commit();

            }

        });




        return view;

    }
    public int sumarEnga(String txtFolder){
        int suma= Integer.parseInt(txtFolder);
        suma= suma+1;
        return suma;
    }
    public  int restarEnga(String resFolder){
        int res= Integer.parseInt(resFolder);
        if(res>0) {
            res = res - 1;
        }
        return res;
    }
}
