package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.RespuestaRepository
import com.gprosoft.incrowdapp.data.model.RespuestaModel

class ResetUseCase {

    private val repository = RespuestaRepository()

    suspend operator fun invoke(): RespuestaModel {
        return repository.getRespuestaReset()
    }

}