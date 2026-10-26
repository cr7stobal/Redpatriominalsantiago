package com.example.redpatriominalsantiago.repository

import com.example.redpatriominalsantiago.model.Templo

// Repository: el único lugar desde donde las pantallas obtienen los templos.
// Hoy los datos están escritos aquí; más adelante podrían venir de Room
// y los ViewModels no tendrían que cambiar.
object TemploRepository {

    private val templos = listOf(
        Templo(
            id = 1,
            nombre = "Parroquia Santa Clara del Valle",
            comuna = "San Bernardo",
            estado = "Abierto",
            horario = "Cierra 19:00",
            distancia = "1,2 km · 15 min a pie",
            direccion = "Av. Ejemplo 1234, San Bernardo (dirección ficticia)",
            historia = "Levantada a comienzos del siglo XX por la comunidad rural del sector, conserva un campanario de adobe y un retablo tallado por artesanos locales. Texto ficticio para el prototipo.",
            anio = "1912",
            estilo = "Neocolonial",
            guias = 2,
            mapaX = 94, mapaY = 220
        ),
        Templo(
            id = 2,
            nombre = "Capilla San Rafael",
            comuna = "Maipú",
            estado = "En celebración",
            horario = "Misa 12:00",
            distancia = "9,1 km · 20 min en auto",
            direccion = "Calle Ficticia 456, Maipú (dirección ficticia)",
            historia = "Pequeña capilla de barrio construida por los vecinos, conocida por sus fiestas patronales y su coro juvenil. Texto ficticio para el prototipo.",
            anio = "1955",
            estilo = "Moderno",
            guias = 1,
            mapaX = 230, mapaY = 184
        ),
        Templo(
            id = 3,
            nombre = "Museo Diocesano Sur",
            comuna = "La Florida",
            estado = "Abierto",
            horario = "Cierra 18:00",
            distancia = "6,8 km · 18 min en metro",
            direccion = "Pasaje Inventado 78, La Florida (dirección ficticia)",
            historia = "Reúne imágenes religiosas, vestimentas y documentos de las parroquias del sector sur. Texto ficticio para el prototipo.",
            anio = "1998",
            estilo = "Contemporáneo",
            guias = 3,
            mapaX = 184, mapaY = 438
        ),
        Templo(
            id = 4,
            nombre = "Templo del Buen Pastor",
            comuna = "Puente Alto",
            estado = "Cerrado",
            horario = "Abre mañana 10:00",
            distancia = "12 km · 30 min en metro",
            direccion = "Av. Imaginaria 900, Puente Alto (dirección ficticia)",
            historia = "Templo de ladrillo con una torre que se ve desde gran parte de la comuna. Texto ficticio para el prototipo.",
            anio = "1934",
            estilo = "Neogótico",
            guias = 0,
            mapaX = 284, mapaY = 108
        ),
        Templo(
            id = 5,
            nombre = "Parroquia San Miguel",
            comuna = "San Miguel",
            estado = "Cerrado",
            horario = "Abre 16:00",
            distancia = "4,5 km · 12 min en auto",
            direccion = "Calle Supuesta 321, San Miguel (dirección ficticia)",
            historia = "Parroquia histórica del barrio, con vitrales restaurados por la comunidad. Texto ficticio para el prototipo.",
            anio = "1921",
            estilo = "Neoclásico",
            guias = 1,
            mapaX = 54, mapaY = 388
        )
    )

    // Todos los templos
    fun obtenerTemplos(): List<Templo> {
        return templos
    }

    // Un templo según su id. Devuelve null si no existe.
    fun buscarPorId(id: Int): Templo? {
        return templos.find { it.id == id }
    }
}
