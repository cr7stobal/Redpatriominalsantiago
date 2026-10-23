package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.redpatriominalsantiago.model.Usuario

class IniciarSesionViewModel: ViewModel() {

    var mail by mutableStateOf("")
    var pass by mutableStateOf("")

    var textoVisible by mutableStateOf(false)
    fun cambiarMail(nuevoMail: String) {
        mail = nuevoMail
    }
    fun cambiarContrasena(nuevaContrasena: String){
        pass = nuevaContrasena
    }

    //val usuarioCorrecto = Usuario("admin@gmail.com", "123")

    fun login(): Boolean {
        var validador = false
        /*
        if (mail == usuarioCorrecto.mail && pass == usuarioCorrecto.pass) {
            validador = true
        }*/
        return validador
    }
}