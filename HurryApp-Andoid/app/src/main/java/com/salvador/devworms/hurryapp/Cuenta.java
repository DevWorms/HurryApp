package com.salvador.devworms.hurryapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

/**
 * Created by salvador on 02/12/2015.
 */
public class Cuenta extends Fragment {
    TextView name;
    String nom;
    String fot;
    Button logout;
    ProfilePictureView fotoper;
    MenuItem agreTar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_cuenta, container, false);
        name=(TextView)view.findViewById(R.id.cuentaNombre);
        logout=(Button)view.findViewById(R.id.logout);
        fotoper=(ProfilePictureView) view.findViewById(R.id.profilePicture);
        try {
            MenuActivity menua =(MenuActivity)getActivity();
            nom =menua.inifbnombre;
            fot = menua.inifbfoto;
            if (nom != null && nom != "")
                name.setText(nom);
            if (fot != null && fot != "")
                fotoper.setProfileId(fot);
        }catch (Exception e){}
       /*Bundle args = this.getActivity().getExtras();
         name= args.getString("nombre");
         fotoper= args.getString("foto");
*/      logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logOut();
                Intent salida = new Intent(Intent.ACTION_MAIN); //Llamando a la activity principal
                getActivity().finish(); // La cerramos.


            }

        });

    return view;
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
       getActivity().getMenuInflater().inflate(R.menu.menu, menu);
       // agreTar = menu.add("agregar tarjeta").setIcon(R.drawable.icn_tar);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ade:
                getFragmentManager().beginTransaction()
                        .replace(R.id.actividad, new MetPago()).commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
       /* super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_cuenta);*/




}