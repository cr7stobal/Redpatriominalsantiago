package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.viewmodel.RegistrarViewModel

@Composable
fun PantallaCrearUsuario(
    registrarViewModel: RegistrarViewModel = viewModel(),
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Text(
            text = "Crear usuario",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = registrarViewModel.mail,
            onValueChange = { registrarViewModel.crearMail(it) },
            label = { Text("Correo electronico") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registrarViewModel.pass,
            onValueChange = { registrarViewModel.crearPass(it) },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Contrasena") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registrarViewModel.pass2,
            onValueChange = { registrarViewModel.comprobarPass(it) },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Confirmar Contrasena") },
            modifier = Modifier.fillMaxWidth()
        )
        if (registrarViewModel.textoVisible) {
            Text(
                text = registrarViewModel.mensajeError,
                color = Color.Red
            )
        }
        Button(
            onClick = {
                registrarViewModel.registrar {
                    navController.popBackStack() // vuelve al login
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Burdeo,
                contentColor = Color.White
            )
        ) {
            Text(text = "Crear Usuario")
        }
    }
}