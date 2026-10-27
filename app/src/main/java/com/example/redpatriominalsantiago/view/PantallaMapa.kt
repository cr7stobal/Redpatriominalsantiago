package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // agregué este import para poder usar .clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.Sesion
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.BeigeIcono
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion

@Composable
fun PantallaMapa(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeIcono) // este fondo hace de "mapa"
    ) {
        // mapa quieto: parques, río y calles
        Forma(210, 150, 160, 110, Color(0xFFD3E6C0)) // parque
        Forma(-10, 340, 130, 130, Color(0xFFD3E6C0)) // parque
        Forma(-80, 290, 560, 36, Color(0xFFBFD9EE), -15f) // río, inclinado
        Forma(0, 190, 400, 12, Color.White) // calle horizontal
        Forma(0, 430, 400, 12, Color.White) // calle horizontal
        Forma(80, 130, 12, 400, Color.White) // calle vertical
        Forma(250, 130, 12, 400, Color.White) // calle vertical
        Forma(-100, 250, 600, 8, Color.White, 25f) // calle diagonal

        // pines de ejemplo sobre el "mapa"
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            tint = Burdeo,
            modifier = Modifier.size(40.dp).align(Alignment.Center).offset(x = (-80).dp, y = (-60).dp)
        )
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            tint = Dorado,
            modifier = Modifier.size(40.dp).align(Alignment.Center).offset(x = 70.dp, y = (-130).dp)
        )
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = null,
            tint = Color(0xFF2E7D32),
            modifier = Modifier.size(40.dp).align(Alignment.Center).offset(x = 90.dp, y = 20.dp)
        )

        // parte de arriba: nombre del usuario y buscador
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            Text(
                text = "Hola, ${Sesion.nombre}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Burdeo
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White)
                    // CAMBIO 1: agregué este clickable para que el buscador se pueda apretar y abra la pantalla de búsqueda
                    // va entre .background y .padding, así toda la barra responde al toque
                    .clickable {
                        println("CR7: Buscador presionado")
                        navController.navigate(Routes.BUSCAR)
                    }
                    .padding(12.dp)
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                Text(
                    text = "Buscar templos o eventos",
                    color = Color.Gray,
                    modifier = Modifier.weight(1f).padding(start = 12.dp)
                )
                // circulo con la primera letra del nombre
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(36.dp).clip(CircleShape).background(Burdeo)
                ) {
                    Text(
                        text = Sesion.nombre.take(1).uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // parte de abajo: tarjeta "Cerca de ti" y barra de navegacion
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    text = "Cerca de ti",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(text = "12 recintos · 3 eventos hoy", fontSize = 12.sp, color = Color.Gray)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(RosaSeleccion)
                    // CAMBIO 2: agregué este clickable para que la tarjeta abra el detalle de la parroquia (el lugar con id 1)
                    // va entre .background y .padding, igual que en el buscador
                    .clickable {
                        println("CR7: Tarjeta Cerca de ti presionada")
                        navController.navigate(Routes.detalleDe(1))
                    }
                    .padding(12.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp)).background(Burdeo)
                ) {
                    Icon(imageVector = Icons.Filled.Church, contentDescription = null, tint = Color.White)
                }
                Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                    Text(text = "Parroquia Santa Clara del Valle", fontWeight = FontWeight.Bold)
                    Text(text = "Abierto · Cierra 19:00", fontSize = 12.sp, color = Color(0xFF2E7D32))
                    Text(text = "1,2 km · 15 min a pie", fontSize = 12.sp, color = Color.Gray)
                }
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(12.dp))

            BarraInferior(Routes.MAPA, navController)
        }
    }
}

// dibuja un rectángulo de color en la posición (x, y), con un tamaño y un giro opcional, sirve para armar el mapa
@Composable
fun Forma(x: Int, y: Int, ancho: Int, alto: Int, color: Color, giro: Float = 0f) {
    Box(
        modifier = Modifier
            .offset(x = x.dp, y = y.dp)
            .size(width = ancho.dp, height = alto.dp)
            .rotate(giro)
            .background(color)
    )
}