package com.gprosoft.incrowdapp.data.network

import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import retrofit2.Response
import retrofit2.http.*

interface UsuarioApiClient {

    @GET("Usuario/{username}/")
    suspend fun getUsuarioInfo(@Path("username") username:String?): Response<UsuarioModel>

    @POST("UpdateUsuario/{username}/")
    suspend fun getUsuarioModifyProfile(@Path("username") username:String?,  @Body user: UsuarioModel?): Response<RespuestaModel>

}