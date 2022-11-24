package com.gprosoft.incrowdapp.ui.view.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.domain.RegisterUseCase
import kotlinx.coroutines.launch
import java.util.regex.Pattern

//añadiendo : ViewModel extiende de la clase viewmodel
class RegisterViewModel : ViewModel() {

    /*El live<Data> permite a nuestra activity o fragment suscribirse
    a un modelo de datos nuestro y que se llame automaticamente cuando se
    realicen cambios en dicho modelo.
    En este caso, el modelo que encapsulamos es RespuestaModel que va a ser
    continuamente cambiado con la funcion randomRespuesta.
    */
    val respuestaModel = MutableLiveData<RespuestaModel>()
    val isLoading = MutableLiveData<Boolean>()


    var registerUseCase = RegisterUseCase()

    fun comprobarRegister(username:String , password:String, name:String, email:String) {
        viewModelScope.launch{
            var currentRespuesta: RespuestaModel = RespuestaModel(false, "Invalid data", "300")
            if(username.isNullOrEmpty() || password.isNullOrEmpty() ||
                name.isNullOrEmpty() || email.isNullOrEmpty()){
                respuestaModel.postValue(currentRespuesta)
            }else{
                if(checkPass(password) && checkEmail(email)){
                    isLoading.postValue(true)
                    UsuarioProvider.usuarioModel = UsuarioModel(username,password,name,email,0.0F)
                    currentRespuesta = registerUseCase()
                    respuestaModel.postValue(currentRespuesta)
                    isLoading.postValue(false)
                }else{
                    respuestaModel.postValue(currentRespuesta)
                }
            }
        }
    }

    private fun checkPass(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                ".{8,}" +               //at least 8 characters
                "$");
        println(password)
        var a = passwordREGEX.matcher(password).matches()
        println(a)
        return passwordREGEX.matcher(password).matches()
    }

    private fun checkEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}