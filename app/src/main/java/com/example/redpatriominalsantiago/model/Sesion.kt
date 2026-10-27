package com.example.redpatriominalsantiago.model

import android.content.Context

object Sesion {
    var nombre = ""
    var iniciada = false

    // Abre El Almacenamiento Del Telefono Llamado Sesion
    private fun datos(context: Context) =
        context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    // GUARDA LA SESION EN EL TELEFONO
    fun guardar(context: Context, nombreUsuario: String) {
        nombre = nombreUsuario
        iniciada = true
        datos(context).edit()
            .putString("nombre", nombreUsuario)
            .putBoolean("iniciada", true)
            .apply()
    }

    fun cargar(context: Context) {
        nombre = datos(context).getString("nombre", "") ?: ""
        iniciada = datos(context).getBoolean("iniciada", false)
    }

    fun cerrar(context: Context) {
        nombre = ""
        iniciada = false
        datos(context).edit().clear().apply()
    }
}