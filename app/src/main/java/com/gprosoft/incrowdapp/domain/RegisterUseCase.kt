package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.RespuestaRepository
import com.gprosoft.incrowdapp.data.model.RespuestaModel

class RegisterUseCase {

    private val repository = RespuestaRepository()

    suspend operator fun invoke(): RespuestaModel {
        val respuesta = repository.getRespuestaRegister()
        return if(respuesta.success == true && respuesta.status == null){
            fun_auxiliar(respuesta)
        }else{
            respuesta
        }
    }

    fun fun_auxiliar(respuesta: RespuestaModel): RespuestaModel {
        return respuesta
    }

}