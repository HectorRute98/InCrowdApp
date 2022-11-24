package com.gprosoft.incrowdapp.data

import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.RespuestaProvider
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.RespuestaService
import com.gprosoft.incrowdapp.data.network.UsuarioService

class UsuarioRepository {

    private val api = UsuarioService()

    suspend fun getUsuarioInfo(): UsuarioModel {
        val response = api.getUserInfo() //la primera vez que yo llame al repositorio,
        // va a llamar al service (aquí), este service va a llamar al api client,
        // va a recuperar esa respuesta, se la devolvera al service y este al repositorio (esto),
        // y el repositorio ahora le dirá al provider  que la respuesta actual es el response
        UsuarioProvider.usuarioModel = UsuarioModel(response.username ,
                                                    UsuarioProvider.usuarioModel.password ,
                                                    response.name, response.email,
                                                    response.valoracion) //Lo almacenamos en esa memoria local
        return response     //lo retorno
    }

    suspend fun getUsuarioModifyProfile(): UsuarioModel {
        val response = api.getModifyProfileUser() //la primera vez que yo llame al repositorio,
        // va a llamar al service (aquí), este service va a llamar al api client,
        // va a recuperar esa respuesta, se la devolvera al service y este al repositorio (esto),
        // y el repositorio ahora le dirá al provider  que la respuesta actual es el response

        UsuarioProvider.usuarioModel = UsuarioModel(response.username ,
                                                    UsuarioProvider.usuarioModel.password ,
                                                    response.name, response.email,
                                                    response.valoracion) //Lo almacenamos en esa memoria local
        println(UsuarioProvider.usuarioModel.name + " " +
                UsuarioProvider.usuarioModel.username + " " +
                UsuarioProvider.usuarioModel.email + " " +
                UsuarioProvider.usuarioModel.password + " " +
                UsuarioProvider.usuarioModel.valoracion
        )
        return response     //lo retorno
    }

}