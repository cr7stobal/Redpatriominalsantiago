package com.example.redpatriominalsantiago.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
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
import androidx.navigation.Navigation
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.viewmodel.IniciarSesionViewModel
import androidx.compose.material3.Icon



@Composable         // funcion grafica: sin esto android no visualiza en pantalla
fun PantallaIniciarSesion(
    iniciarSesionViewModel: IniciarSesionViewModel = viewModel(),
    navController: NavController
) {
    Column() {

    }

    // ELEJIR CON QUE USUARIO INGRESAR
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().padding(50.dp)
    ) {
        Row(
        ) {
            Text(
                "Bienvenido",
                fontSize = 30.sp,
                color = Burdeo,
                fontWeight = FontWeight.Bold
            )
        }
        Row() {
            Text("Elige cómo usarás la Red Patrimonial para personalizar tu experiencia.")
        }
        Row(
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null
            )
            Text(
                text = "Turista",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 30.dp)
            )

        }
        Row() {
            Icon(
                imageVector = Icons.Filled.School,
                contentDescription = null
            )
            Text(
                text = "Colegio / Delegacion",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
        Row() {
            Icon(
                imageVector = Icons.Filled.Church,
                contentDescription = null
            )
            Text(
                text = "Encargado templo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

    }
    // INGRESAR MAIL, CONTRASENA y registrarse
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "No tienes cuenta?",
                fontSize = 16.sp
            )
            TextButton(
                onClick = {
                    navController.navigate(Routes.REGISTER)
                }
            ) {
                Text(
                    text = "Registrate",
                    fontSize = 16.sp,
                    color = Burdeo
                )
            }
        }
    }
    // BOTONES
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize().padding(50.dp)
    ) {
        if(iniciarSesionViewModel.textoVisible){
        Text(
            text = "Credenciales incorrectas",
            color = Color.Red
        )
        }
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        Button(
            onClick = {
                iniciarSesionViewModel.textoVisible = false
                val validador = iniciarSesionViewModel.login()

                if (validador){
                    // vamos a otra pantalla (aqui va el navController)
                }else {
                    iniciarSesionViewModel.textoVisible = true
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
                navController.navigate(Routes.PERMISO_UBICACION)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar como invitado", color = Burdeo)
        }
    }
}