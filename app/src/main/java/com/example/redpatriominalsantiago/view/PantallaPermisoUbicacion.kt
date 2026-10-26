package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion

// Permiso de ubicación (GPS)
@Composable
fun PantallaPermisoUbicacion(
    navController: NavController   // te lleva de una sala a otra.
) {
    // Columna principal: ocupa toda la pantalla con el fondo crema
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .systemBarsPadding()   // evita que el contenido quede bajo la hora y la batería
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Círculo rosado con el pin del mapa
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(160.dp)
                .background(RosaSeleccion, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = Burdeo,
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Título y explicación
        Text(
            text = "Activa tu ubicación",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "La app necesita el GPS de tu teléfono para mostrarte la red de templos a tu alrededor.",
            fontSize = 15.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Los 3 beneficios: cada uno es un ícono y un texto
        Beneficio(Icons.Filled.LocationOn, "Ver templos, museos y eventos cercanos en el mapa")
        Beneficio(Icons.Filled.Schedule, "Calcular tiempos estimados de traslado")
        Beneficio(Icons.Filled.Route, "Trazar tus rutas patrimoniales en terreno")

               // Ocupatodo el espacio libre y empuja los botones hacia abajo
        Spacer(modifier = Modifier.weight(1f))

        // Botón principal
        Button(
            onClick = {
                // Pendiente: aquí se pedirá el permiso de GPS cuando lo veamos en clases ( proxima mejora evaluación 3? ).
                // Por ahora solo avanza al mapa
                navController.navigate(Routes.MAPA)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Burdeo,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.MyLocation,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Permitir ubicación")
        }

        // Botón secundario (AHORA NO)
        TextButton(
            onClick = {
                navController.navigate(Routes.MAPA)   // va al mapa sin pedir el GPS
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ahora no", color = Burdeo)
        }

        // Aviso legal al final para evitar problemas legales jaja
        Text(
            text = "Tu ubicación solo se utiliza para visualizar el mapa y no se comparte.(Leyes 19.628 y 21.719).",
            fontSize = 11.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

// Una fila de la lista de beneficios. Se reutiliza 3 veces, solo cambian el ícono y el texto. para reutilizar codigo
@Composable
fun Beneficio(icono: ImageVector, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .background(RosaSeleccion, CircleShape)
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = Burdeo,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(text = texto, fontSize = 15.sp)
    }
}
