package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.redpatriominalsantiago.model.Templo
import com.example.redpatriominalsantiago.repository.TemploRepository

class FichaViewModel : ViewModel() {

    // Pestaña elegida: "Historia", "Horarios", "Eventos" o "Guías"
    var pestana by mutableStateOf("Historia")

    fun cambiarPestana(nuevaPestana: String) {
        pestana = nuevaPestana
    }

    // Busca el templo que se debe mostrar
    fun obtenerTemplo(id: Int): Templo? {
        return TemploRepository.buscarPorId(id)
    }
}
