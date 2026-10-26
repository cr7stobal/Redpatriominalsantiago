package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.Evento
import com.example.redpatriominalsantiago.ui.theme.BordeSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion
import com.example.redpatriominalsantiago.viewmodel.AgendaViewModel

// Figma 06 · Agenda cultural
@Composable
fun PantallaAgenda(
    navController: NavController,
    agendaViewModel: AgendaViewModel = viewModel()
) {
    // Eventos agrupados por día, ya filtrados por la categoría elegida
    val eventosPorDia = agendaViewModel.eventosPorDia()

    Scaffold(
        containerColor = Fondo,
        topBar = { BarraSuperiorAgenda() },
        bottomBar = { BarraInferiorTurista(navController, "Agenda") }
    ) { innerPadding ->
        // LazyColumn: lista que se desplaza y solo dibuja lo que se ve en pantalla
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // item { }: un elemento suelto de la lista
            item {
                FiltrosAgenda(
                    elegida = agendaViewModel.categoria,
                    onElegir = { agendaViewModel.cambiarCategoria(it) }
                )
            }
            item {
                AvisoTiempoReal("Aviso en tiempo real: Capilla San Rafael abrirá hoy en horario especial de 15:00 a 17:00.")
            }

            // Por cada día: un título y luego sus eventos
            eventosPorDia.forEach { (dia, eventosDelDia) ->
                item {
                    Text(dia, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 4.dp))
                }
                // items(lista) { }: un elemento por cada evento
                items(eventosDelDia) { evento ->
                    TarjetaEventoAgenda(
                        evento = evento,
                        onClick = { navController.navigate("ficha/${evento.idTemplo}") }   // abre la ficha del templo
                    )
                }
            }

            // Si el filtro no deja ningún evento
            if (eventosPorDia.isEmpty()) {
                item {
                    Text("No hay eventos en esta categoría", color = Color.DarkGray)
                }
            }
        }
    }
}

// Barra de arriba: título y dos íconos
@Composable
fun BarraSuperiorAgenda() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Fondo)
            .statusBarsPadding()
            .padding(top = 8.dp, start = 20.dp, end = 12.dp, bottom = 8.dp)
    ) {
        Text("Agenda cultural", fontSize = 22.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        Icon(Icons.Filled.Search, contentDescription = "Buscar", modifier = Modifier.padding(8.dp))
        Icon(Icons.Filled.FilterList, contentDescription = "Filtrar", modifier = Modifier.padding(8.dp))
    }
}

// Chips de categoría (se reutiliza FiltroChip del mapa, sin el punto de color)
@Composable
fun FiltrosAgenda(elegida: String, onElegir: (String) -> Unit) {
    val categorias = listOf("Todos", "Música sacra", "Exposición", "Fiesta", "Visita guiada")
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        categorias.forEach { categoria ->
            FiltroChip(
                texto = categoria,
                seleccionado = categoria == elegida,
                onClick = { onElegir(categoria) },
                mostrarPunto = false
            )
        }
    }
}

// Aviso amarillo con un ícono de altavoz
@Composable
fun AvisoTiempoReal(texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFCEBC6), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Icon(Icons.Filled.Campaign, contentDescription = null, tint = Color(0xFF8A5A00))
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontSize = 12.sp)
    }
}

// Tarjeta de un evento: fecha a la izquierda y datos a la derecha
@Composable
fun TarjetaEventoAgenda(evento: Evento, onClick: () -> Unit) {
    val forma = RoundedCornerShape(16.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(forma)
            .background(Color.White)
            .border(1.dp, BordeSuave, forma)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        // Cuadro rosado con el día y el mes
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(width = 56.dp, height = 64.dp)
                .background(RosaSeleccion, RoundedCornerShape(12.dp))
                .padding(top = 8.dp)
        ) {
            Text(evento.numeroDia, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Burdeo)
            Text(evento.mes, fontSize = 11.sp, color = Burdeo)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(evento.titulo, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text("${evento.lugar} · ${evento.comuna}", fontSize = 12.sp, color = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Schedule, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.DarkGray)
                Spacer(modifier = Modifier.width(4.dp))
                Text("${evento.hora} · ${evento.distancia}", fontSize = 12.sp, color = Color.DarkGray)
            }
        }
    }
}
