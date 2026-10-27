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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.RutasGuardadas
import com.example.redpatriominalsantiago.model.kmRuta
import com.example.redpatriominalsantiago.model.lugaresEjemplo
import com.example.redpatriominalsantiago.model.minutosRuta
import com.example.redpatriominalsantiago.model.textoKm
import com.example.redpatriominalsantiago.model.textoTiempo
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion

@Composable
fun PantallaRutas(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
    ) {
        Text(
            text = "Mis rutas",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // lista que se puede deslizar
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // el 0 significa "ruta nueva"
            Button(
                onClick = {
                    println("CR7: Boton Nueva ruta presionado")
                    navController.navigate(Routes.rutaDe(0))
                },
                colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Nueva ruta")
            }
            Spacer(modifier = Modifier.height(12.dp))

            if (RutasGuardadas.rutas.isEmpty()) {
                Text(text = "Aún no tienes rutas guardadas", color = Color.Gray)
            }

            RutasGuardadas.rutas.forEach { ruta ->
                val n = ruta.paradas.size
                // nombres de los lugares de la ruta, unidos con flechas (los puntos de referencia)
                val nombres = ruta.paradas
                    .mapNotNull { idParada -> lugaresEjemplo.find { it.id == idParada }?.nombre }
                    .joinToString(" → ")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .clickable {
                            println("CR7: Ruta presionada: ${ruta.nombre}")
                            navController.navigate(Routes.rutaDe(ruta.id))
                        }
                        .padding(12.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(44.dp).clip(CircleShape).background(RosaSeleccion)
                    ) {
                        Icon(imageVector = Icons.Filled.Route, contentDescription = null, tint = Burdeo)
                    }
                    Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                        Text(text = ruta.nombre, fontWeight = FontWeight.Bold)
                        Text(
                            text = "$n paradas · ${textoKm(kmRuta(n))} · ${textoTiempo(minutosRuta(n))}",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                        Text(text = nombres, fontSize = 12.sp, color = Color.Gray, maxLines = 2)
                        if (ruta.guia) {
                            Text(
                                text = "Con guía voluntario",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Burdeo
                            )
                        }
                    }
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }
        }

        BarraInferior(Routes.RUTAS, navController)
    }
}