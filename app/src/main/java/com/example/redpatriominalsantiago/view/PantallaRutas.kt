package com.example.redpatriominalsantiago.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.Templo
import com.example.redpatriominalsantiago.ui.theme.AzulRio
import com.example.redpatriominalsantiago.ui.theme.BlancoSuave
import com.example.redpatriominalsantiago.ui.theme.BordeSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.FondoMapa
import com.example.redpatriominalsantiago.ui.theme.VerdeParque
import com.example.redpatriominalsantiago.viewmodel.RutaViewModel

// Figma 07 · Constructor de ruta patrimonial
@Composable
fun PantallaRutas(
    navController: NavController,
    rutaViewModel: RutaViewModel = viewModel()
) {
    val paradas = rutaViewModel.paradas
    val contexto = LocalContext.current   // necesario para mostrar el aviso (Toast)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Barra superior: volver, título y editar
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 4.dp)) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Text("Nueva ruta patrimonial", fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            IconButton(onClick = {
                // Pendiente: cambiar el nombre de la ruta
            }) {
                Icon(Icons.Outlined.Edit, contentDescription = "Editar")
            }
        }

        // Parte central que se puede desplazar
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            MapaRuta(cantidadParadas = paradas.size)

            // Resumen: se recalcula cada vez que cambian las paradas
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
            ) {
                DatoTemplo("${paradas.size}", "paradas", Modifier.weight(1f))
                DatoTemplo("${rutaViewModel.cantidadComunas()}", "comunas", Modifier.weight(1f))
                DatoTemplo(rutaViewModel.tiempoTotal(), "tiempo de visita", Modifier.weight(1f))
            }

            Text(
                "Paradas",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
            )

            if (paradas.isEmpty()) {
                Text(
                    "Tu ruta está vacía. Agrega paradas con \"+ Parada\" o desde la ficha de un templo.",
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // forEachIndexed entrega la posición (0, 1, 2...) y el templo
            paradas.forEachIndexed { indice, templo ->
                FilaParada(
                    numero = indice + 1,
                    templo = templo,
                    onQuitar = { rutaViewModel.quitarParada(templo) }
                )
                // Línea que une con la siguiente parada (menos después de la última)
                if (indice < paradas.size - 1) {
                    Box(
                        modifier = Modifier
                            .padding(start = 29.dp)
                            .size(width = 2.dp, height = 14.dp)
                            .background(BordeSuave)
                    )
                }
            }

            // Tarjeta del guía voluntario con interruptor
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(BlancoSuave, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Icon(Icons.Outlined.Groups, contentDescription = null, tint = Burdeo)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Solicitar guía voluntario", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text("Sujeto a disponibilidad de cada recinto", fontSize = 11.sp, color = Color.DarkGray)
                }
                Switch(
                    checked = rutaViewModel.solicitarGuia,
                    onCheckedChange = { rutaViewModel.cambiarSolicitarGuia(it) },
                    colors = SwitchDefaults.colors(checkedTrackColor = Burdeo)
                )
            }
        }

        // Botones de abajo, siempre visibles
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
        ) {
            OutlinedButton(
                onClick = { rutaViewModel.agregarSiguienteParada() },
                enabled = rutaViewModel.quedanTemplos(),     // gris si ya están todos los templos
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, tint = Burdeo, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Parada", color = Burdeo)
            }
            Button(
                onClick = {
                    // Pendiente: guardar la ruta en la base de datos (Room)
                    Toast.makeText(contexto, "Ruta guardada", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar ruta")
            }
        }
    }
}

// Mapa pequeño dibujado, con un círculo numerado por cada parada
@Composable
fun MapaRuta(cantidadParadas: Int) {
    // Posiciones fijas (en dp) donde se dibuja cada número
    val posiciones = listOf(46 to 146, 136 to 76, 226 to 116, 291 to 41, 90 to 30)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(FondoMapa)
    ) {
        Box(modifier = Modifier.offset(x = 200.dp, y = 14.dp).size(width = 122.dp, height = 40.dp).background(VerdeParque, RoundedCornerShape(18.dp)))
        Box(modifier = Modifier.offset(y = 120.dp).fillMaxWidth().height(20.dp).background(AzulRio))
        Box(modifier = Modifier.offset(y = 45.dp).fillMaxWidth().height(10.dp).background(Color.White))
        Box(modifier = Modifier.offset(y = 160.dp).fillMaxWidth().height(8.dp).background(Color.White))
        Box(modifier = Modifier.offset(x = 100.dp).width(10.dp).fillMaxHeight().background(Color.White))
        Box(modifier = Modifier.offset(x = 245.dp).width(8.dp).fillMaxHeight().background(Color.White))

        // Un círculo numerado por parada (máximo 5, las posiciones disponibles)
        for (i in 0 until minOf(cantidadParadas, posiciones.size)) {
            val (x, y) = posiciones[i]
            NumeroParada(numero = i + 1, modifier = Modifier.offset(x = x.dp, y = y.dp))
        }
    }
}

// Círculo burdeo con el número de la parada
@Composable
fun NumeroParada(numero: Int, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(28.dp)
            .background(Burdeo, CircleShape)
            .border(2.dp, Color.White, CircleShape)
    ) {
        Text("$numero", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

// Una fila de la lista: número, nombre, duración y botón para quitar
@Composable
fun FilaParada(numero: Int, templo: Templo, onQuitar: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        NumeroParada(numero)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(templo.nombre, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text("Visita ${templo.minutosVisita} min · ${templo.comuna}", fontSize = 12.sp, color = Color.DarkGray)
        }
        IconButton(onClick = onQuitar) {
            Icon(Icons.Filled.Close, contentDescription = "Quitar parada", tint = Color.DarkGray)
        }
    }
}
