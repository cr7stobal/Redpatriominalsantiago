package com.example.redpatriominalsantiago.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpatriominalsantiago.util.ValidadorDocumento
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class QrInvitadoViewModel : ViewModel() {

    // Duración del pase: 10 minutos
    private val duracionSegundos = 10 * 60

    // Paso 1: el documento
    var tipoDocumento by mutableStateOf("RUT")        // "RUT" o "Pasaporte"
    var documento by mutableStateOf("")
    var error by mutableStateOf<String?>(null)        // null = sin error

    // Paso 2: el código generado
    var contenidoQr by mutableStateOf<String?>(null)  // null = todavía no se genera
    var documentoMostrado by mutableStateOf("")       // documento formateado para mostrar
    var segundosRestantes by mutableStateOf(0)
    var horaVencimiento by mutableStateOf(0L)          // momento en que vence (milisegundos)

    private var cuentaRegresiva: Job? = null

    fun cambiarTipo(nuevoTipo: String) {
        tipoDocumento = nuevoTipo
        documento = ""
        error = null
    }

    fun cambiarDocumento(nuevoTexto: String) {
        documento = nuevoTexto
        error = null          // el error se borra mientras se escribe
    }

    // Valida el documento y, si está bien, genera el código QR
    fun generarQr() {
        val mensajeError = if (tipoDocumento == "RUT") {
            ValidadorDocumento.errorRut(documento)
        } else {
            ValidadorDocumento.errorPasaporte(documento)
        }
        if (mensajeError != null) {
            error = mensajeError
            return
        }

        documentoMostrado = if (tipoDocumento == "RUT") {
            ValidadorDocumento.formatearRut(documento)
        } else {
            documento.trim().uppercase()
        }
        horaVencimiento = System.currentTimeMillis() + duracionSegundos * 1000L
        val codigo = UUID.randomUUID().toString().take(8).uppercase()   // código único del pase

        // Texto que va dentro del QR y que leerá el tótem
        contenidoQr = "REDPATRIMONIAL;INVITADO;$tipoDocumento=$documentoMostrado;VENCE=$horaVencimiento;COD=$codigo"
        iniciarCuentaRegresiva()
    }

    // Cada segundo recalcula cuánto falta para que venza
    private fun iniciarCuentaRegresiva() {
        cuentaRegresiva?.cancel()      // si había una cuenta anterior, se detiene
        segundosRestantes = duracionSegundos
        cuentaRegresiva = viewModelScope.launch {
            while (segundosRestantes > 0) {
                delay(1000)
                val faltan = (horaVencimiento - System.currentTimeMillis()) / 1000
                segundosRestantes = if (faltan > 0) faltan.toInt() else 0
            }
        }
    }

    fun estaVencido(): Boolean {
        return contenidoQr != null && segundosRestantes == 0
    }

    // Tiempo restante como "09:58"
    fun tiempoRestanteTexto(): String {
        val minutos = segundosRestantes / 60
        val segundos = segundosRestantes % 60
        return "%02d:%02d".format(minutos, segundos)
    }

    // Vuelve al paso 1 para ingresar el documento otra vez
    fun nuevoCodigo() {
        cuentaRegresiva?.cancel()
        contenidoQr = null
        segundosRestantes = 0
    }
}
