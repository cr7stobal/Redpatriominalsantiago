package com.example.redpatriominalsantiago.util

// Reglas para validar el documento del invitado: RUT chileno o pasaporte.
object ValidadorDocumento {

    // Deja el RUT solo con números y el dígito verificador: "12.345.678-5" -> "123456785"
    fun limpiarRut(rut: String): String {
        return rut.replace(".", "").replace("-", "").replace(" ", "").uppercase()
    }

    // Calcula el dígito verificador con el algoritmo módulo 11
    // (el mismo que usa el Registro Civil).
    fun calcularDigitoVerificador(cuerpo: String): Char {
        var suma = 0
        var multiplicador = 2
        // Se recorre el número de derecha a izquierda multiplicando por 2, 3, 4, 5, 6, 7, 2, 3...
        for (digito in cuerpo.reversed()) {
            suma += digito.digitToInt() * multiplicador
            multiplicador = if (multiplicador == 7) 2 else multiplicador + 1
        }
        val resultado = 11 - (suma % 11)
        return when (resultado) {
            11 -> '0'
            10 -> 'K'
            else -> resultado.digitToChar()
        }
    }

    // Revisa un RUT. Devuelve null si es válido, o el mensaje de error si no lo es.
    fun errorRut(rut: String): String? {
        val limpio = limpiarRut(rut)
        if (limpio.length < 8 || limpio.length > 9) {
            return "El RUT debe tener 7 u 8 números más el dígito verificador"
        }
        val cuerpo = limpio.dropLast(1)          // todo menos el último carácter
        val digitoIngresado = limpio.last()      // el último carácter
        if (!cuerpo.all { it.isDigit() }) {
            return "Antes del guion solo puede haber números"
        }
        if (!(digitoIngresado.isDigit() || digitoIngresado == 'K')) {
            return "El dígito verificador debe ser un número o K"
        }
        if (calcularDigitoVerificador(cuerpo) != digitoIngresado) {
            return "El dígito verificador no corresponde a este RUT"
        }
        return null
    }

    // Revisa un pasaporte: 6 a 9 letras o números, con al menos un número.
    fun errorPasaporte(pasaporte: String): String? {
        val limpio = pasaporte.trim().uppercase()
        if (limpio.length < 6 || limpio.length > 9) {
            return "El pasaporte debe tener entre 6 y 9 caracteres"
        }
        if (!limpio.all { it.isLetterOrDigit() }) {
            return "Solo se permiten letras y números, sin espacios ni símbolos"
        }
        if (!limpio.any { it.isDigit() }) {
            return "El pasaporte debe incluir al menos un número"
        }
        return null
    }

    // Muestra el RUT con puntos y guion: "123456785" -> "12.345.678-5"
    fun formatearRut(rut: String): String {
        val limpio = limpiarRut(rut)
        val cuerpo = limpio.dropLast(1)
        val digito = limpio.last()
        val conPuntos = cuerpo.reversed().chunked(3).joinToString(".").reversed()
        return "$conPuntos-$digito"
    }
}
