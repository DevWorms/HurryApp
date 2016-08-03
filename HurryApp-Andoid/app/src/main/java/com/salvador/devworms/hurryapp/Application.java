package com.salvador.devworms.hurryapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Gowtham Chandrasekar on 29-07-2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        gettingHashKey();
        String ubi;
    }
    private String[] folderArray= new String[10];

    public String[] getFolderArray() {
        return folderArray;
    }

    public void setFolderArray(String Folder,int i) {
        this.folderArray[i] = Folder;
    }
    private String[] engargoladoArray= new String[10];

    public String[] getEngarArray() {
        return engargoladoArray;
    }

    public void setEngarArray(String Engar,int i) {
        this.engargoladoArray[i] = Engar;
    }
    private String Archivo;

    public String getArchivo() {
        return Archivo;
    }

    public void setArchivo(String archivoName) {
        this.Archivo = archivoName;
    }
    private String ubicacion;

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubi) {
        this.ubicacion = ubi;
    }

    private int costoEngargolado=0;

    public int getCostoEngargolado() {
        return costoEngargolado;
    }

    public void setCostoEngargolado(int costo) {
        this.costoEngargolado = costo;
    }

    private int costoFolder=0;

    public int getCostoFolder() {
        return costoFolder;
    }

    public void setCostoFolder(int costo) {
        this.costoFolder = costo;
    }

    private String numeroHojas="0";

    public String getnumeroHojas() {
        return numeroHojas;
    }

    public void setnumeroHojas(String numhoj) {
        this.numeroHojas = numhoj;
    }

    private String numeroJuegos="0";

    public String getnumeroJuegos() {
        return numeroJuegos;
    }

    public void setnumeroJuegos(String numjue) {
        this.numeroJuegos = numjue;
    }

    private String hBlancoNegro;

    public String gethBlancoNegro() {
        return hBlancoNegro;
    }

    public void sethBlancoNegro(String bn) {
        this.hBlancoNegro = bn;
    }

    private String hColor;

    public String gethColor() {
        return hColor;
    }

    public void sethColor(String color) {
        this.hColor = color;
    }

    private String navFragment;

    public String getnavFragment() {
        return navFragment;
    }

    public void setnavFragment(String actualFrag) {
        this.navFragment = actualFrag;
    }


    public void gettingHashKey(){

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.salvador.devworms.hurryapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
}
