package com.salvador.devworms.hurryapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by salvador on 28/12/2015.
 */
public class Cantidad_Recarga extends Fragment {
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;


    // private static final String CONFIG_CLIENT_ID = "client id of sandbox";
    private static final String CONFIG_CLIENT_ID = "ARz4NRRhyp7aszqRG1kj_1A2syw_Jp8nT8JwRnSpQqgD7c_bsMd6__bgXfrVsl9g7io9xCjy8kSqxWm1";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private ProgressDialog pDialog;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
                    // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Hipster Store")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));

    PayPalPayment thingToBuy;
    Button btn_re30;
    Button btn_re50;
    Button btn_re100;
    String cantidad;
    ConecInternet conectado=new ConecInternet();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cantidad_recarga, container, false);
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
       getActivity().startService(intent);

        btn_re30=(Button)view.findViewById(R.id.btn_trienta);
        btn_re30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!conectado.verificaConexion(getActivity().getApplicationContext())) {
                    conectado.dialgo(getActivity());

                }else {
                    thingToBuy = new PayPalPayment(new BigDecimal("30"), "MXN",
                            "Recarga $30", PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(getActivity(),
                            PaymentActivity.class);
                    cantidad = "30";

                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
            }

        });
        btn_re50=(Button)view.findViewById(R.id.btn_cincu);
        btn_re50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!conectado.verificaConexion(getActivity().getApplicationContext())) {
                    conectado.dialgo(getActivity());

                } else {
                    thingToBuy = new PayPalPayment(new BigDecimal("50"), "MXN",
                            "Recarga $50", PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(getActivity(),
                            PaymentActivity.class);
                    cantidad = "50";
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
            }
        });
        btn_re100=(Button)view.findViewById(R.id.btn_cien);
        btn_re100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!conectado.verificaConexion(getActivity().getApplicationContext())) {
                    conectado.dialgo(getActivity());

                } else {
                    thingToBuy = new PayPalPayment(new BigDecimal("100"), "MXN",
                            "Recarga $100", PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(getActivity(),
                            PaymentActivity.class);
                    cantidad = "100";
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
            }
        });
        return view;



    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));
                        new getSaldAT().execute();




                       // TextView txt =(TextView)getActivity().findViewById(R.id.saldo);
                       // txt.setText(cantidad);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getActivity(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    class getSaldAT extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cargando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Albums JSON
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            //add your data


            JSONParser jsp= new JSONParser();
            SharedPreferences sp =getActivity().getSharedPreferences("prefe", getActivity().MODE_PRIVATE);
            String Apikey = sp.getString("APIkey","");
            String body="{\n    \"Apikey\" : \""+Apikey+"\",\n    \"Saldo\" : \""+cantidad+"\"\n}";
            String respuesta= jsp.makeHttpRequest("http://hurryprint.devworms.com/api/usuarios/recarga","POST",body,"");
            //  Log.d("Tiendas : ", "> " + respuesta);
            if(respuesta!="error"){
                try {
                    JSONObject json = new JSONObject(respuesta);
                    Log.i("Recarga", respuesta);






                } catch (JSONException e){
                    e.printStackTrace();
                }


            }else {}



            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            pDialog.dismiss();
            // updating UI from Background Thread
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), "Recarga realizda con exito",
                            Toast.LENGTH_LONG).show();




                }
            });

        }
    }


    @Override
    public void onDestroy() {
        // Stop service when done
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }
}
