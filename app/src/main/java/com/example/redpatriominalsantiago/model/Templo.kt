package com.example.redpatriominalsantiago.model

// Datos de un templo de la red patrimonial (todos ficticios)
data class Templo(
    val id: Int,
    val nombre: String,
    val comuna: String,
    val estado: String,       // "Abierto", "Cerrado" o "En celebración"
    val horario: String,      // por ejemplo: "Cierra 19:00"
    val distancia: String,    // por ejemplo: "1,2 km · 15 min a pie"
    val direccion: String,
    val historia: String,
    val anio: String,         // año de construcción
    val estilo: String,       // estilo arquitectónico
    val guias: Int,           // guías voluntarios disponibles
    val mapaX: Int,           // posición del pin en el mapa dibujado (en dp)
    val mapaY: Int,
    val minutosVisita: Int = 30   // duración sugerida de la visita (si no se indica, 30 min)
)
