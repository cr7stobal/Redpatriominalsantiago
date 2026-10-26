package com.example.redpatriominalsantiago.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.ui.theme.BlancoSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Fondo
import com.example.redpatriominalsantiago.viewmodel.QrViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

// Lector de QR de los templos. Basado en el QrScreen del profesor:
// si el código es de un templo de la red, abre su ficha.
@Composable
fun PantallaEscanerQr(
    navController: NavController,
    qrViewModel: QrViewModel = viewModel()
) {
    val context = LocalContext.current

    // Último contenido leído (StateFlow -> estado de Compose)
    val contenidoQR by qrViewModel.contenidoQR.collectAsStateWithLifecycle()

    // Opciones: solo leer códigos QR
    val opciones = remember {
        GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }

    // El lector de Google (abre su propia pantalla de cámara)
    val scanner = remember(context, opciones) {
        GmsBarcodeScanning.getClient(context, opciones)
    }

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
            Text("Escanear templo", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Icon(Icons.Filled.QrCodeScanner, contentDescription = null, tint = Burdeo, modifier = Modifier.size(96.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Lector de Código QR", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Escanea el código QR que está en la entrada de cada parroquia para ver su ficha.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scanner.startScan()
                        .addOnSuccessListener { barcode ->
                            val contenido = barcode.rawValue.orEmpty()
                            qrViewModel.guardarQR(contenido)

                            // Si es el QR de un templo, se abre su ficha
                            val id = qrViewModel.obtenerIdTemplo(contenido)
                            if (id != null) {
                                navController.navigate("ficha/$id")
                            } else {
                                Toast.makeText(context, "Este código no es de un templo de la red", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnCanceledListener {
                            // Escaneo cancelado: no se hace nada
                        }
                        .addOnFailureListener { error ->
                            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                        }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Burdeo, contentColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(Icons.Filled.QrCodeScanner, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Escanear QR")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Último código leído
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BlancoSuave, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text("Contenido del QR:", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contenidoQR.ifEmpty { "Todavía no se ha escaneado ningún código" },
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}
