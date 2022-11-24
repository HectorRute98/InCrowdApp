package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RegisterUseCaseTest{

    lateinit var registerUseCase: RegisterUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        registerUseCase = RegisterUseCase()
    }

    @Test
    fun `when the api confirm your register`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("nuevo_user","nueva_pass","nuevo_name","nuevo_correo", 0.0F)

        //When
        val response = registerUseCase()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api deny your register because your username already exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("nuevo_user","nueva_pass","nuevo_name","nuevo_correo2", 0.0F)

        //When
        val response = registerUseCase()

        //Then
        assert(response.success == false)

    }

    @Test
    fun `when the api deny your register because your email already exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("nuevo_user2","nueva_pass","nuevo_name","nuevo_correo", 0.0F)

        //When
        val response = registerUseCase()

        //Then
        assert(response.success == false)

    }

    @Test
    fun `when the api deny your register because your email and your username already exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("nuevo_user","nueva_pass","nuevo_name","nuevo_correo", 0.0F)

        //When
        val response = registerUseCase()

        //Then
        assert(response.success == false)

    }

}