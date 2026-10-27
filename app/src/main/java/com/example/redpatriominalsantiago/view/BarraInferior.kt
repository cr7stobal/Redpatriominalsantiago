package com.example.redpatriominalsantiago.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion

@Composable
fun BarraInferior(seleccionado: String, navController: NavController) {
    // cambia de pantalla sin apilar la misma pantalla muchas veces
    val irA = { ruta: String ->
        navController.navigate(ruta) {
            popUpTo(Routes.MAPA)
            launchSingleTop = true
        }
    }

    // colores de la barra: el elegido va en rosado y burdeo
    val colores = NavigationBarItemDefaults.colors(
        indicatorColor = RosaSeleccion,
        selectedIconColor = Burdeo,
        selectedTextColor = Burdeo
    )

    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = seleccionado == Routes.MAPA,
            onClick = { irA(Routes.MAPA) },
            icon = { Icon(Icons.Filled.Map, contentDescription = null) },
            label = { Text("Mapa") },
            colors = colores
        )
        NavigationBarItem(
            selected = seleccionado == Routes.AGENDA,
            onClick = { irA(Routes.AGENDA) },
            icon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null) },
            label = { Text("Agenda") },
            colors = colores
        )
        NavigationBarItem(
            selected = seleccionado == Routes.RUTAS,
            onClick = { irA(Routes.RUTAS) },
            icon = { Icon(Icons.Filled.Route, contentDescription = null) },
            label = { Text("Rutas") },
            colors = colores
        )
        NavigationBarItem(
            selected = seleccionado == Routes.PERFIL, // cambié false por esta comparación para que Perfil se marque cuando estás en esa pantalla
            onClick = { irA(Routes.PERFIL) }, // cambié el println por la navegación a la pantalla de perfil
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("Perfil") },
            colors = colores
        )
    }
}