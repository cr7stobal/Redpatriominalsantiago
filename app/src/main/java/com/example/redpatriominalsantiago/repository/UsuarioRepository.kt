package com.example.redpatriominalsantiago.repository

import android.util.Log
import com.example.redpatriominalsantiago.model.Usuario
import com.example.redpatriominalsantiago.network.RetrofitClient
import com.example.redpatriominalsantiago.network.UsuarioApi

class UsuarioRepository(
    private val api: UsuarioApi = RetrofitClient.api
) {
    suspend fun registrar(usuario: Usuario): Int =
        try {
            api.registro(usuario).code()
        } catch (e: Exception) {
            Log.e("API", "Error al registrar: ${e.javaClass.simpleName} - ${e.message}", e)
            -1
        }

    suspend fun login(usuario: Usuario): Int =
        try {
            api.login(usuario).code()
        } catch (e: Exception) {
            Log.e("API", "Error al hacer login: ${e.javaClass.simpleName} - ${e.message}", e)
            -1
        }
}