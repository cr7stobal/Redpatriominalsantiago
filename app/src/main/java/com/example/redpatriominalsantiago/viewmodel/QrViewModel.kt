package com.example.redpatriominalsantiago.viewmodel

import androidx.lifecycle.ViewModel
import com.example.redpatriominalsantiago.repository.TemploRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ViewModel del lector QR (misma forma que el ejemplo del profesor: StateFlow)
class QrViewModel : ViewModel() {

    // _contenidoQR se puede cambiar solo aquí adentro; la pantalla lee contenidoQR
    private val _contenidoQR = MutableStateFlow("")
    val contenidoQR: StateFlow<String> = _contenidoQR.asStateFlow()

    fun guardarQR(contenido: String) {
        _contenidoQR.value = contenido
    }

    // Si el QR es de un templo de la red ("REDPATRIMONIAL;TEMPLO;ID=3"),
    // devuelve su id. Si es otro código, o el templo no existe, devuelve null.
    fun obtenerIdTemplo(contenido: String): Int? {
        val prefijo = "REDPATRIMONIAL;TEMPLO;ID="
        if (!contenido.startsWith(prefijo)) {
            return null
        }
        val id = contenido.removePrefix(prefijo).toIntOrNull() ?: return null
        if (TemploRepository.buscarPorId(id) == null) {
            return null
        }
        return id
    }
}
