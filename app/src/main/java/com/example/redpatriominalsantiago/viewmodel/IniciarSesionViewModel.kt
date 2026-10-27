package com.example.redpatriominalsantiago.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpatriominalsantiago.database.AppDatabase
import com.example.redpatriominalsantiago.model.Sesion
import com.example.redpatriominalsantiago.model.Usuario
import com.example.redpatriominalsantiago.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IniciarSesionViewModel(application: Application): AndroidViewModel(application) {

    private val repository: UsuarioRepository

    var mail by mutableStateOf("")
    var pass by mutableStateOf("")

    var textoVisible by mutableStateOf(false)

    // la "_" al principio es para saber que es privada
    private val _loginCorrecto = MutableStateFlow(false)
    val loginCorrecto = _loginCorrecto.asStateFlow()

    init {
        val usuarioDao = AppDatabase.getDataBase(application).usuarioDao()
        repository = UsuarioRepository(usuarioDao)
    }

    fun cambiarMail(nuevoMail: String) {
        mail = nuevoMail
    }
    fun cambiarContrasena(nuevaContrasena: String){
        pass = nuevaContrasena
    }

    //val usuarioCorrecto = Usuario("admin@gmail.com", "123")

    fun login(alTerminar: () -> Unit) { // cambié esto: ahora recibe qué hacer cuando el login sale bien

        // agregué esto para que no deje ingresar si el mail o la contraseña están vacíos
        if (mail == "" || pass == "") {
            println("CR7: No pude ingresar, faltan datos")
            textoVisible = true
            return
        }

        viewModelScope.launch {
            textoVisible = false
            try {
                val usuario = repository.usuarioLogin(mail, pass)

                if (usuario != null){
                    _loginCorrecto.value = true
                    // cambié Sesion.nombre = usuario.nombre por esta línea, para que además de guardar el nombre deje la sesión guardada en el teléfono
                    Sesion.guardar(getApplication<Application>(), usuario.nombre)
                    println("CR7: Login OK")
                    alTerminar()
                } else {
                    // busca si el mail existe para saber que fallo
                    val usuarioMail = repository.usuarioPorMail(mail)
                    if (usuarioMail == null) {
                        println("CR7: No pude ingresar, el mail esta mal o no existe")
                    } else {
                        println("CR7: No pude ingresar, la contrasena esta mal")
                    }
                    textoVisible = true
                }
            } catch (e: Exception){
                println("CR7: Error al iniciar sesion")
                textoVisible = true
            }
        }
    }
}