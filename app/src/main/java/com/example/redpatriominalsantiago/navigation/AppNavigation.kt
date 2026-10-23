package com.example.redpatriominalsantiago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.redpatriominalsantiago.view.PantallaIniciarSesion
import com.example.redpatriominalsantiago.view.PantallaPrincipal
import com.example.redpatriominalsantiago.view.PantallaRegistro

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.PRESENTACION
    ){
        composable(Routes.PRESENTACION) { PantallaPrincipal(navController) }
        composable(Routes.LOGIN) {PantallaIniciarSesion(navController = navController)}
        composable(Routes.REGISTER) { PantallaRegistro(navController) }
    }
}