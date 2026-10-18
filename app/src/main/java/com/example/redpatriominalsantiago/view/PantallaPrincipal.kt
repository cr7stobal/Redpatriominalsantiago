package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Map
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.BlancoSuave
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado

private data class PaginaIntro(
    val icono: ImageVector,
    val titulo: String,
    val subtitulo: String,
    val descripcion: String
)

private val paginas = listOf(
    PaginaIntro(
        Icons.Filled.Church,
        "Red Patrimonial",
        "de Santiago",
        "Descubre el patrimonio sagrado de la arquidiócesis, más allá de la Plaza de Armas."
    ),
    PaginaIntro(
        Icons.Filled.Map,
        "Explora el mapa",
        "templos, museos y archivos",
        "Encuentra en terreno los templos cercanos y revisa sus horarios en tiempo real."
    ),
    PaginaIntro(
        Icons.Filled.Explore,
        "Planifica tu ruta",
        "para colegios y turistas",
        "Arma recorridos con paradas, tiempos estimados y guías voluntarios."
    )
)

// ---------- Pantalla principal ----------
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaPrincipal(
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = { paginas.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Burdeo)
    ) {
        // Páginas que se deslizan hacia los lados
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { indice ->
            ContenidoPagina(paginas[indice])
        }

        // Botón Omitir (después del pager para que quede encima)
        TextButton(
            onClick = {navController.navigate(Routes.LOGIN)},
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

        // Indicadores + pie de página
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(paginas.size) { i ->
                    val activo = pagerState.currentPage == i
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(
                                if (activo) Color.White else Color.White.copy(alpha = 0.4f)
                            )
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            Text(
                text = "Área de Cultura · Arzobispado de Santiago",
                color = BlancoSuave.copy(alpha = 0.7f),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun ContenidoPagina(pagina: PaginaIntro) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .padding(bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(Dorado),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = pagina.icono,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(72.dp)
            )
        }

        Spacer(Modifier.height(28.dp))

        Text(
            text = pagina.titulo,
            color = Color.White,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = pagina.subtitulo,
            color = Crema,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            fontSize = 26.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = pagina.descripcion,
            color = BlancoSuave,
            fontSize = 17.sp,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}
