package com.example.redpatriominalsantiago.repository

import com.example.redpatriominalsantiago.dao.UsuarioDao
import com.example.redpatriominalsantiago.model.Usuario
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(private val usuarioDao: UsuarioDao) {
    val usuarios: Flow<List<Usuario>> = usuarioDao.usuarioListar()

    suspend fun usuarioAlmacenar(usuario: Usuario){
        usuarioDao.usuarioAlmacenar(usuario)
    }

    suspend fun usuarioEliminar(usuario: Usuario) {
        usuarioDao.usuarioActualizar(usuario)
    }

    suspend fun usuarioActualizaar(usuario: Usuario) {
        usuarioDao.usuarioActualizar(usuario)
    }

    suspend fun usuarioLogin(mail: String, pass: String): Usuario? {
        return usuarioDao.usuarioLogin(mail, pass)
    }

    suspend fun usuarioObtenerInfo(id: Int): Usuario? {
        return usuarioDao.usuarioObtenerInfo(id)
    }
    suspend fun usuarioPorMail(mail: String): Usuario? {
        return usuarioDao.usuarioPorMail(mail)
    }
}