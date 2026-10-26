package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.Templo
import com.example.redpatriominalsantiago.ui.theme.AzulRio
import com.example.redpatriominalsantiago.ui.theme.AzulUsuario
import com.example.redpatriominalsantiago.ui.theme.BlancoSuave
import com.example.redpatriominalsantiago.ui.theme.BordeSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.FondoMapa
import com.example.redpatriominalsantiago.ui.theme.GrisCerrado
import com.example.redpatriominalsantiago.ui.theme.MoradoCelebracion
import com.example.redpatriominalsantiago.ui.theme.VerdeAbierto
import com.example.redpatriominalsantiago.ui.theme.VerdeParque
import com.example.redpatriominalsantiago.viewmodel.MapaViewModel

// Figma 04 · Mapa principal (Turista)
@Composable
fun PantallaMapa(
    navController: NavController,
    mapaViewModel: MapaViewModel = viewModel()
) {
    // Templos que pasan el filtro elegido en los chips
    val templos = mapaViewModel.templosVisibles()

    // Acción para abrir la ficha de un templo: arma la ruta con su id, ej: "ficha/1"
    val abrirFicha: (Int) -> Unit = { id -> navController.navigate("ficha/$id") }

    // Scaffold: la barra inferior abajo y el mapa en el resto de la pantalla
    Scaffold(
        containerColor = Fondo,   // color de fondo, también detrás de la hora
        bottomBar = { BarraInferiorTurista(navController, "Mapa") }
    ) { innerPadding ->
        // Box: pone las capas una encima de otra (mapa, buscador, hoja inferior)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Capa 1: el mapa dibujado con los pines
            MapaDibujado(templos, onAbrirTemplo = abrirFicha)

            // Capa 2: buscador y filtros arriba
            Column(modifier = Modifier.padding(16.dp)) {
                Buscador()
                Spacer(modifier = Modifier.height(12.dp))
                Filtros(
                    filtroElegido = mapaViewModel.filtro,
                    onElegir = { mapaViewModel.cambiarFiltro(it) }
                )
            }

            // Capa 3: botón redondo de "mi ubicación", sobre la hoja inferior
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 200.dp)
                    .size(48.dp)
                    .shadow(4.dp, CircleShape)
                    .background(Color.White, CircleShape)
            ) {
                Icon(Icons.Filled.MyLocation, contentDescription = null, tint = Burdeo)
            }

            // Capa 4: hoja inferior "Cerca de ti"
            HojaCercaDeTi(
                templo = templos.firstOrNull(),   // el más cercano; null si el filtro no deja ninguno
                cantidad = templos.size,
                onAbrirTemplo = abrirFicha,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

// Mapa hecho con cajas de colores: parques, calles, río, tu ubicación y los pines
@Composable
fun MapaDibujado(templos: List<Templo>, onAbrirTemplo: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoMapa)
    ) {
        // Parques (verde)
        Box(
            modifier = Modifier
                .offset(x = 200.dp, y = 48.dp)
                .size(width = 122.dp, height = 138.dp)
                .background(VerdeParque, RoundedCornerShape(18.dp))
        )
        Box(
            modifier = Modifier
                .offset(x = 0.dp, y = 443.dp)
                .size(width = 131.dp, height = 111.dp)
                .background(VerdeParque, RoundedCornerShape(22.dp))
        )

        // Río (celeste)
        Box(
            modifier = Modifier
                .offset(y = 360.dp)
                .fillMaxWidth()
                .height(24.dp)
                .background(AzulRio)
        )

        // Calles horizontales (blancas)
        Box(modifier = Modifier.offset(y = 150.dp).fillMaxWidth().height(10.dp).background(Color.White))
        Box(modifier = Modifier.offset(y = 288.dp).fillMaxWidth().height(5.dp).background(Color.White))
        Box(modifier = Modifier.offset(y = 552.dp).fillMaxWidth().height(8.dp).background(Color.White))

        // Calles verticales (blancas)
        Box(modifier = Modifier.offset(x = 55.dp).width(5.dp).fillMaxHeight().background(Color.White))
        Box(modifier = Modifier.offset(x = 100.dp).width(10.dp).fillMaxHeight().background(Color.White))
        Box(modifier = Modifier.offset(x = 215.dp).width(8.dp).fillMaxHeight().background(Color.White))

        // Tu ubicación: punto azul con un halo
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(x = 148.dp, y = 328.dp)
                .size(44.dp)
                .background(AzulUsuario.copy(alpha = 0.2f), CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .border(3.dp, Color.White, CircleShape)
                    .background(AzulUsuario, CircleShape)
            )
        }

        // Pin dorado de un evento musical
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(x = 150.dp, y = 150.dp)
                .size(34.dp)
                .background(Dorado, CircleShape)
                .border(2.dp, Color.White, CircleShape)
        ) {
            Icon(Icons.Filled.MusicNote, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        }

        // Un pin por cada templo de la lista
        templos.forEach { templo ->
            PinTemplo(templo, onClick = { onAbrirTemplo(templo.id) })
        }
    }
}

// Pin de un templo: ícono del color de su estado y una etiqueta con el nombre
@Composable
fun PinTemplo(templo: Templo, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .offset(x = templo.mapaX.dp, y = templo.mapaY.dp)
            .clickable { onClick() }   // al tocar el pin se abre la ficha
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = templo.nombre,
            tint = colorEstado(templo.estado),
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = templo.nombre,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(6.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

// Color que corresponde a cada estado. Se usa en los pines y en las etiquetas.
fun colorEstado(estado: String): Color {
    return when (estado) {
        "Abierto" -> VerdeAbierto
        "En celebración" -> MoradoCelebracion
        else -> GrisCerrado
    }
}

// Barra de búsqueda blanca con la inicial del perfil (T de Turista)
@Composable
fun Buscador() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .shadow(4.dp, RoundedCornerShape(26.dp))
            .background(Color.White, RoundedCornerShape(26.dp))
            .padding(start = 16.dp, end = 8.dp)
    ) {
        Icon(Icons.Filled.Search, contentDescription = null, tint = Color.DarkGray)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Buscar templos o eventos",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.weight(1f)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .background(Burdeo, CircleShape)
        ) {
            Text("T", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

// Fila de chips de filtro. Se puede deslizar hacia los lados.
@Composable
fun Filtros(filtroElegido: String, onElegir: (String) -> Unit) {
    val opciones = listOf("Todos", "Abierto", "En celebración", "Cerrado")

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        opciones.forEach { opcion ->
            FiltroChip(
                texto = opcion,
                seleccionado = opcion == filtroElegido,
                onClick = { onElegir(opcion) }
            )
        }
    }
}

// Un chip: crema si está elegido, blanco con borde si no.
// mostrarPunto = false sirve para chips que no son estados (por ejemplo, en la Agenda).
@Composable
fun FiltroChip(texto: String, seleccionado: Boolean, onClick: () -> Unit, mostrarPunto: Boolean = true) {
    val forma = RoundedCornerShape(8.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(32.dp)
            .clip(forma)
            .background(if (seleccionado) Crema else Color.White)
            .border(1.dp, if (seleccionado) Crema else BordeSuave, forma)
            .clickable { onClick() }
            .padding(horizontal = 12.dp)
    ) {
        if (seleccionado) {
            Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
        } else if (mostrarPunto && texto != "Todos") {
            // Punto del color del estado
            Box(modifier = Modifier.size(8.dp).background(colorEstado(texto), CircleShape))
            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(texto, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

// Hoja blanca de abajo con el templo más cercano
@Composable
fun HojaCercaDeTi(
    templo: Templo?,
    cantidad: Int,
    onAbrirTemplo: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .background(Color.White, RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            .padding(16.dp)
    ) {
        // Rayita gris de arriba
        Box(
            modifier = Modifier
                .size(width = 32.dp, height = 4.dp)
                .background(BordeSuave, RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Cerca de ti",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(text = "$cantidad recintos", fontSize = 12.sp, color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(10.dp))

        if (templo != null) {
            TarjetaTemplo(templo, onClick = { onAbrirTemplo(templo.id) })
        } else {
            Text("No hay recintos con este filtro", fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

// Tarjeta con foto simulada, nombre, estado y distancia
@Composable
fun TarjetaTemplo(templo: Templo, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BlancoSuave)
            .clickable { onClick() }   // abre la ficha del templo
            .padding(10.dp)
    ) {
        // "Foto" simulada: cuadro burdeo con el ícono de iglesia
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .background(Burdeo, RoundedCornerShape(10.dp))
        ) {
            Icon(Icons.Filled.Church, contentDescription = null, tint = Color.White)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(templo.nombre, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                EtiquetaEstado(templo.estado)
                Spacer(modifier = Modifier.width(6.dp))
                Text(templo.horario, fontSize = 12.sp, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Schedule, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.DarkGray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(templo.distancia, fontSize = 12.sp, color = Color.DarkGray)
            }
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Color.DarkGray)
    }
}

// Etiqueta pequeña del estado: punto y texto del color del estado
@Composable
fun EtiquetaEstado(estado: String) {
    val color = colorEstado(estado)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Box(modifier = Modifier.size(6.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(4.dp))
        Text(estado, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = color)
    }
}
