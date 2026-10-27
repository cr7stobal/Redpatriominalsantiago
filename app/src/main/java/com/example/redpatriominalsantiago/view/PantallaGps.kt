package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.BeigeIcono
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion

@Composable
fun PantallaGps(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // circulo con el icono de ubicacion (reemplaza al mapa)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(BeigeIcono)
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = Burdeo,
                modifier = Modifier.size(72.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Activa tu ubicación",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "La app necesita el GPS de tu teléfono para mostrarte la red de templos a tu alrededor.",
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        ItemGps(Icons.Filled.LocationOn, "Ver templos, museos y eventos cercanos en el mapa")
        Spacer(modifier = Modifier.height(12.dp))
        ItemGps(Icons.Filled.AccessTime, "Calcular tiempos estimados de traslado")
        Spacer(modifier = Modifier.height(12.dp))
        ItemGps(Icons.Filled.Route, "Trazar tus rutas patrimoniales en terreno")

        // empuja los botones hacia abajo
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                println("CR7: Boton Permitir ubicacion presionado")
                // cambié navController.navigate(Routes.MAPA) por esto: borra presentación, login y gps del historial, así el botón atrás en el mapa cierra la app y no vuelve al login
                navController.navigate(Routes.MAPA) {
                    popUpTo(Routes.PRESENTACION) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Burdeo,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.MyLocation,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Permitir ubicación")
        }

        TextButton(
            onClick = {
                println("CR7: Boton Ahora no presionado")
                // cambié navController.navigate(Routes.MAPA) por esto: borra presentación, login y gps del historial, así el botón atrás en el mapa cierra la app y no vuelve al login
                navController.navigate(Routes.MAPA) {
                    popUpTo(Routes.PRESENTACION) { inclusive = true }
                }
            }
        ) {
            Text("Ahora no", color = Burdeo, fontWeight = FontWeight.Bold)
        }

        Text(
            text = "Tu ubicación solo se usa para el mapa y no se comparte (Leyes 19.628 y 21.719).",
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

// una fila con icono en circulo rosado y un texto al lado
@Composable
private fun ItemGps(icono: ImageVector, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(RosaSeleccion)
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = Burdeo
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = texto)
    }
}