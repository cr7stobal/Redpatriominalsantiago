package com.example.redpatriominalsantiago.model

data class Lugar(
    val id: Int,
    val nombre: String,
    val comuna: String,
    val direccion: String,
    val abierto: Boolean,
    val resumenHorario: String,
    val historia: String,
    val anio: String,
    val estilo: String,
    val horarios: List<String>,
    val guias: List<String>
)

// Lugares De Ejemplo Con Datos Ficticios
val lugaresEjemplo = listOf(
    Lugar(
        id = 1,
        nombre = "Parroquia Santa Clara del Valle",
        comuna = "San Bernardo",
        direccion = "Av. Ejemplo 1234, San Bernardo (dirección ficticia)",
        abierto = true,
        resumenHorario = "Hoy hasta 19:00 · Apertura especial 16–18 h",
        historia = "Levantada a comienzos del siglo XX por la comunidad rural del sector, conserva un campanario de adobe y un retablo tallado por artesanos locales. Texto ficticio para el prototipo.",
        anio = "1912",
        estilo = "Neocolonial",
        horarios = listOf("Lunes a viernes: 09:00–19:00", "Sábado: 10:00–20:00", "Domingo y festivos: 09:00–14:00"),
        guias = listOf("María Pérez · Español e inglés", "Tomás Rojas · Español")
    ),
    Lugar(
        id = 2,
        nombre = "Museo Diocesano Sur",
        comuna = "La Florida",
        direccion = "Calle Ficticia 450, La Florida (dirección ficticia)",
        abierto = true,
        resumenHorario = "Hoy 10:00–18:00",
        historia = "Reúne imágenes, textiles y objetos litúrgicos de las parroquias del sur de Santiago. Texto ficticio para el prototipo.",
        anio = "1978",
        estilo = "Moderno",
        horarios = listOf("Martes a sábado: 10:00–18:00", "Domingo: 11:00–15:00", "Lunes: cerrado"),
        guias = listOf("Camila Soto · Español e inglés")
    ),
    Lugar(
        id = 3,
        nombre = "Capilla San Rafael",
        comuna = "Maipú",
        direccion = "Pasaje Ejemplo 88, Maipú (dirección ficticia)",
        abierto = false,
        resumenHorario = "Hoy abre 15:00–17:00 (horario especial)",
        historia = "Pequeña capilla de barrio, construida por los vecinos y recordada por sus fiestas patronales. Texto ficticio para el prototipo.",
        anio = "1935",
        estilo = "Colonial",
        horarios = listOf("Sábado y domingo: 10:00–13:00", "Días de fiesta: según aviso"),
        guias = listOf("Pedro Lagos · Español")
    ),
    Lugar(
        id = 4,
        nombre = "Templo del Buen Pastor",
        comuna = "Puente Alto",
        direccion = "Av. Imaginaria 900, Puente Alto (dirección ficticia)",
        abierto = true,
        resumenHorario = "Hoy hasta 18:00",
        historia = "Templo con campanarios de adobe que han resistido varios terremotos. Texto ficticio para el prototipo.",
        anio = "1889",
        estilo = "Colonial",
        horarios = listOf("Lunes a sábado: 09:00–18:00", "Domingo: 08:00–13:00"),
        guias = listOf("Ana Fuentes · Español", "Luis Vega · Español e inglés", "Rosa Díaz · Español")
    )
)