package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RegisterUseCaseTest{

    lateinit var registerUseCase: RegisterUseCase
    lateinit var deleteUserUseCase: DeleteUserUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        registerUseCase = RegisterUseCase()
        deleteUserUseCase = DeleteUserUseCase()
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
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234","nueva_pass","nuevo_name","nuevo_correo2", 0.0F)

        //When
        val response = registerUseCase()

        //Then
        assert(response.success == false)

    }

    @Test
    fun `when the api deny your register because your email already exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("nuevo_user2","nueva_pass","nuevo_name","javitoperezco@gmail.com", 0.0F)

        //When
        val response = registerUseCase()

        //Then
        assert(response.success == false)

    }

    @Test
    fun `when the api deny your register because your email and your username already exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234","nueva_pass","nuevo_name","javitoperezco@gmail.com", 0.0F)

        //When
        val response = registerUseCase()

        //Then
        assert(response.success == false)

    }

    @Test
    fun `when the api reset the values for this test`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("nuevo_user","nueva_pass","nuevo_name","nuevo_correo", 0.0F)

        //When
        val response = deleteUserUseCase()

        //Then
        assert(response.success == true)

    }

}