package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpatriominalsantiago.model.Usuario
import com.example.redpatriominalsantiago.repository.UsuarioRepository
import kotlinx.coroutines.launch

class IniciarSesionViewModel : ViewModel() {

    private val repository = UsuarioRepository()

    var mail by mutableStateOf("")
    var pass by mutableStateOf("")
    var textoVisible by mutableStateOf(false)
    var mensajeError by mutableStateOf("")

    fun cambiarMail(nuevoMail: String) { mail = nuevoMail }
    fun cambiarContrasena(nuevaContrasena: String) { pass = nuevaContrasena }

    fun login(onExito: () -> Unit) {
        textoVisible = false
        viewModelScope.launch {
            when (repository.login(Usuario(mail = mail.trim(), pass = pass))) {
                200 -> onExito()
                401 -> { mensajeError = "Credenciales incorrectas"; textoVisible = true }
                else -> { mensajeError = "No se pudo conectar con el servidor"; textoVisible = true }
            }
        }
    }
}