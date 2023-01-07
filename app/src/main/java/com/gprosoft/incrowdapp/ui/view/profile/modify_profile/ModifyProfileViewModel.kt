package com.gprosoft.incrowdapp.ui.view.profile.modify_profile

import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import com.gprosoft.incrowdapp.domain.DeleteUserUseCase
import com.gprosoft.incrowdapp.domain.ModifyProfileUseCase
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class ModifyProfileViewModel : ViewModel() {

    val respuestaModel = MutableLiveData<RespuestaModel>()
    val respuestaModel2 = MutableLiveData<RespuestaModel>()
    val isLoading = MutableLiveData<Boolean>()

    var modifyProfileUseCase = ModifyProfileUseCase()
    var deleteUserUseCase = DeleteUserUseCase()


    fun deleteAccount(){
        viewModelScope.launch{
            isLoading.postValue(true)
            var currentRespuesta2: RespuestaModel = deleteUserUseCase()
            //println(currentRespuesta2.status + currentRespuesta2.message+currentRespuesta2.success)
            respuestaModel2.postValue(currentRespuesta2)
            isLoading.postValue(false)
        }
    }

    fun saveChanges(
        nameET: EditText,
        usernameET: TextView,
        emailET: EditText,
        passwordET: EditText
    ) {
        viewModelScope.launch{
            var currentRespuesta: RespuestaModel = RespuestaModel(false, "Invalid data", "300")
            if(usernameET.text.isNullOrEmpty() && passwordET.text.isNullOrEmpty() && nameET.text.isNullOrEmpty() && emailET.text.isNullOrEmpty()) {
                respuestaModel.postValue(currentRespuesta)
            }else{
                var name:String?
                if(nameET.text.isNullOrEmpty()){
                    name = UsuarioProvider.usuarioModel.name
                }else{
                    name = nameET.text.toString()
                }

                var password:String?
                if(passwordET.text.isNullOrEmpty()){
                    password = UsuarioProvider.usuarioModel.password
                }else{
                    password = passwordET.text.toString()
                }

                var email:String?
                if(emailET.text.isNullOrEmpty()){
                    email = UsuarioProvider.usuarioModel.email
                }else{
                    email = emailET.text.toString()
                }

                if(checkPass(password!!) && checkEmail(email!!)){
                    isLoading.postValue(true)
                    UsuarioProvider.usuarioNuevo = UsuarioModel(UsuarioProvider.usuarioModel.username,password,name,email, UsuarioProvider.usuarioModel.valoracion)
                    currentRespuesta = modifyProfileUseCase()
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