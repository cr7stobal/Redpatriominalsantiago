package com.example.redpatriominalsantiago.model

data class Evento(
    val fecha: String,
    val dia: String,
    val mes: String,
    val titulo: String,
    val lugar: String,
    val hora: String,
    val distancia: String
)

// lista de eventos de ejemplo (datos ficticios), el "lugar" empieza con el nombre del lugar para poder relacionarlos
val eventosEjemplo = listOf(
    Evento("Sábado 31 de octubre", "31", "OCT", "Concierto de música sacra", "Parroquia Santa Clara del Valle · San Bernardo", "19:30", "1,2 km"),
    Evento("Sábado 31 de octubre", "31", "OCT", "Exposición: Arte colonial en la periferia", "Museo Diocesano Sur · La Florida", "10:00–18:00", "6,8 km"),
    Evento("Domingo 01 de noviembre", "01", "NOV", "Misa de Todos los Santos", "Capilla San Rafael · Maipú", "11:00", "9,1 km"),
    Evento("Domingo 01 de noviembre", "01", "NOV", "Visita guiada: campanarios de adobe", "Templo del Buen Pastor · Puente Alto", "16:00", "12 km"),
    Evento("Lunes 02 de noviembre", "02", "NOV", "Misa de difuntos", "Parroquia Santa Clara del Valle · San Bernardo", "12:00", "1,2 km"),
    Evento("Lunes 02 de noviembre", "02", "NOV", "Taller de restauración de retablos", "Museo Diocesano Sur · La Florida", "15:00", "6,8 km")
)