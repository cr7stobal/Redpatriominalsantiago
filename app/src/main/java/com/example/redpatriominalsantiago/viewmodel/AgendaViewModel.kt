package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.redpatriominalsantiago.model.Evento
import com.example.redpatriominalsantiago.repository.EventoRepository

class AgendaViewModel : ViewModel() {

    // Los eventos vienen del repositorio
    private val eventos = EventoRepository.obtenerEventos()

    // Categoría elegida en los chips
    var categoria by mutableStateOf("Todos")

    fun cambiarCategoria(nuevaCategoria: String) {
        categoria = nuevaCategoria
    }

    // Eventos filtrados y agrupados por día.
    // Resultado: "Sábado 03 de octubre" -> [evento1, evento2], "Domingo 04 de octubre" -> [...]
    fun eventosPorDia(): Map<String, List<Evento>> {
        val filtrados = if (categoria == "Todos") {
            eventos
        } else {
            eventos.filter { it.categoria == categoria }
        }
        return filtrados.groupBy { it.dia }
    }
}
