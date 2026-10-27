package com.example.redpatriominalsantiago.model

import android.content.Context // agregué este import para poder guardar datos en el teléfono

object Sesion {
    var nombre = ""
    var iniciada = false // agregué esto para saber si hay una sesión iniciada

    // agregué esta función: abre el almacenamiento del teléfono llamado "sesion"
    private fun datos(context: Context) =
        context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    // agregué esta función: guarda el nombre y marca la sesión como iniciada, queda guardado aunque se cierre la app
    fun guardar(context: Context, nombreUsuario: String) {
        nombre = nombreUsuario
        iniciada = true
        datos(context).edit()
            .putString("nombre", nombreUsuario)
            .putBoolean("iniciada", true)
            .apply()
    }

    // agregué esta función: lee lo que se guardó la última vez, se usa al abrir la app
    fun cargar(context: Context) {
        nombre = datos(context).getString("nombre", "") ?: ""
        iniciada = datos(context).getBoolean("iniciada", false)
    }

    // agregué esta función: borra la sesión (todavía no está conectada a ningún botón)
    fun cerrar(context: Context) {
        nombre = ""
        iniciada = false
        datos(context).edit().clear().apply()
    }
}