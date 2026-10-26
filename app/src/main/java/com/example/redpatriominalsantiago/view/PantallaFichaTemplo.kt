package com.example.redpatriominalsantiago.view

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.ui.theme.BlancoSuave
import com.example.redpatriominalsantiago.ui.theme.BordeSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.viewmodel.FichaViewModel

// Figma 05 · Ficha patrimonial del templo
// Recibe el id del templo que viene en la ruta (por ejemplo "ficha/1")
@Composable
fun PantallaFichaTemplo(
    navController: NavController,
    idTemplo: Int,
    fichaViewModel: FichaViewModel = viewModel()
) {
    val templo = fichaViewModel.obtenerTemplo(idTemplo)

    // Si el id no existe, se muestra un aviso en vez de que la app se caiga
    if (templo == null) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text("No se encontró el templo")
        }
        return
    }

    // verticalScroll: si el contenido no cabe, se puede desplazar hacia abajo
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Cabecera(
            onVolver = { navController.popBackStack() }   // vuelve a la pantalla anterior
        )

        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            // Nombre del templo
            Text(templo.nombre, fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 28.sp)
            Spacer(modifier = Modifier.height(12.dp))

            // Estado y horario
            Row(verticalAlignment = Alignment.CenterVertically) {
                EtiquetaEstado(templo.estado)   // la misma etiqueta del mapa
                Spacer(modifier = Modifier.width(8.dp))
                Text(templo.horario, fontSize = 12.sp, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Dirección
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(templo.direccion, fontSize = 13.sp, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Botones "Cómo llegar" y "Agregar a ruta", mitad y mitad
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        // Pendiente: abrir indicaciones para llegar
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cómo llegar")
                }
                Button(
                    onClick = {
                        // Pendiente: agregar a la ruta (pantalla 07)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Crema, contentColor = Color(0xFF4A3700)),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(Icons.Filled.Route, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar a ruta")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Pestañas
            Pestanas(
                elegida = fichaViewModel.pestana,
                onElegir = { fichaViewModel.cambiarPestana(it) }
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Contenido según la pestaña elegida
            when (fichaViewModel.pestana) {
                "Historia" -> {
                    Text(templo.historia, fontSize = 14.sp, lineHeight = 21.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DatoTemplo(templo.anio, "Año", Modifier.weight(1f))
                        DatoTemplo(templo.estilo, "Estilo", Modifier.weight(1f))
                        // "1 guía" en singular, "2 guías" en plural
                        val textoGuias = if (templo.guias == 1) "1 guía" else "${templo.guias} guías"
                        DatoTemplo(textoGuias, "Disponibles", Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    TarjetaEvento()
                }
                "Horarios" -> {
                    Text("Lunes a viernes: 09:00 – 19:00", fontSize = 14.sp)
                    Text("Sábado: 10:00 – 14:00", fontSize = 14.sp)
                    Text("Domingo: misas 10:00 y 12:00", fontSize = 14.sp)
                    Text("Hoy: ${templo.horario}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
                "Eventos" -> {
                    TarjetaEvento()
                }
                "Guías" -> {
                    Text("${templo.guias} guías voluntarios disponibles en este recinto.", fontSize = 14.sp)
                    Text("Las visitas guiadas se solicitan al encargado del templo.", fontSize = 14.sp, color = Color.DarkGray)
                }
            }
        }
    }
}

// "Foto" simulada con degradado, botón volver, botón agregar y contador de fotos
@Composable
fun Cabecera(onVolver: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Brush.horizontalGradient(listOf(Color(0xFF9B3A45), Color(0xFFD9B56A))))
    ) {
        // Ícono de iglesia al centro, semitransparente
        Icon(
            Icons.Filled.Church,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier
                .align(Alignment.Center)
                .size(84.dp)
        )
        // Botón volver (arriba a la izquierda)
        BotonRedondo(
            onClick = onVolver,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
        }
        // Botón agregar (arriba a la derecha)
        BotonRedondo(
            onClick = {
                // Pendiente: agregar a favoritos o a la ruta
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Agregar")
        }
        // Contador de fotos (abajo a la derecha)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Icon(Icons.Filled.Image, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("1 / 8", color = Color.White, fontSize = 12.sp)
        }
    }
}

// Botón blanco y redondo, con sombra. Lo de adentro (el ícono) se pasa como "contenido".
@Composable
fun BotonRedondo(onClick: () -> Unit, modifier: Modifier = Modifier, contenido: @Composable () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(40.dp)
            .shadow(4.dp, CircleShape)
            .background(Color.White, CircleShape)
            .clickable { onClick() }
    ) {
        contenido()
    }
}

// Las 4 pestañas: la elegida en burdeo con una rayita debajo
@Composable
fun Pestanas(elegida: String, onElegir: (String) -> Unit) {
    val opciones = listOf("Historia", "Horarios", "Eventos", "Guías")
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            opciones.forEach { opcion ->
                val seleccionada = opcion == elegida
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onElegir(opcion) }
                        .padding(top = 6.dp)
                ) {
                    Text(
                        text = opcion,
                        fontSize = 14.sp,
                        fontWeight = if (seleccionada) FontWeight.SemiBold else FontWeight.Medium,
                        color = if (seleccionada) Burdeo else Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .size(width = 44.dp, height = 3.dp)
                            .background(if (seleccionada) Burdeo else Color.Transparent, RoundedCornerShape(2.dp))
                    )
                }
            }
        }
        HorizontalDivider(color = BordeSuave)
    }
}

// Cuadro con un dato del templo (por ejemplo "1912" / "Año")
@Composable
fun DatoTemplo(valor: String, etiqueta: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(BlancoSuave, RoundedCornerShape(12.dp))
            .padding(10.dp)
    ) {
        Text(valor, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Burdeo)
        Text(etiqueta, fontSize = 11.sp, color = Color.DarkGray)
    }
}

// Tarjeta crema con el próximo evento del templo
@Composable
fun TarjetaEvento() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Crema, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .background(Dorado, CircleShape)
        ) {
            Icon(Icons.Filled.MusicNote, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("Concierto de música sacra", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text("Sáb 03 oct · 19:30 · Entrada liberada", fontSize = 12.sp, color = Color.DarkGray)
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null)
    }
}
