package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Church
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.eventosEjemplo
import com.example.redpatriominalsantiago.model.lugaresEjemplo
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion

@Composable
fun PantallaBuscar(navController: NavController) {
    var texto by remember { mutableStateOf("") }

    // FILTRA LUGARES Y EVENTOS CON EL TEXTO ESCRITO
    val lugares = lugaresEjemplo.filter {
        it.nombre.contains(texto, ignoreCase = true) || it.comuna.contains(texto, ignoreCase = true)
    }
    val eventos = eventosEjemplo.filter {
        it.titulo.contains(texto, ignoreCase = true) || it.lugar.contains(texto, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // flecha para volver y campo de busqueda
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, tint = Burdeo)
            }
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                placeholder = { Text("Buscar templos o eventos") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 12.dp)
        ) {
            if (lugares.isEmpty() && eventos.isEmpty()) {
                Text(text = "No se encontraron resultados", color = Color.Gray)
            }

            // Lugares Encontrados
            if (lugares.isNotEmpty()) {
                Text(text = "Lugares", fontWeight = FontWeight.Bold, color = Burdeo)
                lugares.forEach { lugar ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .clickable {
                                println("CR7: Lugar presionado: ${lugar.nombre}")
                                navController.navigate(Routes.detalleDe(lugar.id))
                            }
                            .padding(12.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(40.dp).clip(CircleShape).background(RosaSeleccion)
                        ) {
                            Icon(imageVector = Icons.Filled.Church, contentDescription = null, tint = Burdeo)
                        }
                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(text = lugar.nombre, fontWeight = FontWeight.Bold)
                            Text(text = lugar.comuna, fontSize = 13.sp, color = Color.Gray)
                        }
                    }
                }
            }

            // EVENTOS ENCONTRADOS
            if (eventos.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Eventos", fontWeight = FontWeight.Bold, color = Burdeo)
                eventos.forEach { evento ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier.clickable {
                            println("CR7: Evento presionado: ${evento.titulo}")
                            // abre el detalle del lugar del evento
                            val lugar = lugaresEjemplo.find { evento.lugar.contains(it.nombre) }
                            if (lugar != null) navController.navigate(Routes.detalleDe(lugar.id))
                        }
                    ) {
                        TarjetaEvento(evento)
                    }
                }
            }
        }
    }
}