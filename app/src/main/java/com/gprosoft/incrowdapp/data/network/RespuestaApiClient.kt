package com.gprosoft.incrowdapp.data.network

import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RespuestaApiClient {

    @GET("Login/{username}/{password}")
    suspend fun getRespuestaLogin(@Path("username") username:String? , @Path("password") password:String?):Response<RespuestaModel> //Meter aquí la llamada a la base de datos

    @POST("CreateUsuario/")
    suspend fun getRespuestaRegister(@Body user: UsuarioModel?): Response<RespuestaModel>

    @GET("enviarCorreo/{correo}/")
    suspend fun getRespuestaReset(@Path("correo") correo:String?):Response<RespuestaModel>

    @GET("DeleteUsuario/{username}/")
    suspend fun getRespuestaDeleteUser(@Path("username") username:String?):Response<RespuestaModel>

}