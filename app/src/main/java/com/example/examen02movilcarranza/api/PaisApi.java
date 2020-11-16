package com.example.examen02movilcarranza.api;

import com.example.examen02movilcarranza.entity.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaisApi {
    @GET("pais/")
    public abstract Call<List<Pais>>listaPais();
}
