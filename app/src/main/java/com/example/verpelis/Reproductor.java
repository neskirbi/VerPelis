package com.example.verpelis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

public class Reproductor extends AppCompatActivity {
    String nombre,foto,link;
    VideoView reproductor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        nombre=getIntent().getExtras().get("nombre").toString();
        foto=getIntent().getExtras().get("portada").toString();
        link=getIntent().getExtras().get("link").toString();
        reproductor = findViewById(R.id.reproductor);
        reproductor.setVideoPath(link);
        reproductor.start();
        Log.i("Reproductior",link);

        Toast.makeText(this, ""+nombre, Toast.LENGTH_SHORT).show();

    }
}
