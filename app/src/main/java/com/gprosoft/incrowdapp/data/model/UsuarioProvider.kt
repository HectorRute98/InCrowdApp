package com.gprosoft.incrowdapp.data.model

class UsuarioProvider {
    companion object {  //Sería como una clase estática de Java, to-do el mundo puede acceder a ella
        var usuarioModel:UsuarioModel = UsuarioModel(null,null,null,null, null)
        var usuarioNuevo:UsuarioModel = UsuarioModel(null,null,null,null, null)
    }
}