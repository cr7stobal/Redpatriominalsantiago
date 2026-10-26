package com.example.redpatriominalsantiago

import com.example.redpatriominalsantiago.util.ValidadorDocumento
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

// Pruebas del validador de RUT y pasaporte (se ejecutan en el computador, sin emulador)
class ValidadorDocumentoTest {

    @Test
    fun rutValidoEnDistintosFormatos() {
        assertNull(ValidadorDocumento.errorRut("12.345.678-5"))
        assertNull(ValidadorDocumento.errorRut("12345678-5"))
        assertNull(ValidadorDocumento.errorRut("123456785"))
        assertNull(ValidadorDocumento.errorRut("11.111.111-1"))
        assertNull(ValidadorDocumento.errorRut("1.000.005-k"))   // dígito K, en minúscula
        assertNull(ValidadorDocumento.errorRut("9.999.999-3"))   // RUT de 7 números
    }

    @Test
    fun rutConDigitoIncorrecto() {
        assertNotNull(ValidadorDocumento.errorRut("12.345.678-9"))
        assertNotNull(ValidadorDocumento.errorRut("11.111.111-K"))
    }

    @Test
    fun rutMalEscrito() {
        assertNotNull(ValidadorDocumento.errorRut("123"))            // muy corto
        assertNotNull(ValidadorDocumento.errorRut("12.34A.678-5"))   // letra en el cuerpo
        assertNotNull(ValidadorDocumento.errorRut("12.345.678-X"))   // dígito inválido
    }

    @Test
    fun formatoDelRut() {
        assertEquals("12.345.678-5", ValidadorDocumento.formatearRut("123456785"))
        assertEquals("1.000.005-K", ValidadorDocumento.formatearRut("1000005k"))
    }

    @Test
    fun pasaportes() {
        assertNull(ValidadorDocumento.errorPasaporte("F12345678"))
        assertNull(ValidadorDocumento.errorPasaporte("ab1234"))
        assertNotNull(ValidadorDocumento.errorPasaporte("ABC"))          // muy corto
        assertNotNull(ValidadorDocumento.errorPasaporte("ABCDEFGH"))     // sin números
        assertNotNull(ValidadorDocumento.errorPasaporte("AB-12345"))     // con símbolo
    }
}
