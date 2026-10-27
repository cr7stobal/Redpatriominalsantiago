package com.example.redpatriominalsantiago.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val RedPatrimonialScheme = lightColorScheme(
    primary = Burdeo,
    onPrimary = Crema,

    secondary = Dorado,
    onSecondary = Burdeo,

    background = Fondo,

    error = Color.Red
)

@Composable
fun RedPatriominalSantiagoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = RedPatrimonialScheme,
        content = content
    )
}