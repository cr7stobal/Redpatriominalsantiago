package com.example.redpatriominalsantiago.network

import com.example.redpatriominalsantiago.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApi {
    @POST("api/usuarios/registro")
    suspend fun registro(@Body usuario: Usuario): Response<Unit>

    @POST("api/usuarios/login")
    suspend fun login(@Body usuario: Usuario): Response<Unit>
}