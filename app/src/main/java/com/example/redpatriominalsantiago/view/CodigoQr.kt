package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

// Dibuja un código QR a partir de un texto.
// ZXing calcula la "matriz" de cuadritos (negro o blanco) y Canvas la pinta.
@Composable
fun CodigoQr(contenido: String, tamano: Dp, color: Color = Color.Black) {
    // remember: la matriz se calcula una sola vez por cada contenido, no en cada redibujo
    val matriz = remember(contenido) {
        QRCodeWriter().encode(contenido, BarcodeFormat.QR_CODE, 0, 0)
    }

    Canvas(modifier = Modifier.size(tamano)) {
        val lado = size.width / matriz.width     // tamaño de cada cuadrito
        for (x in 0 until matriz.width) {
            for (y in 0 until matriz.height) {
                if (matriz.get(x, y)) {          // true = cuadrito negro
                    drawRect(
                        color = color,
                        topLeft = Offset(x * lado, y * lado),
                        size = Size(lado, lado)
                    )
                }
            }
        }
    }
}
