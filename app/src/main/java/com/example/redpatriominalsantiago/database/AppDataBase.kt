package com.example.redpatriominalsantiago.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.redpatriominalsantiago.dao.UsuarioDao
import com.example.redpatriominalsantiago.model.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UsuarioDao
}