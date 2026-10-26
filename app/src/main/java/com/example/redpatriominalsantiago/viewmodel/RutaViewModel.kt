package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.redpatriominalsantiago.model.Templo
import com.example.redpatriominalsantiago.repository.RutaRepository
import com.example.redpatriominalsantiago.repository.TemploRepository

class RutaViewModel : ViewModel() {

    // Las paradas vienen del repositorio (la misma lista que usa la ficha)
    val paradas = RutaRepository.paradas

    // Estado del interruptor "Solicitar guía voluntario"
    var solicitarGuia by mutableStateOf(true)

    fun cambiarSolicitarGuia(nuevoValor: Boolean) {
        solicitarGuia = nuevoValor
    }

    fun quitarParada(templo: Templo) {
        RutaRepository.quitar(templo)
    }

    // Agrega el primer templo que todavía no está en la ruta
    fun agregarSiguienteParada() {
        val siguiente = TemploRepository.obtenerTemplos().firstOrNull { !RutaRepository.contiene(it) }
        if (siguiente != null) {
            RutaRepository.agregar(siguiente)
        }
    }

    // ¿Quedan templos por agregar? Sirve para habilitar o no el botón "+ Parada"
    fun quedanTemplos(): Boolean {
        return TemploRepository.obtenerTemplos().any { !RutaRepository.contiene(it) }
    }

    // Cantidad de comunas distintas que recorre la ruta
    fun cantidadComunas(): Int {
        return paradas.map { it.comuna }.distinct().size
    }

    // Suma los minutos de visita y los muestra como "2 h 05"
    fun tiempoTotal(): String {
        val minutos = paradas.sumOf { it.minutosVisita }
        val horas = minutos / 60
        val resto = minutos % 60
        return "$horas h ${resto.toString().padStart(2, '0')}"
    }
}
