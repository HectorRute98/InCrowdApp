package com.gprosoft.incrowdapp.data

import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.RespuestaProvider
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.data.network.RespuestaApiClient
import com.gprosoft.incrowdapp.data.network.RespuestaService


//Será la encargada de gestionar si accedemos a la parte de network o de database de momento el provider
class RespuestaRepository {

    private val api = RespuestaService()

    suspend fun getRespuestaLogin():RespuestaModel{
        val response = api.getLoginResponse() //la primera vez que yo llame al repositorio,
                                              // va a llamar al service (aquí), este service va a llamar al api client,
                                              // va a recuperar esa respuesta, se la devolvera al service y este al repositorio (esto),
                                              // y el repositorio ahora le dirá al provider  que la respuesta actual es el response
        RespuestaProvider.respuesta = response //Lo almacenamos en esa memoria local
        return response     //lo retorno
    }

    suspend fun getRespuestaRegister():RespuestaModel{
        val response = api.getRegisterResponse() //la primera vez que yo llame al repositorio,
        // va a llamar al service (aquí), este service va a llamar al api client,
        // va a recuperar esa respuesta, se la devolvera al service y este al repositorio (esto),
        // y el repositorio ahora le dirá al provider  que la respuesta actual es el response
        RespuestaProvider.respuesta = response //Lo almacenamos en esa memoria local
        return response     //lo retorno
    }

    suspend fun getRespuestaReset():RespuestaModel{
        val response = api.getResetResponse() //la primera vez que yo llame al repositorio,
        // va a llamar al service (aquí), este service va a llamar al api client,
        // va a recuperar esa respuesta, se la devolvera al service y este al repositorio (esto),
        // y el repositorio ahora le dirá al provider  que la respuesta actual es el response
        RespuestaProvider.respuesta = response //Lo almacenamos en esa memoria local
        return response     //lo retorno
    }

    suspend fun getRespuestaDeleteUser():RespuestaModel{
        val response = api.getDeleteUserResponse() //la primera vez que yo llame al repositorio,
        // va a llamar al service (aquí), este service va a llamar al api client,
        // va a recuperar esa respuesta, se la devolvera al service y este al repositorio (esto),
        // y el repositorio ahora le dirá al provider  que la respuesta actual es el response
        RespuestaProvider.respuesta = response //Lo almacenamos en esa memoria local
        return response     //lo retorno
    }

}