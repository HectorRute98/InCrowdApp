package com.gprosoft.incrowdapp.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*Es un objeto porque de otra manera tendría que hacerlo una clase
y dentro introducir un companion object y esto es mas limpio*/
object RetrofitHelper {

    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://incrown.onrender.com/incrown_app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}