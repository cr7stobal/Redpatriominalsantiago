package com.example.redpatriominalsantiago.view


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.redpatriominalsantiago.ui.theme.BeigeIcono
import com.example.redpatriominalsantiago.ui.theme.BordeSuave
import com.example.redpatriominalsantiago.ui.theme.RosaSeleccion
import org.w3c.dom.Text

data class PersonaIngreso(
    val icono: ImageVector,
    val nombre: String,
    val descripcion: String
)

@Composable
fun PantallaIniciarSesion(
    iniciarSesionViewModel: IniciarSesionViewModel = viewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(24.dp)
    ) {
        // TITULO DE BIENVENIDA
        Row(
        ) {
            Text(
                "Bienvenido",
                fontSize = 30.sp,
                color = Burdeo,
                fontWeight = FontWeight.Bold,
            )
        }
        // texto de bienvenida
        Row(
        ) {
            Text("Elige cómo usarás la Red Patrimonial para personalizar tu experiencia.")
        }
        // Opciones Para Elegir El Tipo De Usuario
        val radioButtonUsuarios = listOf(
            PersonaIngreso(
                icono = Icons.Filled.Person,
                nombre = "Turista",
                descripcion = "Explora templos y rutas por tu cuenta"
            ),
            PersonaIngreso(
                icono = Icons.Filled.School,
                nombre = "Colegio / Delegación",
                descripcion = "Planifica y agenda salidas pedagógicas"
            ),
            PersonaIngreso(
                icono = Icons.Filled.Church,
                nombre = "Encargado de templo",
                descripcion = "Publica horarios, eventos y avisos"
            )
        )

        var selectButton = remember {
            mutableStateOf(radioButtonUsuarios.first())
        }
        Column(
            modifier = Modifier.selectableGroup()
        ) {
            // UNA TARJETA POR CADA OPCION
            radioButtonUsuarios.forEach {
                val isSelect = it == selectButton.value
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelect) RosaSeleccion else Color.White)
                        .border(
                            width = if (isSelect) 2.dp else 1.dp,
                            color = if (isSelect) Burdeo else BordeSuave,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { selectButton.value = it }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    // circulo con el icono
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(if (isSelect) Burdeo else BeigeIcono)
                    ) {
                        Icon(
                            imageVector = it.icono,
                            contentDescription = null,
                            tint = if (isSelect) Color.White else Burdeo,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    // Nombre Y Descripcion
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp)
                    ) {
                        Text(text = it.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = it.descripcion, fontSize = 11.sp, color = Color.Gray)
                    }
                    RadioButton(
                        selected = isSelect,
                        onClick = { selectButton.value = it },
                        colors = RadioButtonDefaults.colors(selectedColor = Burdeo)
                    )
                }
            }
        }
        // campos de correo y contrasena
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Iniciar sesion",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = iniciarSesionViewModel.mail,
                onValueChange = { iniciarSesionViewModel.cambiarMail(it) },
                label = {
                    Text("Correo electronico")
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = iniciarSesionViewModel.pass,
                onValueChange = { iniciarSesionViewModel.cambiarContrasena(it) },
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text("Contrasena")
                },
                modifier = Modifier.fillMaxWidth()
            )
            // ENLACE PARA IR A REGISTRARSE
            Row(
                verticalAlignment = Alignment.CenterVertically
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
        // Mensaje De Error Y Botones
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize().padding(50.dp)
        ) {
            if (iniciarSesionViewModel.textoVisible) {
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
                    iniciarSesionViewModel.login { navController.navigate(Routes.GPS) }
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
                    println("CR7: Boton Invitado presionado...")
                    navController.navigate(Routes.GPS)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar como invitado", color = Burdeo)
            }
        }
    }
}