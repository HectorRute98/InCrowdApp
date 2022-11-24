package com.gprosoft.incrowdapp.data.network

import com.gprosoft.incrowdapp.core.RetrofitHelper
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

//Para cuando nuestro repositorio tenga que sacar la información de nuestra base de datos
//El resto del proyecto siempre que llame aquí, le va a devolver un servicio de internet sea como sea

class RespuestaService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getLoginResponse():RespuestaModel{
        //Para que se ejecute la llamada en un hilo secundario para no saturar la interfaz del usuario que es el hilo principal
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RespuestaApiClient::class.java).getRespuestaLogin(
                UsuarioProvider.usuarioModel.username,
                UsuarioProvider.usuarioModel.password
            )
            response.body() ?: RespuestaModel(false,"Respuesta nula","500") //Si la respuesta es nula, que quieres devolver
        }
    }

    suspend fun getRegisterResponse():RespuestaModel{
        //Para que se ejecute la llamada en un hilo secundario para no saturar la interfaz del usuario que es el hilo principal
        return withContext(Dispatchers.IO){
            val usuarioModel = UsuarioModel(UsuarioProvider.usuarioModel.username,
                UsuarioProvider.usuarioModel.password,
                UsuarioProvider.usuarioModel.name,
                UsuarioProvider.usuarioModel.email,
                UsuarioProvider.usuarioModel.valoracion)
            val response = retrofit.create(RespuestaApiClient::class.java).getRespuestaRegister(usuarioModel)
            response.body() ?: RespuestaModel(false,"Respuesta nula","500") //Si la respuesta es nula, que quieres devolver
        }
    }

    suspend fun getResetResponse():RespuestaModel{
        //Para que se ejecute la llamada en un hilo secundario para no saturar la interfaz del usuario que es el hilo principal
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RespuestaApiClient::class.java).getRespuestaReset(UsuarioProvider.usuarioModel.email)
            response.body() ?: RespuestaModel(false,"Respuesta nula","500") //Si la respuesta es nula, que quieres devolver
        }
    }

    suspend fun getDeleteUserResponse():RespuestaModel{
        //Para que se ejecute la llamada en un hilo secundario para no saturar la interfaz del usuario que es el hilo principal
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RespuestaApiClient::class.java).getRespuestaDeleteUser(UsuarioProvider.usuarioModel.username)
            response.body() ?: RespuestaModel(false,"Respuesta nula","500") //Si la respuesta es nula, que quieres devolver
        }
    }

}