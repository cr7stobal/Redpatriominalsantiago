package com.example.redpatriominalsantiago.model

// Un evento de la agenda cultural (todos ficticios)
data class Evento(
    val id: Int,
    val titulo: String,
    val lugar: String,        // nombre del templo o museo
    val comuna: String,
    val dia: String,          // por ejemplo: "Sábado 03 de octubre" (se usa para agrupar)
    val numeroDia: String,    // "03"
    val mes: String,          // "OCT"
    val hora: String,         // "19:30"
    val distancia: String,    // "1,2 km"
    val categoria: String,    // "Música sacra", "Exposición", "Fiesta" o "Visita guiada"
    val idTemplo: Int         // para abrir la ficha del templo donde ocurre
)
