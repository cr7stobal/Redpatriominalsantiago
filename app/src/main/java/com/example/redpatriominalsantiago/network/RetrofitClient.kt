package com.example.redpatriominalsantiago.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 10.0.2.2 = el "localhost" de tu PC visto desde el emulador
    private const val BASE_URL = "http://192.168.1.117:8080/"

    val api: UsuarioApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioApi::class.java)
    }
}