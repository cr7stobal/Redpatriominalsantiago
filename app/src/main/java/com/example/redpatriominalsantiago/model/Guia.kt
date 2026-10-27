package com.example.redpatriominalsantiago.model

// un guía voluntario, "codigo" es el texto que viene dentro de su QR
data class Guia(
    val codigo: String,
    val nombre: String,
    val funcion: String,
    val rango: String,
    val recinto: String
)

// guías de ejemplo (datos ficticios)
val guiasEjemplo = listOf(
    Guia("GUIA-001", "María Pérez", "Recorre la parroquia y explica su historia y su campanario de adobe", "Guía senior", "Parroquia Santa Clara del Valle"),
    Guia("GUIA-002", "Camila Soto", "Guía las visitas del museo y las exposiciones de arte colonial", "Guía voluntaria", "Museo Diocesano Sur"),
    Guia("GUIA-003", "Pedro Lagos", "Acompaña a grupos pequeños y cuenta la historia de las fiestas patronales", "Guía aprendiz", "Capilla San Rafael"),
    Guia("GUIA-004", "Ana Fuentes", "Coordina a los guías y organiza las visitas de colegios", "Coordinadora de guías", "Templo del Buen Pastor")
)