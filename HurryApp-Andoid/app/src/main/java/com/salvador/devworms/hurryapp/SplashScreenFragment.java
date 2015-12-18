package com.salvador.devworms.hurryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by salvador on 01/12/2015.
 */
public class SplashScreenFragment extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_splash_screen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    //Nos indica siu mostramos el login o si mostramos el tutorial
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreenFragment.this);
                    Boolean slider = preferences.getBoolean("MostrarSlider", true );
                    if(slider)
                    {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("MostrarSlider",false);
                        editor.apply();

                        Intent intent = new Intent(SplashScreenFragment.this,Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {

                        Intent intent = new Intent(SplashScreenFragment.this,Login.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        };
        timerThread.start();
    }

}
