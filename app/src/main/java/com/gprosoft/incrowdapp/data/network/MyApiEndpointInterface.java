package com.gprosoft.incrowdapp.data.network;

import com.gprosoft.incrowdapp.data.model.Evento;
import com.gprosoft.incrowdapp.data.model.RespuestaModel;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MyApiEndpointInterface {

    public static final String BASE_URL = "https://incrown.onrender.com/incrown_app/";

    @GET("Eventos/{organizador}/")
    Call<List<Evento>> eventoUsuario(@Path("organizador") String username);

    @POST("CreateEvento/")
    Call<RespuestaModel> createEvento(@Body Evento evento);

    @PUT("UpdateEvento/{eventoname}/")
    Call<Evento> updateEvento(@Path("eventoname") String eventoname, @Body RequestBody evento);

    @DELETE("DeleteEvento/{nomEvento}/")
    Call<ResponseBody> deleteEvento(@Path("nomEvento") String nomEvento);

}
