package com.example.redpatriominalsantiago.model

import androidx.compose.runtime.mutableStateListOf

data class Ruta(
    val id: Int,
    val nombre: String,
    val paradas: List<Int>,
    val guia: Boolean
)

object RutasGuardadas {
    val rutas = mutableStateListOf(
        Ruta(1, "Ruta colonial del sur", listOf(1, 2, 4), false),
        Ruta(2, "Patrimonio de barrio", listOf(3, 1), true)
    )

    // GUARDA UNA RUTA NUEVA O ACTUALIZA UNA EXISTENTE
    fun guardar(id: Int, nombre: String, paradas: List<Int>, guia: Boolean) {
        if (id == 0) {
            val nuevoId = (rutas.maxOfOrNull { it.id } ?: 0) + 1
            rutas.add(Ruta(nuevoId, nombre, paradas, guia))
        } else {
            val posicion = rutas.indexOfFirst { it.id == id }
            if (posicion != -1) rutas[posicion] = Ruta(id, nombre, paradas, guia)
        }
    }
}

// Estimaciones Fijas De Tiempo Y Distancia
const val VISITA_MIN = 30
const val TRASLADO_MIN = 15
const val KM_POR_TRAMO = 1.8

fun minutosRuta(paradas: Int): Int {
    if (paradas == 0) return 0
    return paradas * VISITA_MIN + (paradas - 1) * TRASLADO_MIN
}

fun kmRuta(paradas: Int): Double {
    if (paradas == 0) return 0.0
    return (paradas - 1) * KM_POR_TRAMO
}

fun textoTiempo(minutos: Int): String {
    val horas = minutos / 60
    val resto = minutos % 60
    return if (horas > 0) "$horas h $resto min" else "$resto min"
}

fun textoKm(km: Double): String = "%.1f km".format(km).replace('.', ',')