package com.example.redpatriominalsantiago.repository

import com.example.redpatriominalsantiago.model.Evento

// Repository de eventos: el único lugar desde donde se obtienen los eventos
object EventoRepository {

    private val eventos = listOf(
        Evento(
            id = 1,
            titulo = "Concierto de música sacra",
            lugar = "Parroquia Santa Clara del Valle",
            comuna = "San Bernardo",
            dia = "Sábado 03 de octubre", numeroDia = "03", mes = "OCT",
            hora = "19:30", distancia = "1,2 km",
            categoria = "Música sacra",
            idTemplo = 1
        ),
        Evento(
            id = 2,
            titulo = "Exposición: Arte colonial en la periferia",
            lugar = "Museo Diocesano Sur",
            comuna = "La Florida",
            dia = "Sábado 03 de octubre", numeroDia = "03", mes = "OCT",
            hora = "10:00–18:00", distancia = "6,8 km",
            categoria = "Exposición",
            idTemplo = 3
        ),
        Evento(
            id = 3,
            titulo = "Fiesta de San Francisco y bendición",
            lugar = "Capilla San Rafael",
            comuna = "Maipú",
            dia = "Domingo 04 de octubre", numeroDia = "04", mes = "OCT",
            hora = "11:00", distancia = "9,1 km",
            categoria = "Fiesta",
            idTemplo = 2
        ),
        Evento(
            id = 4,
            titulo = "Visita guiada: campanarios de adobe",
            lugar = "Templo del Buen Pastor",
            comuna = "Puente Alto",
            dia = "Domingo 04 de octubre", numeroDia = "04", mes = "OCT",
            hora = "16:00", distancia = "12 km",
            categoria = "Visita guiada",
            idTemplo = 4
        )
    )

    fun obtenerEventos(): List<Evento> {
        return eventos
    }
}
