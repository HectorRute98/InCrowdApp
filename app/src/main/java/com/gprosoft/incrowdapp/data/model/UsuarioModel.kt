package com.gprosoft.incrowdapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UsuarioModel(
    @SerializedName("username") @Expose val username: String?,
    @SerializedName("password") @Expose val password: String?,
    @SerializedName("nombre") @Expose val name: String?,
    @SerializedName("correo")@Expose val email: String?,
    @SerializedName("valoracion")@Expose val valoracion: Float?
    ) : Serializable