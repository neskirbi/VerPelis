package com.example.verpelis;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.verpelis.Retrofit.GetCatalogo;
import com.example.verpelis.Retrofit.ModelCatalogo;
import com.example.verpelis.Retrofit.ModelNegocio;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Catalogo extends AppCompatActivity {

    TextView data;
    AppCompatActivity este;
    private RecyclerView recyclerView;
    ListView catalogolist;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<ModelCatalogo> catalogos;
    List<ModelNegocio> negocios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        Log.i("LifeCycle","OnCreate");

        catalogolist=findViewById(R.id.catalogolist);
        este=this;


        GetNegocios();



    }
    private void GetNegocios(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.inegi.org.mx/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetCatalogo catalogo=retrofit.create(GetCatalogo.class);
        Call<List<ModelNegocio>> call= catalogo.getNegocios();
        call.enqueue(new Callback<List<ModelNegocio>>() {
            @Override
            public void onResponse(Call<List<ModelNegocio>> call, Response<List<ModelNegocio>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Catalogo.this, "Mamo el response:" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                negocios=response.body();
                String AllString="";



                Log.i("LifeCycle","aqui--->"+catalogos);

                for(ModelNegocio cat:negocios){
                    AllString+="Id: "+ cat.getId()+"\n";
                    AllString+="Nombre: "+ cat.getNombre()+"\n";
                    AllString+="Clase_actividad: "+ cat.getClase_actividad()+"\n";
                }
                Log.i("LifeCycle",AllString);

                AdaptadorNegocios adaptador1 = new AdaptadorNegocios(este);
                catalogolist.setAdapter(adaptador1);



            }

            @Override
            public void onFailure(Call<List<ModelNegocio>> call, Throwable t) {
                Toast.makeText(Catalogo.this, "Otro error"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetCatalogo(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://warezmovies.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetCatalogo catalogo=retrofit.create(GetCatalogo.class);
        Call<List<ModelCatalogo>> call= catalogo.getCatalogo();
        call.enqueue(new Callback<List<ModelCatalogo>>() {
            @Override
            public void onResponse(Call<List<ModelCatalogo>> call, Response<List<ModelCatalogo>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Catalogo.this, "Mamo el response:" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                catalogos=response.body();
                String AllString="";



                Log.i("LifeCycle","aqui--->"+catalogos);

                for(ModelCatalogo cat:catalogos){
                    AllString+="Id: "+ cat.getId_catalogo()+"\n";
                    AllString+="Nombre: "+ cat.getNombre()+"\n";
                    AllString+="Link: "+ cat.getLink()+"\n";
                }
                Log.i("LifeCycle",AllString);
                Log.i("LifeCycle","Aqui--->");
                AdaptadorCatalogo adaptador = new AdaptadorCatalogo(este);
                catalogolist.setAdapter(adaptador);



            }

            @Override
            public void onFailure(Call<List<ModelCatalogo>> call, Throwable t) {
                Toast.makeText(Catalogo.this, "Otro error"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        Log.i("LifeCycle","OnStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("LifeCycle","OnResume");

        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("LifeCycle","OnPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeCycle", "OnStop");
    }

    @Override
    protected  void onDestroy() {
        Log.i("LifeCycle", "OnDestroy");
        super.onDestroy();
    }


    class AdaptadorCatalogo extends ArrayAdapter<ModelCatalogo> {
        AppCompatActivity appCompatActivity;

        public AdaptadorCatalogo(AppCompatActivity context) {

            super(context, R.layout.card_movie, catalogos);
            appCompatActivity = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.card_movie, null);
            Log.i("LifeCycle","Adaptador: "+catalogos.get(position).getNombre());


            final ImageView imagen =item.findViewById(R.id.portada);
            Picasso.get().load(catalogos.get(position).getPortada()).into(imagen);

            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),Reproductor.class);
                    intent.putExtra("link",catalogos.get(position).getLink());
                    intent.putExtra("nombre",catalogos.get(position).getNombre());
                    intent.putExtra("portada",catalogos.get(position).getPortada());
                    startActivity(intent);

                }
            });




            return(item);
        }

    }

    class AdaptadorNegocios extends ArrayAdapter<ModelNegocio> {
        AppCompatActivity appCompatActivity;

        public AdaptadorNegocios(AppCompatActivity context) {

            super(context, R.layout.card_negocios, negocios);
            appCompatActivity = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = appCompatActivity.getLayoutInflater();

            View item = inflater.inflate(R.layout.card_negocios, null);
            Log.i("LifeCycle","Adaptador: "+negocios.get(position).getNombre());

            TextView titulo = item.findViewById(R.id.titulo);
            titulo.setText(negocios.get(position).getNombre());

            TextView actividad = item.findViewById(R.id.actividad);
            actividad.setText(negocios.get(position).getClase_actividad());






            return(item);
        }

    }
}

