package com.example.redpatriominalsantiago.database

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.redpatriominalsantiago.dao.UsuarioDao
import com.example.redpatriominalsantiago.model.Usuario

// no va a pregumtar nada de esto
@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDataBase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db_proyecto"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}