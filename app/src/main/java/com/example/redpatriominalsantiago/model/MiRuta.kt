package com.example.redpatriominalsantiago.model

import androidx.compose.runtime.mutableStateListOf

// guarda los ids de los lugares agregados a la ruta, la lista avisa a la pantalla cuando cambia
object MiRuta {
    val lugares = mutableStateListOf<Int>()
}