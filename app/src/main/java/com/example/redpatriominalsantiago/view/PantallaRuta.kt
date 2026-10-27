package com.example.redpatriominalsantiago.view

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.MiRuta
import com.example.redpatriominalsantiago.model.RutasGuardadas
import com.example.redpatriominalsantiago.model.TRASLADO_MIN
import com.example.redpatriominalsantiago.model.VISITA_MIN
import com.example.redpatriominalsantiago.model.kmRuta
import com.example.redpatriominalsantiago.model.lugaresEjemplo
import com.example.redpatriominalsantiago.model.minutosRuta
import com.example.redpatriominalsantiago.model.textoKm
import com.example.redpatriominalsantiago.model.textoTiempo
import com.example.redpatriominalsantiago.ui.theme.BeigeIcono
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo

@Composable
fun PantallaRuta(navController: NavController, id: Int) {
    val context = LocalContext.current
    // si id es 0 es una ruta nueva, si no se busca la ruta guardada
    val rutaGuardada = RutasGuardadas.rutas.find { it.id == id }

    var nombre by remember { mutableStateOf(rutaGuardada?.nombre ?: "") }
    var guia by remember { mutableStateOf(rutaGuardada?.guia ?: false) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    // ids de las paradas: una ruta nueva parte con los lugares agregados desde el detalle
    val paradas = remember {
        mutableStateListOf<Int>().apply { addAll(rutaGuardada?.paradas ?: MiRuta.lugares.toList()) }
    }
    // los lugares completos de cada parada
    val lugares = paradas.mapNotNull { idParada -> lugaresEjemplo.find { it.id == idParada } }

    // valida y guarda la ruta
    val guardar = {
        if (nombre.isBlank()) {
            Toast.makeText(context, "Escribe un nombre para la ruta", Toast.LENGTH_SHORT).show()
            println("CR7: No se pudo guardar la ruta, falta el nombre")
        } else if (paradas.size < 2) {
            Toast.makeText(context, "Agrega al menos 2 paradas", Toast.LENGTH_SHORT).show()
            println("CR7: No se pudo guardar la ruta, faltan paradas")
        } else {
            RutasGuardadas.guardar(id, nombre, paradas.toList(), guia)
            if (id == 0) MiRuta.lugares.clear()
            Toast.makeText(context, "Ruta guardada", Toast.LENGTH_SHORT).show()
            println("CR7: Ruta guardada: $nombre")
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
    ) {
        // barra de arriba
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
            Text(
                text = if (id == 0) "Nueva ruta patrimonial" else "Editar ruta",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // contenido que se puede deslizar
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            MapaRuta(lugares.size)
            Spacer(modifier = Modifier.height(12.dp))

            // resumen
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatoCard("${lugares.size}", "paradas", Modifier.weight(1f))
                DatoCard(textoKm(kmRuta(lugares.size)), "recorrido", Modifier.weight(1f))
                DatoCard(textoTiempo(minutosRuta(lugares.size)), "tiempo total", Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la ruta") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // lista de paradas
            Text(text = "Paradas", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            if (lugares.isEmpty()) {
                Text(text = "Aún no hay paradas, usa el botón + Parada", color = Color.Gray)
            }
            lugares.forEachIndexed { i, lugar ->
                // desde la segunda parada se muestra el tiempo de traslado
                if (i > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 10.dp, top = 6.dp, bottom = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(text = " $TRASLADO_MIN min en transporte", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(32.dp).clip(CircleShape).background(Burdeo)
                    ) {
                        Text(text = "${i + 1}", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                        Text(text = lugar.nombre, fontWeight = FontWeight.Bold)
                        Text(text = "Visita $VISITA_MIN min · ${lugar.comuna}", fontSize = 13.sp, color = Color.Gray)
                    }
                    IconButton(
                        onClick = {
                            println("CR7: Parada quitada: ${lugar.nombre}")
                            paradas.remove(lugar.id)
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            // guía voluntario
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(BeigeIcono)
                    .padding(12.dp)
            ) {
                Icon(imageVector = Icons.Filled.Group, contentDescription = null, tint = Burdeo)
                Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                    Text(text = "Solicitar guía voluntario", fontWeight = FontWeight.Bold)
                    Text(text = "Sujeto a disponibilidad de cada recinto", fontSize = 12.sp, color = Color.Gray)
                }
                Switch(
                    checked = guia,
                    onCheckedChange = { guia = it },
                    colors = SwitchDefaults.colors(checkedTrackColor = Burdeo)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // botones fijos abajo
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            OutlinedButton(
                onClick = { mostrarDialogo = true },
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Parada")
            }
            Button(
                onClick = { guardar() },
                colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
                modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Guardar ruta")
            }
        }
    }

    // ventana para elegir una parada nueva
    if (mostrarDialogo) {
        val disponibles = lugaresEjemplo.filter { it.id !in paradas }
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Agregar parada") },
            text = {
                Column {
                    if (disponibles.isEmpty()) {
                        Text("Ya agregaste todos los lugares")
                    }
                    disponibles.forEach { lugar ->
                        Text(
                            text = "${lugar.nombre} · ${lugar.comuna}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    println("CR7: Parada agregada: ${lugar.nombre}")
                                    paradas.add(lugar.id)
                                    mostrarDialogo = false
                                }
                                .padding(vertical = 10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarDialogo = false }) { Text("Cerrar") }
            }
        )
    }
}

// mapa quieto con puntos numerados unidos por una línea punteada, "cantidad" es cuántas paradas hay
@Composable
private fun MapaRuta(cantidad: Int) {
    // posición (x, y) en dp de cada punto: se reparten a lo ancho y suben y bajan
    val puntos = (0 until cantidad).map { i ->
        val x = if (cantidad == 1) 180 else 40 + i * 280 / (cantidad - 1)
        val y = if (i % 2 == 0) 140 else 70
        Pair(x, y)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(BeigeIcono)
    ) {
        // fondo del mapa
        Forma(200, 20, 150, 70, Color(0xFFD3E6C0))
        Forma(-80, 150, 560, 30, Color(0xFFBFD9EE), -8f)
        Forma(0, 100, 400, 10, Color.White)
        Forma(130, 0, 10, 220, Color.White)
        Forma(250, 0, 10, 220, Color.White)

        // línea punteada que une un punto con el siguiente
        Canvas(modifier = Modifier.fillMaxSize()) {
            for (i in 0 until puntos.size - 1) {
                drawLine(
                    color = Burdeo,
                    start = Offset(puntos[i].first.dp.toPx(), puntos[i].second.dp.toPx()),
                    end = Offset(puntos[i + 1].first.dp.toPx(), puntos[i + 1].second.dp.toPx()),
                    strokeWidth = 4.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 12f))
                )
            }
        }

        // los círculos numerados
        puntos.forEachIndexed { i, punto ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .offset(x = (punto.first - 14).dp, y = (punto.second - 14).dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Burdeo)
            ) {
                Text(text = "${i + 1}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}