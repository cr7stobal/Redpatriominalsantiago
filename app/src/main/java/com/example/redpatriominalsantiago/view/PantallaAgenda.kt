package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.Evento
import com.example.redpatriominalsantiago.model.eventosEjemplo
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.BordeSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion

@Composable
fun PantallaAgenda(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
    ) {
        // titulo e iconos de arriba
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = "Agenda cultural",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                Icon(imageVector = Icons.Filled.FilterList, contentDescription = null)
            }
        }

        // BOTONES DE FILTRO
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Filtro("Esta semana", Icons.Filled.Check, true)
            Filtro("Cerca de mí", Icons.Filled.LocationOn, false)
            Filtro("Música sacra", Icons.Filled.MusicNote, false)
        }

        // Lista Que Se Puede Deslizar
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // aviso en tiempo real
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Crema)
                    .padding(12.dp)
            ) {
                Icon(imageVector = Icons.Filled.Campaign, contentDescription = null, tint = Burdeo)
                Text(
                    text = "Aviso en tiempo real: Capilla San Rafael abrirá hoy en horario especial de 15:00 a 17:00.",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            // AGRUPA LOS EVENTOS POR FECHA
            eventosEjemplo.groupBy { it.fecha }.forEach { (fecha, lista) ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = fecha, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                lista.forEach { evento ->
                    Spacer(modifier = Modifier.height(10.dp))
                    TarjetaEvento(evento)
                }
            }
        }

        BarraInferior(Routes.AGENDA, navController)
    }
}

@Composable
private fun Filtro(texto: String, icono: ImageVector, seleccionado: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (seleccionado) Crema else Color.White)
            .border(1.dp, if (seleccionado) Dorado else BordeSuave, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(imageVector = icono, contentDescription = null, tint = Burdeo, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = texto, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TarjetaEvento(evento: Evento) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, BordeSuave, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(RosaSeleccion)
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Text(
                text = evento.dia,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Burdeo
            )
            Text(text = evento.mes, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Burdeo)
        }
        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
            Text(text = evento.titulo, fontWeight = FontWeight.Bold)
            Text(text = evento.lugar, fontSize = 13.sp, color = Color.Gray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = " ${evento.hora} · ${evento.distancia}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}