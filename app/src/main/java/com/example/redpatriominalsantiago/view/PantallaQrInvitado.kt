package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.ui.theme.BlancoSuave
import com.example.redpatriominalsantiago.ui.theme.BordeSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.viewmodel.QrInvitadoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Pase QR temporal para que el invitado acceda a los tótems de las parroquias.
// Paso 1: ingresar RUT o pasaporte. Paso 2: mostrar el QR, válido por 10 minutos.
@Composable
fun PantallaQrInvitado(
    navController: NavController,
    qrViewModel: QrInvitadoViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Barra superior
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 4.dp)) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Text("Pase QR de acceso", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            // Si todavía no hay código, se muestra el formulario; si ya hay, el QR
            if (qrViewModel.contenidoQr == null) {
                FormularioDocumento(qrViewModel)
            } else {
                PaseGenerado(qrViewModel)
            }
        }
    }
}

// Paso 1: elegir RUT o pasaporte y escribir el documento
@Composable
fun FormularioDocumento(qrViewModel: QrInvitadoViewModel) {
    Icon(Icons.Filled.QrCode2, contentDescription = null, tint = Burdeo, modifier = Modifier.size(72.dp))
    Spacer(modifier = Modifier.height(12.dp))
    Text("Accede a los tótems de las parroquias", fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        "Como invitado, necesitas identificarte con tu RUT o pasaporte. Generaremos un código QR válido por 10 minutos.",
        fontSize = 14.sp,
        color = Color.DarkGray,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(20.dp))

    // Elegir el tipo de documento (se reutilizan los chips del mapa)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("RUT", "Pasaporte").forEach { tipo ->
            FiltroChip(
                texto = tipo,
                seleccionado = qrViewModel.tipoDocumento == tipo,
                onClick = { qrViewModel.cambiarTipo(tipo) },
                mostrarPunto = false
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    val esRut = qrViewModel.tipoDocumento == "RUT"
    OutlinedTextField(
        value = qrViewModel.documento,
        onValueChange = { qrViewModel.cambiarDocumento(it) },
        label = { Text(if (esRut) "RUT" else "N° de pasaporte") },
        placeholder = { Text(if (esRut) "12.345.678-5" else "F12345678") },
        leadingIcon = { Icon(Icons.Filled.Badge, contentDescription = null) },
        isError = qrViewModel.error != null,           // borde rojo si hay error
        supportingText = {
            // Texto de ayuda debajo del campo: el error o una pista
            Text(qrViewModel.error ?: if (esRut) "Con o sin puntos, con guion y dígito verificador" else "Entre 6 y 9 letras o números")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Burdeo, focusedLabelColor = Burdeo),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = { qrViewModel.generarQr() },
        enabled = qrViewModel.documento.isNotBlank(),
        colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text("Generar código QR")
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        "Tu documento solo se usa para generar el pase y no se comparte (Leyes 19.628 y 21.719).",
        fontSize = 11.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center
    )
}

// Paso 2: el QR con la cuenta regresiva
@Composable
fun PaseGenerado(qrViewModel: QrInvitadoViewModel) {
    val vencido = qrViewModel.estaVencido()
    val horaVence = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(qrViewModel.horaVencimiento))

    Text(
        if (vencido) "Código expirado" else "Muestra este código en el tótem",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = if (vencido) Color.Red else Color.Black
    )
    Spacer(modifier = Modifier.height(16.dp))

    // Tarjeta blanca con el QR. Si venció, el QR se ve gris.
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, BordeSuave, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        CodigoQr(
            contenido = qrViewModel.contenidoQr ?: "",
            tamano = 240.dp,
            color = if (vencido) Color.LightGray else Color.Black
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    // Cuenta regresiva
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(if (vencido) Color(0xFFFDE2E2) else BlancoSuave, RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(Icons.Filled.Timer, contentDescription = null, tint = if (vencido) Color.Red else Burdeo)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            if (vencido) "Venció a las $horaVence" else "Válido por ${qrViewModel.tiempoRestanteTexto()}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (vencido) Color.Red else Burdeo
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    // Datos del pase
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlancoSuave, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text("Perfil: Invitado", fontSize = 14.sp)
        Text("${qrViewModel.tipoDocumento}: ${qrViewModel.documentoMostrado}", fontSize = 14.sp)
        Text("Vence a las $horaVence", fontSize = 14.sp)
    }
    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = { qrViewModel.nuevoCodigo() },
        colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text("Generar nuevo código")
    }
}
