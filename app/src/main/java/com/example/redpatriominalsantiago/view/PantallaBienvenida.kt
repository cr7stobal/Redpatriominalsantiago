package com.example.redpatriominalsantiago.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.redpatriominalsantiago.R
import com.example.redpatriominalsantiago.model.Perfil
import com.example.redpatriominalsantiago.view.components.CampoTexto
import com.example.redpatriominalsantiago.ui.theme.RedPatriominalSantiagoTheme
import com.example.redpatriominalsantiago.ui.theme.SuperficieIcono

private data class OpcionPerfil(
    val perfil: Perfil,
    @param:StringRes val titulo: Int,
    @param:StringRes val descripcion: Int,
    @param:DrawableRes val icono: Int
)

private val Opciones = listOf(
    OpcionPerfil(
        Perfil.Turista,
        R.string.perfil_turista,
        R.string.perfil_turista_descripcion,
        R.drawable.ic_user
    ),
    OpcionPerfil(
        Perfil.Colegio,
        R.string.perfil_colegio,
        R.string.perfil_colegio_descripcion,
        R.drawable.ic_group
    ),
    OpcionPerfil(
        Perfil.Encargado,
        R.string.perfil_encargado,
        R.string.perfil_encargado_descripcion,
        R.drawable.ic_church
    )
)

// Figma · 02 Bienvenida y selección de perfil.
// Prototipo académico: no hay cuentas reales, los campos vienen con datos ficticios y no se validan.
@Composable
fun PantallaBienvenida(
    onIngresar: (Perfil) -> Unit,
    onInvitado: () -> Unit,
    modifier: Modifier = Modifier
) {
    var perfil by rememberSaveable { mutableStateOf(Perfil.Turista) }
    var correo by rememberSaveable { mutableStateOf("usuario.demo@ejemplo.cl") }
    var contrasena by rememberSaveable { mutableStateOf("demo1234") }
    val foco = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .imePadding()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.bienvenida_titulo),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.bienvenida_descripcion),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(
                modifier = Modifier.selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Opciones.forEach { opcion ->
                    TarjetaPerfil(
                        opcion = opcion,
                        seleccionado = perfil == opcion.perfil,
                        onClick = { perfil = opcion.perfil }
                    )
                }
            }
            CampoTexto(
                valor = correo,
                onValorChange = { correo = it },
                etiqueta = stringResource(R.string.campo_correo),
                icono = R.drawable.ic_mail,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            CampoTexto(
                valor = contrasena,
                onValorChange = { contrasena = it },
                etiqueta = stringResource(R.string.campo_contrasena),
                icono = R.drawable.ic_lock,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { foco.clearFocus() })
            )
        }
        Button(
            onClick = { onIngresar(perfil) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(stringResource(R.string.bienvenida_ingresar))
        }
        TextButton(
            onClick = onInvitado,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {
            Text(stringResource(R.string.bienvenida_invitado))
        }
        Text(
            text = stringResource(R.string.bienvenida_pie),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TarjetaPerfil(opcion: OpcionPerfil, seleccionado: Boolean, onClick: () -> Unit) {
    val colores = MaterialTheme.colorScheme
    val forma = MaterialTheme.shapes.medium
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(forma)
            .background(if (seleccionado) colores.primaryContainer else Color.White)
            .border(
                width = if (seleccionado) 2.dp else 1.dp,
                color = if (seleccionado) colores.primary else colores.outline,
                shape = forma
            )
            .selectable(selected = seleccionado, role = Role.RadioButton, onClick = onClick)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(if (seleccionado) colores.primary else SuperficieIcono, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(opcion.icono),
                contentDescription = null,
                tint = if (seleccionado) colores.onPrimary else colores.primary,
                modifier = Modifier.size(22.dp)
            )
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = stringResource(opcion.titulo),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
                lineHeight = 18.sp
            )
            Text(
                text = stringResource(opcion.descripcion),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal,
                lineHeight = 14.sp,
                color = colores.onSurfaceVariant
            )
        }
        RadioButton(selected = seleccionado, onClick = null)
    }
}

@Preview(widthDp = 360, heightDp = 800)
@Composable
private fun PreviewPantallaBienvenida() {
    RedPatriominalSantiagoTheme {
        PantallaBienvenida(onIngresar = {}, onInvitado = {})
    }
}
