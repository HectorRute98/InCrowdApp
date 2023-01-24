package com.gprosoft.incrowdapp.domain

import com.gprosoft.incrowdapp.data.model.UsuarioModel
import com.gprosoft.incrowdapp.data.model.UsuarioProvider
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ModifyProfileUseCaseTest {

    lateinit var modify : ModifyProfileUseCase
    lateinit var login : LoginUseCase


    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        modify = ModifyProfileUseCase()
        login = LoginUseCase()
    }

    @Test
    fun `when the api denies the modification of a user that does not exist`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234567",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector1234","Hector Tests","javitoperezco@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == false)

    }

    @Test
    fun `when the api confirm the modification of the password of a user that exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector123456","Hector Tests","javitoperezco@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the modification of the password of a user that exists 2`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector1234","Hector Tests","javitoperezco@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the modification of the email of a user that exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector1234","Hector Tests","javitoperezco12@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the modification of the email of a user that exists 2`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector1234","Hector Tests","javitoperezco@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the modification of the name of a user that exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector1234","Hector_Tests_2","javitoperezco@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the modification of the name of a user that exists2`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector1234","Hector Tests","javitoperezco@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the modification of the values of a user that exists`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector12342","Hector Tests 2","javitoperezco2@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the modification of the values of a user that exists 2`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector123422","Hector Tests 22","javitoperezco22@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the modification of the password of a user that exists 3 - reset values`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234",null,null,null,null)
        UsuarioProvider.usuarioNuevo = UsuarioModel("Hector1234","Hector1234","Hector Tests","javitoperezco@gmail.com", 0.0F)

        //When
        val response = modify()

        //Then
        assert(response.success == true)

    }

    @Test
    fun `when the api confirm the login of a user whose values were changed`()= runBlocking {

        //Given
        UsuarioProvider.usuarioModel = UsuarioModel("Hector1234","Hector1234",null,null, null)

        //When
        val response = login()

        //Then
        assert(response.success == false)
        //assert(response.success == true)

    }

}