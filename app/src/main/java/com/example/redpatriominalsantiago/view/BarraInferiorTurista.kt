package com.example.redpatriominalsantiago.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.BlancoSuave
import com.example.redpatriominalsantiago.ui.theme.Crema

// Barra inferior del flujo Turista: Mapa · Agenda · Rutas · Perfil
// "seleccionada" indica qué pestaña se marca (por ejemplo "Mapa").
@Composable
fun BarraInferiorTurista(
    navController: NavController,
    seleccionada: String
) {
    // Colores de la pestaña marcada: fondo crema y texto oscuro
    val colores = NavigationBarItemDefaults.colors(
        indicatorColor = Crema,
        selectedIconColor = Color.Black,
        selectedTextColor = Color.Black,
        unselectedIconColor = Color.DarkGray,
        unselectedTextColor = Color.DarkGray
    )

    // Cambia de pestaña sin apilar pantallas:
    // popUpTo(MAPA) deja solo el mapa debajo, así el botón atrás siempre vuelve al mapa,
    // y launchSingleTop evita abrir dos veces la misma pantalla.
    fun irA(ruta: String) {
        navController.navigate(ruta) {
            popUpTo(Routes.MAPA)
            launchSingleTop = true
        }
    }

    NavigationBar(containerColor = BlancoSuave) {
        NavigationBarItem(
            selected = seleccionada == "Mapa",
            onClick = { irA(Routes.MAPA) },
            icon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) },
            label = { Text("Mapa") },
            colors = colores
        )
        NavigationBarItem(
            selected = seleccionada == "Agenda",
            onClick = { irA(Routes.AGENDA) },
            icon = { Icon(Icons.Outlined.CalendarMonth, contentDescription = null) },
            label = { Text("Agenda") },
            colors = colores
        )
        NavigationBarItem(
            selected = seleccionada == "Rutas",
            onClick = { irA(Routes.RUTAS) },
            icon = { Icon(Icons.Outlined.Route, contentDescription = null) },
            label = { Text("Rutas") },
            colors = colores
        )
        NavigationBarItem(
            selected = seleccionada == "Perfil",
            onClick = { irA(Routes.PERFIL) },
            icon = { Icon(Icons.Outlined.Person, contentDescription = null) },
            label = { Text("Perfil") },
            colors = colores
        )
    }
}
