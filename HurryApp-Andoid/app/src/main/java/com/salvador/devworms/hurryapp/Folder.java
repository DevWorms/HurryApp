package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

/**
 * Created by salva on 13/05/2016.
 */
public class Folder extends Fragment {
    TextView txtFBla,txtFAz,txtFRed,txtFVer,txtFRosa,txtFMor,txtFGin,txtFAzf,txtFNeg;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_folder, container, false);
        txtFBla=(TextView)view.findViewById(R.id.txtFolBlan);
        txtFAz=(TextView)view.findViewById(R.id.txtFolAzul);
        txtFRed=(TextView)view.findViewById(R.id.txtFolRed);
        txtFVer=(TextView)view.findViewById(R.id.txtFolVer);
        txtFRosa=(TextView)view.findViewById(R.id.txtFolRosa);
        txtFNeg=(TextView)view.findViewById(R.id.txtFolNeg);
        txtFAzf=(TextView)view.findViewById(R.id.txtFolAzF);
        txtFGin=(TextView)view.findViewById(R.id.txtFolGin);
        txtFMor=(TextView)view.findViewById(R.id.txtFolMor);


        Button btnMasFbl=(Button)view.findViewById(R.id.btnMasBlan);
        btnMasFbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "bla");
                txtFBla.setText(String.valueOf(sumarFolder(txtFBla.getText().toString())));
            }

        });

        Button bntMenFblr=(Button)view.findViewById(R.id.btnMenBlan);
        bntMenFblr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "bla");
                txtFBla.setText(String.valueOf(restarFolder(txtFBla.getText().toString())));
            }

        });

        Button btnMasFaz=(Button)view.findViewById(R.id.btnMasAzul);
        btnMasFaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azul");
                txtFAz.setText(String.valueOf(sumarFolder(txtFAz.getText().toString())));
            }

        });

        Button bntMenFaz=(Button)view.findViewById(R.id.btnMenAzul);
        bntMenFaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azul");
                txtFAz.setText(String.valueOf(restarFolder(txtFAz.getText().toString())));
            }

        });
        Button btnMasFred=(Button)view.findViewById(R.id.btnMasRed);
        btnMasFred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "red");
                txtFRed.setText(String.valueOf(sumarFolder(txtFRed.getText().toString())));
            }

        });

        Button bntMenFred=(Button)view.findViewById(R.id.btnMenRed);
        bntMenFred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "red");
                txtFRed.setText(String.valueOf(restarFolder(txtFRed.getText().toString())));
            }

        });
        Button btnMasFved=(Button)view.findViewById(R.id.btnMasVer);
        btnMasFved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "ver");
                txtFVer.setText(String.valueOf(sumarFolder(txtFVer.getText().toString())));
            }

        });

        Button bntMenFved=(Button)view.findViewById(R.id.btnMenVer);
        bntMenFved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "ver");
                txtFVer.setText(String.valueOf(restarFolder(txtFVer.getText().toString())));
            }

        });
        Button btnMasFrosa=(Button)view.findViewById(R.id.btnMasRosa);
        btnMasFrosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "ama");
                txtFRosa.setText(String.valueOf(sumarFolder(txtFRosa.getText().toString())));
            }

        });

        Button bntMenFrosa=(Button)view.findViewById(R.id.btnMenRosa);
        bntMenFrosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azul");
                txtFRosa.setText(String.valueOf(restarFolder(txtFRosa.getText().toString())));
            }

        });

        Button btnMasFneg=(Button)view.findViewById(R.id.btnMasNeg);
        btnMasFneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "neg");
                txtFNeg.setText(String.valueOf(sumarFolder(txtFNeg.getText().toString())));
            }

        });

        Button bntMenFneg=(Button)view.findViewById(R.id.btnMenNeg);
        bntMenFneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "neg");
                txtFNeg.setText(String.valueOf(restarFolder(txtFNeg.getText().toString())));
            }

        });

        Button btnMasFazf=(Button)view.findViewById(R.id.btnMasAzF);
        btnMasFazf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azulf");
                txtFAzf.setText(String.valueOf(sumarFolder(txtFAzf.getText().toString())));
            }

        });

        Button bntMenFazf=(Button)view.findViewById(R.id.btnMenAzF);
        bntMenFazf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "azulf");
                txtFAzf.setText(String.valueOf(restarFolder(txtFAzf.getText().toString())));
            }

        });
        Button btnMasFgin=(Button)view.findViewById(R.id.btnMasGin);
        btnMasFgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "gin");
                txtFGin.setText(String.valueOf(sumarFolder(txtFGin.getText().toString())));
            }

        });

        Button bntMenFgin=(Button)view.findViewById(R.id.btnMenGin);
        bntMenFgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "gin");
                txtFGin.setText(String.valueOf(restarFolder(txtFGin.getText().toString())));
            }

        });

        Button btnMasFmor=(Button)view.findViewById(R.id.btnMasMor);
        btnMasFmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "mor");
                txtFMor.setText(String.valueOf(sumarFolder(txtFMor.getText().toString())));
            }

        });

        Button bntMenFmor=(Button)view.findViewById(R.id.btnMenMor);
        bntMenFmor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bonton : ", "> " + "mor");
                txtFMor.setText(String.valueOf(restarFolder(txtFMor.getText().toString())));
            }

        });




        Button btnAcepFol=(Button)view.findViewById(R.id.btnAceptFolder);
        btnAcepFol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((Application) getActivity().getApplication()).setFolderArray(txtFBla.getText().toString(),0);
                ((Application) getActivity().getApplication()).setFolderArray(txtFAz.getText().toString(),1);
                ((Application) getActivity().getApplication()).setFolderArray(txtFRed.getText().toString(),2);
                ((Application) getActivity().getApplication()).setFolderArray(txtFVer.getText().toString(),3);
                ((Application) getActivity().getApplication()).setFolderArray(txtFRosa.getText().toString(),4);
                int folderClaro= Integer.parseInt(txtFBla.getText().toString())+ Integer.parseInt(txtFAz.getText().toString())+ Integer.parseInt(txtFRed.getText().toString())+ Integer.parseInt(txtFVer.getText().toString())+ Integer.parseInt(txtFVer.getText().toString())+ Integer.parseInt(txtFRosa.getText().toString());
                folderClaro= folderClaro*3;
                ((Application) getActivity().getApplication()).setFolderArray(txtFNeg.getText().toString(),5);
                ((Application) getActivity().getApplication()).setFolderArray(txtFAzf.getText().toString(),6);
                ((Application) getActivity().getApplication()).setFolderArray(txtFGin.getText().toString(),7);
                ((Application) getActivity().getApplication()).setFolderArray(txtFMor.getText().toString(),8);
                int folderIntenso=Integer.parseInt(txtFNeg.getText().toString())+Integer.parseInt(txtFAzf.getText().toString())+Integer.parseInt(txtFGin.getText().toString())+Integer.parseInt(txtFMor.getText().toString());
                folderIntenso=folderIntenso*5;

                ((Application) getActivity().getApplication()).setCostoFolder(folderIntenso+folderClaro);
               getFragmentManager().beginTransaction()
                        .replace(R.id.actividad, new Compra()).commit();

            }

        });




        return view;
    }
    public int sumarFolder(String txtFolder){
       int suma= Integer.parseInt(txtFolder);
        suma= suma+1;
        return suma;
    }
    public  int restarFolder(String resFolder){
        int res= Integer.parseInt(resFolder);
        if(res>0) {
            res = res - 1;
        }
        return res;
    }
}
