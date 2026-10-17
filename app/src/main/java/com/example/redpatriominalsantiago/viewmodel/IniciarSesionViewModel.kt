package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class IniciarSesionViewModel: ViewModel() {

    var mail by mutableStateOf("")
    var pass by mutableStateOf("")
    fun cambiarMail(nuevoMail: String) {
        mail = nuevoMail
    }
    fun cambiarContrasena(nuevaContrasena: String){
        pass = nuevaContrasena
    }
}