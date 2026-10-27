package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado


data class SlideInfo(
    val icono: ImageVector,
    val titulo: String,
    val subtitulo: String,
    val descripcion: String
)

private val slides = listOf(
    SlideInfo(
        icono = Icons.Filled.Church,
        titulo = "Red Patrimonial",
        subtitulo = "de Santiago",
        descripcion = "Descubre el patrimonio sagrado de la arquidiócesis, más allá de la Plaza de Armas."
    ),
    SlideInfo(
        icono = Icons.Filled.Map,
        titulo = "Recorre la ciudad",
        subtitulo = "templos y capillas",
        descripcion = "Encuentra iglesias, capillas y monumentos históricos cerca de ti con un mapa fácil de usar."
    ),
    SlideInfo(
        icono = Icons.Filled.Favorite,
        titulo = "Guarda tus favoritos",
        subtitulo = "tu ruta personal",
        descripcion = "Marca los lugares que más te gusten y arma tu propio recorrido patrimonial."
    )
)

@Composable
fun PantallaPrincipal(
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = { slides.size })
    val esUltima = pagerState.currentPage == slides.size - 1

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Burdeo)
    ) {
        // Carrusel
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pagina ->
            SlideContenido(slides[pagina])
        }

        // Indicadores y botón inferior
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(slides.size) { i ->
                    val activo = pagerState.currentPage == i
                    Box(
                        modifier = Modifier
                            .size(if (activo) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (activo) Dorado else Crema.copy(alpha = 0.5f))
                    )
                }
            }

            if (esUltima) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.navigate(Routes.LOGIN) },
                    colors = ButtonDefaults.buttonColors(containerColor = Dorado)
                ) {
                    Text("Comenzar", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Botón Omitir (al final para que quede encima)
        TextButton(
            onClick = { navController.navigate(Routes.LOGIN) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(end = 8.dp, top = 8.dp)
        ) {
            Text(
                "Omitir",
                color = Crema,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun SlideContenido(slide: SlideInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(Dorado)
        ) {
            Icon(
                imageVector = slide.icono,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = slide.titulo,
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = slide.subtitulo,
            color = Crema,
            fontSize = 26.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = slide.descripcion,
            color = Crema,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )
    }
}