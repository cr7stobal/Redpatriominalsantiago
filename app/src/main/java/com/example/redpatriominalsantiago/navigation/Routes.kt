package com.example.redpatriominalsantiago.navigation

object Routes {  // Creador de pantallas exquisito
    const val PRESENTACION = "presentacion"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PERMISO_UBICACION = "permiso_ubicacion"   // GPS
    const val MAPA = "mapa"                             // inicio del flujo Turista
    const val FICHA = "ficha/{id}"                      // {id} se reemplaza por el id del templo, ej: "ficha/1"
    const val AGENDA = "agenda"                         // 06 · Agenda cultural
}