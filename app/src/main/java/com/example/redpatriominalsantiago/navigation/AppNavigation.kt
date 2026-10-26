package com.example.redpatriominalsantiago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.redpatriominalsantiago.view.PantallaAgenda
import com.example.redpatriominalsantiago.view.PantallaEscanerQr
import com.example.redpatriominalsantiago.view.PantallaFichaTemplo
import com.example.redpatriominalsantiago.view.PantallaIniciarSesion
import com.example.redpatriominalsantiago.view.PantallaMapa
import com.example.redpatriominalsantiago.view.PantallaPerfil
import com.example.redpatriominalsantiago.view.PantallaPermisoUbicacion
import com.example.redpatriominalsantiago.view.PantallaQrInvitado
import com.example.redpatriominalsantiago.view.PantallaPrincipal
import com.example.redpatriominalsantiago.view.PantallaRegistro
import com.example.redpatriominalsantiago.view.PantallaRutas

@Composable
fun AppNavigation() { // mapa de la app
    val navController = rememberNavController()  // crea el control remoto que cambia de pantalla y guarda el historial.

    NavHost(  // Se encargará de consolidar y permitir la navegación entre las pantallas. (ej: Login y Home)
        navController = navController, // indicar de que manera se comportara, tipo remember
        startDestination = Routes.PRESENTACION
    ){
        composable(Routes.PRESENTACION) { PantallaPrincipal(navController) }  // ejemplo de las 3: en esta ruta crearas tal cosa.
        composable(Routes.LOGIN) {PantallaIniciarSesion(navController = navController)}  // el navcontroller, le pasa a cada pantalla,
        // para que puedan navegar. Por ejemplo, el botón “Omitir” de la intro.
        composable(Routes.REGISTER) { PantallaRegistro(navController) }
        composable(Routes.PERMISO_UBICACION) { PantallaPermisoUbicacion(navController) }    // gps
        composable(Routes.MAPA) { PantallaMapa(navController) }    // 04 · Mapa principal (Turista)
        composable(Routes.FICHA) { entrada ->                     // 05 · Ficha del templo
            // Se lee el {id} que viene en la ruta. Si falta, se usa 0 (no existe).
            val id = entrada.arguments?.getString("id")?.toIntOrNull() ?: 0
            PantallaFichaTemplo(navController, id)
        }
        composable(Routes.AGENDA) { PantallaAgenda(navController) }    // 06 · Agenda cultural
        composable(Routes.RUTAS) { PantallaRutas(navController) }      // 07 · Constructor de ruta
        composable(Routes.PERFIL) { PantallaPerfil(navController) }    // perfil del invitado
        composable(Routes.QR_INVITADO) { PantallaQrInvitado(navController) }   // pase QR
        composable(Routes.ESCANER_QR) { PantallaEscanerQr(navController) }     // lector QR de templos
    }
}