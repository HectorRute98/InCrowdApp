package com.gprosoft.incrowdapp.data.model

import com.google.gson.annotations.SerializedName

//@SerializedName("nombre que vamos a recibir de backend") aunque sea el mismo
data class RespuestaModel(
    @SerializedName("success") val success:Boolean?,
    @SerializedName("message") val message:String?,
    @SerializedName("status") val status:String?
)