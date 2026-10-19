package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.viewmodel.IniciarSesionViewModel


@Composable
fun PantallaIniciarSesion(
    iniciarSesionViewModel: IniciarSesionViewModel = viewModel(),
    navController: NavController
) {
    // ELEJIR CON QUE USUARIO INGRESAR
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().padding(50.dp)
    ) {
        Text(
            text = "Turista",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Text(
            text = "Colegio / Delegacion",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Text(
            text = "Encargado templo",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
    // INGRESAR MAIL, CONTRASENA Y CREAR USUARIO
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Text(
            text = "Iniciar sesion",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = iniciarSesionViewModel.mail,
            onValueChange = {iniciarSesionViewModel.cambiarMail(it)},
            label = {
                Text("Correo electronico")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = iniciarSesionViewModel.pass,
            onValueChange = {iniciarSesionViewModel.cambiarContrasena(it)},
            visualTransformation = PasswordVisualTransformation(),
            label = {
                Text("Contrasena")
            },
            modifier = Modifier.fillMaxWidth()
        )
        TextButton(
            onClick = {navController.navigate(Routes.REGISTER)},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Dorado,
                contentColor = Crema
            ),
        ) {
            Text(
                text = "Crear Usuario"
            )
        }

    }
    // BOTONES Ingresar, invitado
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize().padding(50.dp)
    ) {
        if (iniciarSesionViewModel.textoVisible) {
            Text(
                text = iniciarSesionViewModel.mensajeError,
                color = Color.Red
            )
        }
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        Button(
            onClick = {
                iniciarSesionViewModel.login {
                    // Aquí va la pantalla que sigue después del login, por ejemplo:
                    // navController.navigate(Routes.MAPA)
                    println("Login correcto")
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Burdeo,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ingresar")
        }
        TextButton(
            onClick = {
                println("Boton Invitado presionado...")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar como invitado", color = Burdeo)
        }
    }
}