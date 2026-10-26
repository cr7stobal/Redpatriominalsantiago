package com.example.redpatriominalsantiago

import com.example.redpatriominalsantiago.viewmodel.QrViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

// Pruebas de cómo se reconoce el QR de un templo
class QrViewModelTest {

    private val viewModel = QrViewModel()

    @Test
    fun qrDeTemploValido() {
        assertEquals(3, viewModel.obtenerIdTemplo("REDPATRIMONIAL;TEMPLO;ID=3"))
    }

    @Test
    fun qrDeOtraCosa() {
        assertNull(viewModel.obtenerIdTemplo("https://www.google.com"))
        assertNull(viewModel.obtenerIdTemplo("REDPATRIMONIAL;INVITADO;RUT=12.345.678-5"))   // el pase del invitado
    }

    @Test
    fun temploQueNoExiste() {
        assertNull(viewModel.obtenerIdTemplo("REDPATRIMONIAL;TEMPLO;ID=99"))
        assertNull(viewModel.obtenerIdTemplo("REDPATRIMONIAL;TEMPLO;ID=abc"))
    }
}
