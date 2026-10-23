package com.example.redpatriominalsantiago.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "usuario")  // si no se pone nombre, queda con el nombre de la data class (Usuario)
data class Usuario (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val mail: String,
    val pass: String
)