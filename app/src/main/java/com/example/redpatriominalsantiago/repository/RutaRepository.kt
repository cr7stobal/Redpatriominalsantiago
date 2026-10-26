package com.example.redpatriominalsantiago.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.redpatriominalsantiago.model.Templo

// Repository de la ruta del turista.
// Es compartido: la ficha del templo agrega paradas y la pantalla de rutas las muestra.
object RutaRepository {

    // mutableStateListOf: lista que se puede cambiar y que Compose "observa".
    // Al agregar o quitar una parada, las pantallas que la usan se redibujan solas.
    val paradas = mutableStateListOf<Templo>()

    init {
        // Ruta de ejemplo con 4 paradas, como en el Figma (se ejecuta una sola vez)
        listOf(1, 3, 2, 4).forEach { id ->
            TemploRepository.buscarPorId(id)?.let { paradas.add(it) }
        }
    }

    fun agregar(templo: Templo) {
        if (!contiene(templo)) {      // no se repite una parada
            paradas.add(templo)
        }
    }

    fun quitar(templo: Templo) {
        paradas.remove(templo)
    }

    fun contiene(templo: Templo): Boolean {
        return paradas.any { it.id == templo.id }
    }
}
