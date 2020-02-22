package com.example.verpelis;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import static java.lang.Integer.parseInt;

public class Portada extends AppCompatActivity {

    Funciones funciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);
        funciones = new Funciones(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        Visita();

        Update_app();
    }


    private void Update_app() {

        String respuesta = funciones.Conexion("", getResources().getString(R.string.Url_updates));

        try {
            Log.i("Log_Visitas", "" + "R: " + respuesta);
            JSONObject object = new JSONObject(respuesta);
            Log.i("Log_Visitas", "Obj-version: " + object.getString("version"));
            Log.i("Log_Visitas", "Obj-nombre: " + object.getString("nombre"));

            Log.i("Log_Visitas", "Obj-version-apk: " + parseInt(getApplicationContext().getResources().getString(R.string.app_version)));

            if (parseInt(object.getString("version")) > parseInt(getApplicationContext().getResources().getString(R.string.app_version))) {
                Intent intent = new Intent(getApplicationContext(), Update.class);
                intent.putExtra("URL", object.getString("url"));
                intent.putExtra("nombre", object.getString("nombre"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else {
                Empezar();
            }
        } catch (Exception e) {
            Log.i("Log_Visitas", e.getMessage());
            Empezar();
        }

    }

    public void Visita() {

        funciones.Conexion("{\"imei\":\"" + getImei() + "\"}", getResources().getString(R.string.Url_visitas));
    }

    private String getImei() {
        //StringBuilder builder = new StringBuilder();
        //.append()
        String imei = "";
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            imei = tm.getDeviceId(); // Obtiene el imei  or  "352319065579474";
        }catch (Exception e){}

        return imei;
        //TelephonyManager mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //mngr.getDeviceId();
    }

    void Empezar(){
        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(Portada.this, Catalogo.class);
                startActivity(intent);
                finish();
            };
        }, 1000);
    }
}
