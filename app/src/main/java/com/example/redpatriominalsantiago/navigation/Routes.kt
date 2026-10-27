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
    // ARMA LA RUTA DEL DETALLE CON EL ID
    fun detalleDe(id: Int) = "detalle/$id"
    const val RUTAS = "rutas"
    const val RUTA = "ruta/{id}"
    // Arma La Ruta Del Editor Con El Id
    fun rutaDe(id: Int) = "ruta/$id"
    const val PERFIL = "perfil"
}