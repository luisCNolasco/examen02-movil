package com.example.examen02movilcarranza.api;

import com.example.examen02movilcarranza.entity.Autor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AutorApi {

    @POST("autor/")
    public abstract Call<Autor> insertaAutor(@Body Autor obj);
}
