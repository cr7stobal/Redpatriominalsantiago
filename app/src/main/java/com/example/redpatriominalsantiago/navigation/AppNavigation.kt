package com.example.redpatriominalsantiago.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.redpatriominalsantiago.model.Sesion
import com.example.redpatriominalsantiago.view.PantallaAgenda
import com.example.redpatriominalsantiago.view.PantallaBuscar
import com.example.redpatriominalsantiago.view.PantallaDetalle
import com.example.redpatriominalsantiago.view.PantallaGps
import com.example.redpatriominalsantiago.view.PantallaIniciarSesion
import com.example.redpatriominalsantiago.view.PantallaMapa
import com.example.redpatriominalsantiago.view.PantallaPerfil // agregué
import com.example.redpatriominalsantiago.view.PantallaPrincipal
import com.example.redpatriominalsantiago.view.PantallaRegistro
import com.example.redpatriominalsantiago.view.PantallaRuta
import com.example.redpatriominalsantiago.view.PantallaRutas

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val inicio = remember {
        Sesion.cargar(context)
        if (Sesion.iniciada) Routes.MAPA else Routes.PRESENTACION
    }

    NavHost(
        navController = navController,
        startDestination = inicio
    ){
        composable(Routes.PRESENTACION) { PantallaPrincipal(navController) }
        composable(Routes.LOGIN) { PantallaIniciarSesion(navController = navController) }
        composable(Routes.REGISTER) { PantallaRegistro(navController) }
        composable(Routes.GPS) { PantallaGps(navController) }
        composable(Routes.MAPA) { PantallaMapa(navController) }
        composable(Routes.AGENDA) { PantallaAgenda(navController) }
        composable(Routes.BUSCAR) { PantallaBuscar(navController) }
        composable(
            route = Routes.DETALLE,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: 1
            PantallaDetalle(navController, id)
        }
        composable(Routes.RUTAS) { PantallaRutas(navController) }
        composable(
            route = Routes.RUTA,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entrada ->
            val id = entrada.arguments?.getInt("id") ?: 0
            PantallaRuta(navController, id)
        }
        composable(Routes.PERFIL) { PantallaPerfil(navController) } // agregué la pantalla de perfil
    }
}