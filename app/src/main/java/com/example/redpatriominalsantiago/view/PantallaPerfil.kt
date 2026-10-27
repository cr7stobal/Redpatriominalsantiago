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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.model.Guia
import com.example.redpatriominalsantiago.model.MiRuta
import com.example.redpatriominalsantiago.model.RutasGuardadas
import com.example.redpatriominalsantiago.model.Sesion
import com.example.redpatriominalsantiago.model.guiasEjemplo
import com.example.redpatriominalsantiago.model.minutosRuta
import com.example.redpatriominalsantiago.model.textoTiempo
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

@Composable
fun PantallaPerfil(navController: NavController) {
    val context = LocalContext.current
    var guiaEscaneado by remember { mutableStateOf<Guia?>(null) }
    var mensajeQr by remember { mutableStateOf("") }

    // abre el escáner de QR de Google, cuando lee un código busca al guía que tiene ese código
    val escanearQr = {
        println("CR7: Boton Escanear QR presionado")
        GmsBarcodeScanning.getClient(context)
            .startScan()
            .addOnSuccessListener { barcode ->
                val texto = barcode.rawValue ?: ""
                val guia = guiasEjemplo.find { it.codigo == texto }
                if (guia != null) {
                    guiaEscaneado = guia
                    mensajeQr = ""
                    println("CR7: QR leido, guia: ${guia.nombre}")
                } else {
                    guiaEscaneado = null
                    mensajeQr = "Código no reconocido: $texto"
                    println("CR7: QR leido pero no es un guia: $texto")
                }
            }
            .addOnCanceledListener { println("CR7: Escaneo de QR cancelado") }
            .addOnFailureListener { e ->
                mensajeQr = "No se pudo abrir el escáner"
                println("CR7: Error al escanear QR: ${e.message}")
            }
    }

    // borra la sesión guardada y vuelve a la pantalla del comienzo, el usuario sigue en la base de datos
    val cerrarSesion = {
        Sesion.cerrar(context)
        MiRuta.lugares.clear()
        println("CR7: Sesion cerrada")
        navController.navigate(Routes.PRESENTACION) {
            popUpTo(Routes.MAPA) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
    ) {
        // encabezado con la inicial y el nombre
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(56.dp).clip(CircleShape).background(Burdeo)
            ) {
                Text(
                    text = Sesion.nombre.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(text = Sesion.nombre, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = "Sesión iniciada", fontSize = 13.sp, color = Color.Gray)
            }
        }

        // contenido que se puede deslizar
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // resumen
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatoCard("${RutasGuardadas.rutas.size}", "rutas guardadas", Modifier.weight(1f))
                DatoCard("${MiRuta.lugares.size}", "en tu próxima ruta", Modifier.weight(1f))
            }

            // lector de QR
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Guía voluntario", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { escanearQr() },
                colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.QrCodeScanner, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Escanear QR de guía")
            }
            if (mensajeQr != "") {
                Text(text = mensajeQr, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            // credencial del guía escaneado
            val guiaActual = guiaEscaneado
            if (guiaActual != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Crema)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "IDENTIFICADOR DE GUÍA",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Burdeo
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(48.dp).clip(CircleShape).background(Dorado)
                        ) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = null, tint = Color.White)
                        }
                        Column(modifier = Modifier.padding(start = 12.dp)) {
                            Text(text = guiaActual.nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(
                                text = guiaActual.rango,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Burdeo
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = guiaActual.funcion)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Recinto: ${guiaActual.recinto}", fontSize = 13.sp, color = Color.DarkGray)
                    Text(text = "Código: ${guiaActual.codigo}", fontSize = 12.sp, color = Color.Gray)
                }
            }

            // historial de rutas
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Historial de rutas", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            if (RutasGuardadas.rutas.isEmpty()) {
                Text(text = "Aún no tienes rutas guardadas", color = Color.Gray)
            }
            RutasGuardadas.rutas.forEach { ruta ->
                val n = ruta.paradas.size
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .clickable {
                            println("CR7: Ruta del historial presionada: ${ruta.nombre}")
                            navController.navigate(Routes.rutaDe(ruta.id))
                        }
                        .padding(12.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Route, contentDescription = null, tint = Burdeo)
                    Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                        Text(text = ruta.nombre, fontWeight = FontWeight.Bold)
                        Text(
                            text = "$n paradas · ${textoTiempo(minutosRuta(n))}",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                    Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }

            // cerrar sesión
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = { cerrarSesion() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Burdeo),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar sesión")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        BarraInferior(Routes.PERFIL, navController)
    }
}