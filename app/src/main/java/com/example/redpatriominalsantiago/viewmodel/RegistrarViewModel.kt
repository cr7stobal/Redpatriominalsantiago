package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpatriominalsantiago.model.Usuario
import com.example.redpatriominalsantiago.repository.UsuarioRepository
import kotlinx.coroutines.launch

class RegistrarViewModel : ViewModel() {

    private val repository = UsuarioRepository()

    var mail by mutableStateOf("")
    var pass by mutableStateOf("")
    var pass2 by mutableStateOf("")
    var textoVisible by mutableStateOf(false)
    var mensajeError by mutableStateOf("")

    fun crearMail(nuevoMail: String) { mail = nuevoMail }
    fun crearPass(nuevaContrasena: String) { pass = nuevaContrasena }
    fun comprobarPass(comprobarPass: String) { pass2 = comprobarPass }

    fun todoBien(): Boolean =
        mail.contains("@") && mail.contains(".") && pass.isNotEmpty() && pass == pass2

    fun registrar(onExito: () -> Unit) {
        textoVisible = false
        if (!todoBien()) {
            mensajeError = "Revisa el correo y que las contraseñas coincidan"
            textoVisible = true
            return
        }
        viewModelScope.launch {
            when (repository.registrar(Usuario(mail = mail.trim(), pass = pass))) {
                201 -> onExito()
                409 -> { mensajeError = "Ese correo ya está registrado"; textoVisible = true }
                else -> { mensajeError = "No se pudo conectar con el servidor"; textoVisible = true }
            }
        }
    }
}