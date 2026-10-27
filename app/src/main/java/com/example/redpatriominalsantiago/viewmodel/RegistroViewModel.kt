package com.example.redpatriominalsantiago.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpatriominalsantiago.database.AppDatabase
import com.example.redpatriominalsantiago.model.Usuario
import com.example.redpatriominalsantiago.repository.UsuarioRepository
import kotlinx.coroutines.launch

class RegistroViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UsuarioRepository

    var nombre by mutableStateOf("")
    var mail by mutableStateOf("")
    var pass by mutableStateOf("")
    var pass2 by mutableStateOf("")
    var mensaje by mutableStateOf("")

    init {
        val usuarioDao = AppDatabase.getDataBase(application).usuarioDao()
        repository = UsuarioRepository(usuarioDao)
    }

    fun cambiarNombre(n: String) {
        nombre = n
    }
    fun cambiarMail(m: String) {
        mail = m
    }
    fun cambiarPass(p: String) {
        pass = p
    }
    fun cambiarPass2(p2: String) {
        pass2 = p2
    }
    fun usuarioAlmacenar(alTerminar: () -> Unit) {
        // valida que el mail tenga @ y .
        if (!mail.contains("@") || !mail.contains(".")) {
            mensaje = "El correo debe tener @ y ."
            println("CR7: Mail invalido, debe contener @ y .")
            return
        }
        // valida que la contraseña no este vacia
        if (pass == "") {
            mensaje = "Debes ingresar una contrasena"
            println("CR7: Contrasena vacia")
            return
        }
        // valida que las contraseñas sean iguales
        if (pass != pass2) {
            mensaje = "Las contrasenas no coinciden"
            println("CR7: Las contrasenas no coinciden")
            return
        }

        mensaje = ""

        val usuario = Usuario(0, nombre, mail, pass)

        viewModelScope.launch {
            try {
                repository.usuarioAlmacenar(usuario)
                println("CR7: Usuario Almacenado")
                alTerminar()
            } catch (e: Exception){
                println("CR7: Error al almacenar Usuario")
            }
        }
    }


}