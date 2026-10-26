package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.redpatriominalsantiago.model.Templo
import com.example.redpatriominalsantiago.repository.TemploRepository

class MapaViewModel : ViewModel() {

    // Los templos vienen del repositorio
    val templos = TemploRepository.obtenerTemplos()

    // Filtro elegido en los chips. Al cambiar, la pantalla se vuelve a dibujar.
    var filtro by mutableStateOf("Todos")

    fun cambiarFiltro(nuevoFiltro: String) {
        filtro = nuevoFiltro
    }

    // Devuelve solo los templos que coinciden con el filtro
    fun templosVisibles(): List<Templo> {
        if (filtro == "Todos") {
            return templos
        }
        return templos.filter { it.estado == filtro }
    }
}
