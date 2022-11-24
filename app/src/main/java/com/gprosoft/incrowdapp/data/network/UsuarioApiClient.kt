package com.gprosoft.incrowdapp.data.network

import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApiClient {

    @GET("Usuario/{username}/")
    suspend fun getUsuarioInfo(@Path("username") username:String?): Response<UsuarioModel>

    @PUT("UpdateUsuario/{username}/")
    suspend fun getUsuarioModifyProfile(@Path("username") username:String?,  @Body user: UsuarioModel?): Response<UsuarioModel>

}