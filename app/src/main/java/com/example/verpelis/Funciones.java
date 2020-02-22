package com.example.verpelis;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Funciones {


    Context context;



    //Variables que usa para generar las preguntas de la encuesta con un id_respuestas para todas
    String id_hoja="",conf_respuestas="";
    ArrayList<String> respuestas_encuestas=new ArrayList<>();

    public Funciones(Context context) {
        this.context=context;

    }

    public String Conexion(String data, String url) {

        String result = "";


        try {

            //v.vibrate(50);

            Log.i("Conexion", "Enviando: "+url+"    "+data);

            //Create a URL object holding our url
            URL myUrl = new URL(url);
            //Create a connection
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            //Set methods and timeouts
            connection.setRequestMethod("POST");
            connection.setReadTimeout(1500);
            connection.setConnectTimeout(1500);
            //connection.addRequestProperty("pr","33");

            //Connect to our url
            connection.connect();


            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            //Log.i("datos",getPostDataString(postDataParams));
            writer.write("data="+data);

            writer.flush();
            writer.close();
            os.close();


            //Create a new InputStreamReader

            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());

            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            //stringBuilder.append(data);

            //Check if the line we are reading is not null
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
            Log.i("Conexion","Recibiendo: "+result);
            return result;


        } catch (Exception ee) {
            Log.i("Conexion", "Error_conexion: "+ ee.getMessage());
            return  "";
        }



    }



    public String decode64(String str){
        byte[] data = Base64.decode(str, Base64.DEFAULT);
        try {
            str = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String encode64(String toString) {
        byte[] data;
        try {
            data = toString.getBytes("UTF-8");
            toString = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return toString;
    }



    public void Select_vibrar(Context context){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milisegundos
        long[] pattern = { 0, 70};
        v.vibrate(pattern,-1);
    }

    public void Vibrar(int milli){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milisegundos
        long[] pattern = { 0, milli};
        v.vibrate(pattern,-1);
    }





    public int BateriaNivel(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        return level;

    }



    public String GetDate(){
        return (String) DateFormat.format("yyyy-MM-dd", new Date());
    }
    public String GetTime(){
        return (String) DateFormat.format("HH:mm:ss", new Date());
    }

    public String GetDateTime(){
        return (String) DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date());
    }









    public String URL_Dominio(){
        String URL="",wifiname=getWifiName();

        URL=context.getString(R.string.app_dominio);
        Log.i("URLLLLL","web: "+wifiname);


        return URL;
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }





    public String GetUIID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }





    ///Preguntas, aqui se carga la vista de las preguntas

    public void Texto(String txt,LinearLayout contenedor){
        LinearLayout.LayoutParams parameter = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        parameter.setMargins(0,20,0,0); // left, top, right, bottom

        TextView texto = null;
        texto =new TextView(context);
        texto.setLayoutParams(parameter);
        texto.setText(txt);

        contenedor.addView(texto);
    }


    private boolean VarificarArregloCompleto(ArrayList<String> respuestas){
        int completo=0;
        for (int i = 0;i < respuestas.size();i++){
            if(respuestas.get(i).length()==0){
                completo++;
            }
        }
        if(completo > 0){
            return false;
        }else{
            return true;
        }
    }


    void Iniciar(Context cont){
        context=cont;

        Intent mainIntent = new Intent(context, Catalogo.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);

    }


    public String getWifiName() {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }

    public int AnchoScreen(int porciento){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int ancho=Math.round(metrics.widthPixels*((float)porciento/100));
        Log.i("medidas", ancho+"");
        return ancho;
    }

    public int AltoScreen(int porciento){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int alto=Math.round(metrics.heightPixels*((float)porciento/100));
        Log.i("medidas", alto+"");
        return alto;
    }

    public void Dialogo(String msn){
        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(context);
        builder1.setMessage(msn);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        /*builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}
