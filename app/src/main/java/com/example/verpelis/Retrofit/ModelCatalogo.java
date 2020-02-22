package com.example.verpelis.Retrofit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ModelCatalogo {
    private String id_catalogo;
    private String nombre;
    private String descripcion;
    private String tags;
    private String link;
    private String foto;
    private String fecha;

    public String getId_catalogo() {
        return id_catalogo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTags() {
        return tags;
    }

    public String getLink() {
        return link;
    }

    public String getPortada() {
        return foto;
    }

    public String getFecha() {
        return fecha;
    }
}
