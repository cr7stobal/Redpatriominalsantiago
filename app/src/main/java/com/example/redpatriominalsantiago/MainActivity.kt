package com.example.redpatriominalsantiago

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.redpatriominalsantiago.ui.theme.RedPatriominalSantiagoTheme
import com.example.redpatriominalsantiago.view.PantallaBienvenida
import com.example.redpatriominalsantiago.view.PantallaPrincipal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // La app solo tiene tema claro: íconos de sistema oscuros aunque el teléfono esté en modo oscuro
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            RedPatriominalSantiagoTheme {
                var mostrarIntro by rememberSaveable { mutableStateOf(true) }
                if (mostrarIntro) {
                    PantallaPrincipal(onContinuar = { mostrarIntro = false })
                } else {
                    // Pendiente: ambos botones abrirán la pantalla 03 · Permiso de ubicación
                    PantallaBienvenida(onIngresar = {}, onInvitado = {})
                }
            }
        }
    }
}
