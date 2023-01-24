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

class UsuarioService {

    private val retrofit = RetrofitHelper.getRetrofit()
    private val MEDIA_TYPE_JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")
    private val J = JSONObject()

    suspend fun getUserInfo(): UsuarioModel {
        //Para que se ejecute la llamada en un hilo secundario para no saturar la interfaz del usuario que es el hilo principal
        return withContext(Dispatchers.IO){
            val response = retrofit.create(UsuarioApiClient::class.java).getUsuarioInfo(
                UsuarioProvider.usuarioModel.username,
            )
            response.body() ?: UsuarioModel("fallo","fallo","fallo", "fallo", 0.0F) //Si la respuesta es nula, que quieres devolver
        }
    }

    suspend fun getModifyProfileUser(): RespuestaModel {
        return withContext(Dispatchers.IO){
            println(UsuarioProvider.usuarioNuevo.name + UsuarioProvider.usuarioNuevo.username +
                    UsuarioProvider.usuarioNuevo.email + UsuarioProvider.usuarioNuevo.password +
                    UsuarioProvider.usuarioNuevo.valoracion)
            val response = retrofit.create(UsuarioApiClient::class.java).getUsuarioModifyProfile(UsuarioProvider.usuarioModel.username , UsuarioProvider.usuarioNuevo)
            response.body() ?: RespuestaModel(null,null,null) //Si la respuesta es nula, que quieres devolver
        }
    }

}