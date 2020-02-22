package com.example.verpelis.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCatalogo {
    @GET("api/GetCatalogo")
    Call<List<ModelCatalogo>> getCatalogo();

    @GET("app/api/denue/v1/consulta/Buscar/tacos/22.8911979,-109.914867/500/ccfacb58-e06d-407a-b901-25284acb3b28")
    Call<List<ModelNegocio>> getNegocios();

}
