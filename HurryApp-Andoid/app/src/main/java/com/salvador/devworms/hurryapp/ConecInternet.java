package com.salvador.devworms.hurryapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by salvador on 13/01/2016.
 */
public class ConecInternet {

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }
    public void dialgo(final Context ctx){
        new AlertDialog.Builder(ctx)
                .setTitle("LO LAMENTAMOS")
                .setMessage("ESTAMOS TENIENDO PROBLEMAS CON SU CONEXION A INTERNET, COMPRUEBE SU CONEXION")
                .setPositiveButton("VOLVER A INTENTAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (!verificaConexion(ctx)) {
                            dialgo(ctx);

                        }                  }
                })
                .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })

                .show();
    }
}
