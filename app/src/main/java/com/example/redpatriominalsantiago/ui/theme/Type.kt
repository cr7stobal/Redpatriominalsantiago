package com.example.redpatriominalsantiago.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.redpatriominalsantiago.R

@OptIn(ExperimentalTextApi::class)
private fun lora(weight: FontWeight, style: FontStyle = FontStyle.Normal) = Font(
    resId = if (style == FontStyle.Italic) R.font.lora_italic else R.font.lora,
    weight = weight,
    style = style,
    variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight))
)

// Lora da el tono patrimonial y editorial a los titulares; Roboto es la fuente nativa de Material 3
val Lora = FontFamily(
    lora(FontWeight.Bold),
    lora(FontWeight.Normal, FontStyle.Italic)
)

private val Base = Typography()

val Typography = Typography(
    displayLarge = Base.displayLarge.copy(fontFamily = Lora, fontWeight = FontWeight.Bold),
    displayMedium = Base.displayMedium.copy(fontFamily = Lora, fontWeight = FontWeight.Bold),
    displaySmall = Base.displaySmall.copy(fontFamily = Lora, fontWeight = FontWeight.Bold),
    headlineLarge = Base.headlineLarge.copy(fontFamily = Lora, fontWeight = FontWeight.Bold),
    // Lora Bold 28 — Titulares
    headlineMedium = Base.headlineMedium.copy(fontFamily = Lora, fontWeight = FontWeight.Bold),
    headlineSmall = Base.headlineSmall.copy(fontFamily = Lora, fontWeight = FontWeight.Bold),
    // Roboto Medium 20 — Barras
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // Roboto Regular 16 — Cuerpo de texto para fichas e historia
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = Base.labelLarge.copy(letterSpacing = 0.sp),
    // Roboto Medium 12 — Etiquetas y chips
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    )
)
