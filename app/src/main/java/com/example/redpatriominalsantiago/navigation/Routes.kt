package com.example.redpatriominalsantiago.navigation

object Routes {
    const val PRESENTACION = "presentacion"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val GPS = "gps"
    const val MAPA = "mapa"
    const val AGENDA = "agenda"
    const val BUSCAR = "buscar"
    const val DETALLE = "detalle/{id}"
    fun detalleDe(id: Int) = "detalle/$id"
    const val RUTAS = "rutas"
    const val RUTA = "ruta/{id}"
    fun rutaDe(id: Int) = "ruta/$id"
    const val PERFIL = "perfil" // agregué la ruta de la pantalla de perfil
}