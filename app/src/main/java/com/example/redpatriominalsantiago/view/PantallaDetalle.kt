package com.example.redpatriominalsantiago.view

import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.MiRuta
import com.example.redpatriominalsantiago.model.eventosEjemplo
import com.example.redpatriominalsantiago.model.lugaresEjemplo
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.BeigeIcono
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.ui.theme.Fondo

@Composable
fun PantallaDetalle(navController: NavController, id: Int) {
    // Lugar A Mostrar
    val lugar = lugaresEjemplo.find { it.id == id } ?: lugaresEjemplo[0]
    val context = LocalContext.current
    // pestana elegida: 0 historia, 1 horarios, 2 eventos, 3 guias
    var pestana by remember { mutableStateOf(0) }
    val enRuta = MiRuta.lugares.contains(lugar.id)
    val eventosLugar = eventosEjemplo.filter { it.lugar.contains(lugar.nombre) }

    // AGREGA O QUITA EL LUGAR DE LA RUTA
    val alternarRuta = {
        if (enRuta) MiRuta.lugares.remove(lugar.id) else MiRuta.lugares.add(lugar.id)
        val mensaje = if (enRuta) "Quitado de tu ruta" else "Agregado a tu ruta"
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        println("CR7: $mensaje: ${lugar.nombre}")
    }

    // abre la app de mapas del telefono
    val comoLlegar = {
        println("CR7: Boton Como llegar presionado: ${lugar.nombre}")
        try {
            val uri = Uri.parse("geo:0,0?q=" + Uri.encode("${lugar.nombre}, ${lugar.comuna}"))
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (e: Exception) {
            Toast.makeText(context, "No hay una app de mapas instalada", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
    ) {
        // Encabezado Con Boton Volver Y Boton +
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Brush.horizontalGradient(listOf(Burdeo, Dorado)))
        ) {
            Icon(
                imageVector = Icons.Filled.Church,
                contentDescription = null,
                tint = Crema,
                modifier = Modifier.size(90.dp).align(Alignment.Center)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .statusBarsPadding()
                    .padding(12.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        println("CR7: Boton volver presionado")
                        navController.popBackStack()
                    }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, tint = Burdeo)
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
                    .padding(12.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { alternarRuta() }
            ) {
                Icon(
                    imageVector = if (enRuta) Icons.Filled.Check else Icons.Filled.Add,
                    contentDescription = null,
                    tint = Burdeo
                )
            }
        }

        // CONTENIDO DESLIZABLE
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = lugar.nombre,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(8.dp))

            // estado y horario
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (lugar.abierto) Color(0xFFDDF0DF) else Color(0xFFE6E6E6))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (lugar.abierto) "Abierto" else "Cerrado",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (lugar.abierto) Color(0xFF2E7D32) else Color.DarkGray
                    )
                }
                Text(
                    text = lugar.resumenHorario,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                )
            }

            // Direccion
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = lugar.direccion,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            // BOTONES COMO LLEGAR Y RUTA
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { comoLlegar() },
                    colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cómo llegar")
                }
                Button(
                    onClick = { alternarRuta() },
                    colors = ButtonDefaults.buttonColors(containerColor = Crema, contentColor = Burdeo),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (enRuta) Icons.Filled.Check else Icons.Filled.Route,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (enRuta) "Quitar de ruta" else "Agregar a ruta")
                }
            }

            // pestanas para cambiar el contenido
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                listOf("Historia", "Horarios", "Eventos", "Guías").forEachIndexed { i, nombre ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { pestana = i }
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = nombre,
                            fontWeight = if (pestana == i) FontWeight.Bold else FontWeight.Normal,
                            color = if (pestana == i) Burdeo else Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(if (pestana == i) Burdeo else Color.Transparent)
                        )
                    }
                }
            }

            // Contenido Segun La Pestana
            Spacer(modifier = Modifier.height(12.dp))
            when (pestana) {
                0 -> {
                    Text(text = lugar.historia)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DatoCard(lugar.anio, "Año", Modifier.weight(1f))
                        DatoCard(lugar.estilo, "Estilo", Modifier.weight(1f))
                        DatoCard("${lugar.guias.size} guías", "Disponibles", Modifier.weight(1f))
                    }
                    // PROXIMO EVENTO DEL LUGAR
                    val proximo = eventosLugar.firstOrNull()
                    if (proximo != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Crema)
                                .clickable {
                                    println("CR7: Evento presionado: ${proximo.titulo}")
                                    navController.navigate(Routes.AGENDA)
                                }
                                .padding(12.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.size(40.dp).clip(CircleShape).background(Dorado)
                            ) {
                                Icon(imageVector = Icons.Filled.MusicNote, contentDescription = null, tint = Color.White)
                            }
                            Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                                Text(text = proximo.titulo, fontWeight = FontWeight.Bold)
                                Text(text = "${proximo.dia} ${proximo.mes} · ${proximo.hora}", fontSize = 12.sp)
                            }
                            Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
                        }
                    }
                }
                1 -> lugar.horarios.forEach {
                    Text(text = it, modifier = Modifier.padding(bottom = 8.dp))
                }
                2 -> {
                    if (eventosLugar.isEmpty()) {
                        Text(text = "No hay eventos programados", color = Color.Gray)
                    }
                    eventosLugar.forEach {
                        TarjetaEvento(it)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                else -> lugar.guias.forEach {
                    Text(text = it, modifier = Modifier.padding(bottom = 8.dp))
                }
            }
        }
    }
}

// caja con un dato grande y su nombre
@Composable
fun DatoCard(valor: String, etiqueta: String, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BeigeIcono)
            .padding(vertical = 12.dp)
    ) {
        Text(text = valor, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, color = Burdeo)
        Text(text = etiqueta, fontSize = 12.sp, color = Color.Gray)
    }
}