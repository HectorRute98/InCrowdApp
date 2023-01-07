package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.RespuestaRepository
import com.gprosoft.incrowdapp.data.UsuarioRepository
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel

class ModifyProfileUseCase {

    private val repository = UsuarioRepository()

    suspend operator fun invoke(): RespuestaModel {
        return repository.getUsuarioModifyProfile()
    }


}