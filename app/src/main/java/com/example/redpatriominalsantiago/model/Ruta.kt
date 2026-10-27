package com.example.redpatriominalsantiago.model

import androidx.compose.runtime.mutableStateListOf

// una ruta: tiene un nombre y la lista de ids de los lugares que visita (las paradas)
data class Ruta(
    val id: Int,
    val nombre: String,
    val paradas: List<Int>,
    val guia: Boolean
)

// rutas guardadas, con dos de ejemplo (datos ficticios)
object RutasGuardadas {
    val rutas = mutableStateListOf(
        Ruta(1, "Ruta colonial del sur", listOf(1, 2, 4), false),
        Ruta(2, "Patrimonio de barrio", listOf(3, 1), true)
    )

    // si id es 0 crea una ruta nueva, si no reemplaza la que ya existía
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

// estimaciones fijas hasta que tengamos GPS
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

// 165 minutos se muestra como "2 h 45 min"
fun textoTiempo(minutos: Int): String {
    val horas = minutos / 60
    val resto = minutos % 60
    return if (horas > 0) "$horas h $resto min" else "$resto min"
}

// 5.4 se muestra como "5,4 km"
fun textoKm(km: Double): String = "%.1f km".format(km).replace('.', ',')