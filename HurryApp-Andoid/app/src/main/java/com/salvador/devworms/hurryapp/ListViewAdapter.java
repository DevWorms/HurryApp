package com.salvador.devworms.hurryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by salvador on 14/04/2016.
 */
public class ListViewAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] tiendas;
    String[] dispo;
    String[] color;
    String[] blaneg;
    LayoutInflater inflater;

    public ListViewAdapter(Context context, String[] tiendas, String[] dispo, String[] color, String[] blaneg ) {
        this.context = context;
        this.tiendas = tiendas;
        this.dispo = dispo;
        this.color = color;
        this.blaneg = blaneg;
    }

    @Override
    public int getCount() {
        return tiendas.length;
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
        ImageView imgDis;
        ImageView imgBN;
        ImageView imgColor;
        TextView txtTienda;



        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_tiendas, parent, false);

        // Locate the TextViews in listview_item.xml
         imgDis = (ImageView) itemView.findViewById(R.id.imgTiDispo);
         imgBN=(ImageView) itemView.findViewById(R.id.imgImpBN);
         imgColor= (ImageView) itemView.findViewById(R.id.imgImpColor);
         txtTienda= (TextView) itemView.findViewById(R.id.txtNomTienda);
         if("0".equals(dispo[position])){
            imgDis.setVisibility(itemView.INVISIBLE);
        }
        if("0".equals(color[position])){
            imgColor.setVisibility(itemView.INVISIBLE);
        }
        if("0".equals( blaneg[position])){
            imgBN.setVisibility(itemView.INVISIBLE);
        }


        // Capture position and set to the TextViews

        txtTienda.setText(tiendas[position]);


        return itemView;
    }
}