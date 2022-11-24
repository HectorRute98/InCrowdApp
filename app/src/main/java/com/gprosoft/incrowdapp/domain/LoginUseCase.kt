package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.RespuestaRepository
import com.gprosoft.incrowdapp.data.UsuarioRepository
import com.gprosoft.incrowdapp.data.model.RespuestaModel

class LoginUseCase {

    private val repository = RespuestaRepository()
    private val repository2 = UsuarioRepository()

    suspend operator fun invoke():RespuestaModel{
        val respuesta = repository.getRespuestaLogin()
        if(respuesta.success == true){
            repository2.getUsuarioInfo()
        }else{
        }
        return respuesta
    }

}