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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.redpatriominalsantiago.repository.RutaRepository
import com.example.redpatriominalsantiago.ui.theme.BlancoSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.ui.theme.GrisCerrado
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion
import com.example.redpatriominalsantiago.ui.theme.VerdeAbierto

// Perfil del invitado: qué puede hacer sin cuenta, qué gana al crear una,
// y el acceso al pase QR (arriba a la derecha).
@Composable
fun PantallaPerfil(navController: NavController) {
    Scaffold(
        containerColor = Fondo,
        bottomBar = { BarraInferiorTurista(navController, "Perfil") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            // Barra superior: título y botón "Generar QR" a la derecha
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Perfil", fontSize = 22.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                FilledTonalButton(
                    onClick = { navController.navigate(Routes.QR_INVITADO) },
                    colors = ButtonDefaults.filledTonalButtonColors(containerColor = Crema, contentColor = Color(0xFF4A3700))
                ) {
                    Icon(Icons.Filled.QrCode2, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Generar QR")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Encabezado del invitado
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(64.dp)
                        .background(RosaSeleccion, CircleShape)
                ) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = Burdeo, modifier = Modifier.size(36.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Invitado", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Explorando sin cuenta", fontSize = 14.sp, color = Color.DarkGray)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Actividad de esta sesión
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatoTemplo("${RutaRepository.paradas.size}", "paradas en tu ruta", Modifier.weight(1f))
                DatoTemplo("Turista", "modo de uso", Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Lo que puede hacer un invitado
            Text("Como invitado puedes", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            FilaPermiso(Icons.Filled.CheckCircle, VerdeAbierto, "Ver el mapa y las fichas de los templos")
            FilaPermiso(Icons.Filled.CheckCircle, VerdeAbierto, "Revisar la agenda cultural")
            FilaPermiso(Icons.Filled.CheckCircle, VerdeAbierto, "Armar una ruta (se pierde al cerrar la app)")
            FilaPermiso(Icons.Filled.CheckCircle, VerdeAbierto, "Generar un pase QR de 10 minutos para los tótems")
            Spacer(modifier = Modifier.height(16.dp))

            // Lo que necesita una cuenta
            Text("Con una cuenta podrás", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            FilaPermiso(Icons.Filled.Lock, GrisCerrado, "Guardar tus rutas patrimoniales")
            FilaPermiso(Icons.Filled.Lock, GrisCerrado, "Agendar visitas guiadas")
            FilaPermiso(Icons.Filled.Lock, GrisCerrado, "Recibir avisos de tus templos favoritos")
            Spacer(modifier = Modifier.height(20.dp))

            // Botones para dejar de ser invitado
            Button(
                onClick = { navController.navigate(Routes.REGISTER) },
                colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Crear cuenta")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { navController.navigate(Routes.LOGIN) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Iniciar sesión", color = Burdeo)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Prototipo académico · datos 100% ficticios",
                fontSize = 11.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
    }
}

// Una fila con ícono y texto (permitido o bloqueado)
@Composable
fun FilaPermiso(icono: ImageVector, colorIcono: Color, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(BlancoSuave, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Icon(icono, contentDescription = null, tint = colorIcono, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(texto, fontSize = 14.sp)
    }
}
