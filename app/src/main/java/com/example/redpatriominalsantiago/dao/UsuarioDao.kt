package com.example.redpatriominalsantiago.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import com.example.redpatriominalsantiago.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert
    suspend fun usuarioAlmacenar(usuario: Usuario)

    @Update
    suspend fun usuarioActualizar(usuario: Usuario)

    @Delete
    suspend fun usuarioEliminar(usuario: Usuario)

    @Query("SELECT * FROM usuario ORDER by id DESC")
    fun usuarioListar(): Flow<List<Usuario>>

    // CAMBIO: busca un usuario por su correo (sirve para saber si ya existe)
    @Query("SELECT * FROM usuario WHERE mail = :mail LIMIT 1")
    suspend fun usuarioPorMail(mail: String): Usuario?

    @Query("""
        SELECT * FROM usuario
        WHERE mail = :mail AND pass = :pass
        LIMIT 1
    """)
    suspend fun usuarioLogin(mail: String, pass: String): Usuario?

    @Query("""
        SELECT * FROM usuario
        WHERE id = :id LIMIT 1
    """)
    suspend fun usuarioObtenerInfo(id: Int): Usuario?
}