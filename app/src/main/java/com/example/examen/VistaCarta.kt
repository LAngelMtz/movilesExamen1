package com.example.examen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Muestra la imagen de una carta o el reverso (fondo.jpg) si está oculta.
 */
@Composable
fun VistaCarta(
    carta: Carta?,
    oculta: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val idRecurso = if (carta == null || oculta) {
        R.drawable.fondo
    } else {
        val resId = context.resources.getIdentifier(
            carta.obtenerNombreImagen(),
            "drawable",
            context.packageName
        )
        if (resId != 0) resId else R.drawable.fondo
    }

    Image(
        painter = painterResource(id = idRecurso),
        contentDescription = if (carta != null && !oculta) carta.obtenerTextoCarta() else "Carta oculta",
        modifier = modifier
            .size(width = 80.dp, height = 115.dp)
            .padding(4.dp)
    )
}
