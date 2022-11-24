package com.gprosoft.incrowdapp.ui.view.reset

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.domain.ResetUseCase
import kotlinx.coroutines.launch

//añadiendo : ViewModel extiende de la clase viewmodel
class ResetViewModel : ViewModel() {

    /*El live<Data> permite a nuestra activity o fragment suscribirse
    a un modelo de datos nuestro y que se llame automaticamente cuando se
    realicen cambios en dicho modelo.
    En este caso, el modelo que encapsulamos es RespuestaModel que va a ser
    continuamente cambiado con la funcion randomRespuesta.
    */
    val respuestaModel = MutableLiveData<RespuestaModel>()
    val isLoading = MutableLiveData<Boolean>()


    var resetUseCase = ResetUseCase()

    fun comprobarReset(correo:String) {
        viewModelScope.launch{
            var currentRespuesta: RespuestaModel = RespuestaModel(false, "Invalid data", "300")
            if(correo.isNullOrEmpty()){
                respuestaModel.postValue(currentRespuesta)
            }else{
                isLoading.postValue(true)
                UsuarioProvider.usuarioModel = UsuarioModel(null,null,null,correo,0.0F)
                currentRespuesta = resetUseCase()
                respuestaModel.postValue(currentRespuesta)
                isLoading.postValue(false)
            }
        }
    }
}