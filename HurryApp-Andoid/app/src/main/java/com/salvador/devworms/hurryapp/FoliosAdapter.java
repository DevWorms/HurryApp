package com.salvador.devworms.hurryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by salvador on 15/04/2016.
 */
public class FoliosAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] nombres;
    String[] folios;
    String[] estatus;

    LayoutInflater inflater;

    public FoliosAdapter(Context context, String[] nombres, String[] folios, String[] estatus ) {
        this.context = context;
        this.nombres = nombres;
        this.estatus = estatus;
        this.folios = folios;

    }

    @Override
    public int getCount() {
        return folios.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables

        TextView txtNombre;
        TextView txtFolio;
        TextView txtEstatus;



        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_folios, parent, false);

        // Locate the TextViews in listview_item.xml

        txtNombre= (TextView) itemView.findViewById(R.id.txtNomArch);
        txtFolio= (TextView) itemView.findViewById(R.id.txtFolio);
        txtEstatus= (TextView) itemView.findViewById(R.id.txtEsta);




        // Capture position and set to the TextViews

        txtNombre.setText(nombres[position]);
        txtFolio.setText(folios[position]);
        txtEstatus.setText(estatus[position]);


        return itemView;
    }
}