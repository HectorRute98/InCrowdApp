package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResetUseCaseTest{

    lateinit var resetUseCase: ResetUseCase


    @Before
    fun onBefore(){
        MockKAnnotations.init(this)

        resetUseCase = ResetUseCase()
    }



    @Test
    fun `when the api confirm your password reset`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel(null,null,null,"javitoperezco@gmail.com", 0.0F)

        //When
        val response = resetUseCase()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api deny your password reset`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel(null,null,null,"correo_dont_exists", 0.0F)

        //When
        val response = resetUseCase()

        //Then
        assert(response.success == false)

    }




}
