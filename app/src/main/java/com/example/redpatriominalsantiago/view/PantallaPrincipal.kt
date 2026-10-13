package com.example.redpatriominalsantiago.view

import androidx.activity.compose.LocalActivity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.redpatriominalsantiago.R
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.ui.theme.DoradoContenedor
import com.example.redpatriominalsantiago.ui.theme.Lora
import com.example.redpatriominalsantiago.ui.theme.RedPatriominalSantiagoTheme
import kotlinx.coroutines.launch

private data class PaginaIntro(
    @param:DrawableRes val icono: Int,
    @param:StringRes val titulo: Int,
    @param:StringRes val subtitulo: Int,
    @param:StringRes val descripcion: Int
)

private val paginas = listOf(
    PaginaIntro(
        R.drawable.ic_church,
        R.string.intro_1_titulo,
        R.string.intro_1_subtitulo,
        R.string.intro_1_descripcion
    ),
    PaginaIntro(
        R.drawable.ic_pin,
        R.string.intro_2_titulo,
        R.string.intro_2_subtitulo,
        R.string.intro_2_descripcion
    ),
    PaginaIntro(
        R.drawable.ic_route,
        R.string.intro_3_titulo,
        R.string.intro_3_subtitulo,
        R.string.intro_3_descripcion
    )
)

// Figma · 01 Splash, como introducción de tres páginas que se deslizan.
// "Siguiente" avanza de página y en la última abre la bienvenida.
@Composable
fun PantallaPrincipal(onContinuar: () -> Unit, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { paginas.size })
    val scope = rememberCoroutineScope()
    IconosDeSistemaClaros()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Burdeo)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Páginas que se deslizan hacia los lados
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { indice ->
            ContenidoPagina(paginas[indice])
        }

        // Botón + indicadores + pie de página
        Column(
            modifier = Modifier.padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilledTonalButton(
                onClick = {
                    if (pagerState.currentPage < paginas.lastIndex) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        onContinuar()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                    .height(48.dp)
            ) {
                Text(stringResource(R.string.intro_siguiente))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(paginas.size) { i ->
                    val activo = pagerState.currentPage == i
                    Box(
                        Modifier
                            .size(8.dp)
                            .background(
                                Color.White.copy(alpha = if (activo) 1f else 0.5f),
                                CircleShape
                            )
                    )
                }
            }
            Text(
                text = stringResource(R.string.intro_pie),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun ContenidoPagina(pagina: PaginaIntro) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(112.dp)
                .background(Dorado, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(pagina.icono),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }
        Text(
            text = stringResource(pagina.titulo),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(pagina.subtitulo),
            fontFamily = Lora,
            fontStyle = FontStyle.Italic,
            fontSize = 22.sp,
            color = DoradoContenedor,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(pagina.descripcion),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.85f),
            textAlign = TextAlign.Center,
            modifier = Modifier.width(260.dp)
        )
    }
}

// El fondo burdeo necesita la hora y los íconos de sistema en blanco; al salir vuelven a oscuro
@Composable
private fun IconosDeSistemaClaros() {
    val window = LocalActivity.current?.window ?: return
    DisposableEffect(window) {
        val barras = WindowCompat.getInsetsController(window, window.decorView)
        barras.isAppearanceLightStatusBars = false
        barras.isAppearanceLightNavigationBars = false
        onDispose {
            barras.isAppearanceLightStatusBars = true
            barras.isAppearanceLightNavigationBars = true
        }
    }
}

@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun PreviewPantallaPrincipal() {
    RedPatriominalSantiagoTheme {
        PantallaPrincipal(onContinuar = {})
    }
}
