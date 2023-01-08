package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.RespuestaRepository
import com.gprosoft.incrowdapp.data.model.RespuestaModel
import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations


class LoginUseCaseTest{

    lateinit var loginUseCase: LoginUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        loginUseCase = LoginUseCase()
    }

    @Test
    fun `when the api confirm your login`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234","Hector1234",null,null, 0.0F)

        //When
        val response = loginUseCase()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api deny your login`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("unknown","unknown",null,null, 0.0F)

        //When
        val response = loginUseCase()

        //Then
        assert(response.success == false)

    }

    @Test
    fun `when the api deny your login because your pass`()= runBlocking {

        //Given

        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234","unknown",null,null, 0.0F)

        //When
        val response = loginUseCase()

        //Then
        assert(response.success == false)

    }

}