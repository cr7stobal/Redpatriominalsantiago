package com.example.redpatriominalsantiago.navigation

object Routes {  // Creador de pantallas exquisito
    const val PRESENTACION = "presentacion"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val PERMISO_UBICACION = "permiso_ubicacion"   // GPS
    const val MAPA = "mapa"                             // inicio del flujo Turista
    const val FICHA = "ficha/{id}"                      // {id} se reemplaza por el id del templo, ej: "ficha/1"
    const val AGENDA = "agenda"                         // 06 · Agenda cultural
    const val RUTAS = "rutas"                           // 07 · Constructor de ruta
    const val PERFIL = "perfil"                         // perfil del invitado
    const val QR_INVITADO = "qr_invitado"               // pase QR de 10 minutos para los tótems
    const val ESCANER_QR = "escaner_qr"                 // leer el QR de un templo
}