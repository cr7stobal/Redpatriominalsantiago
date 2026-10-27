package com.example.redpatriominalsantiago.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.redpatriominalsantiago.navigation.Routes
import com.example.redpatriominalsantiago.ui.theme.Burdeo
import com.example.redpatriominalsantiago.ui.theme.Crema
import com.example.redpatriominalsantiago.ui.theme.Dorado
import com.example.redpatriominalsantiago.viewmodel.RegistroViewModel


@Composable
fun PantallaRegistro(
    navController: NavController,
    registroViewModel: RegistroViewModel = viewModel()
){
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Burdeo)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Red Patrimonial",
                    color = Crema,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
) { innerPadding ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    )
}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Text(
            "Registro",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Burdeo
        )
        OutlinedTextField(
            value = registroViewModel.nombre,
            onValueChange = {registroViewModel.cambiarNombre(it)},
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registroViewModel.mail,
            onValueChange = {registroViewModel.cambiarMail(it)},
            label = {
                Text("Corre electronico")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registroViewModel.pass,
            onValueChange = {registroViewModel.cambiarPass(it)},
            label = {
                Text("Contrasena")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = registroViewModel.pass2,
            onValueChange = {registroViewModel.cambiarPass2(it)},
            label = {
                Text("Confirmar contrasena")
            },
            modifier = Modifier.fillMaxWidth()
        )
        if (registroViewModel.mensaje != "") {
            Text(
                text = registroViewModel.mensaje,
                color = Color.Red
            )
        }
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        Button(
            onClick = {registroViewModel.usuarioAlmacenar { navController.popBackStack()}},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Burdeo,
                contentColor = Color.White
            )
        ) {
            Text("Crear usuario")
        }
    }
}