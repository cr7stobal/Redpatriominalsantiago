package com.example.redpatriominalsantiago.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Burdeo,
    onPrimary = Color.White,
    primaryContainer = BurdeoContenedor,
    onPrimaryContainer = Burdeo,
    secondary = Dorado,
    onSecondary = Color.White,
    secondaryContainer = DoradoContenedor,
    onSecondaryContainer = SobreDoradoContenedor,
    tertiary = EstadoCelebracion,
    onTertiary = Color.White,
    tertiaryContainer = EstadoCelebracionContenedor,
    onTertiaryContainer = EstadoCelebracion,
    background = FondoCrema,
    onBackground = Texto,
    surface = FondoCrema,
    onSurface = Texto,
    surfaceVariant = FondoMapa,
    onSurfaceVariant = TextoSecundario,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = FondoCrema,
    surfaceContainer = SuperficieCrema,
    surfaceContainerHigh = SuperficieCrema,
    surfaceContainerHighest = SuperficieCrema,
    outline = Borde,
    outlineVariant = Borde
)

// El prototipo de Figma solo define tema claro, por eso no hay esquema oscuro ni color dinámico
@Composable
fun RedPatriominalSantiagoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
