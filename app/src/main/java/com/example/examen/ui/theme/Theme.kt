package com.example.examen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.examen.TemaApp

// Esquema de colores para el Tema Verde
private val EsquemaVerde = lightColorScheme(
    primary = VerdePrimario,
    onPrimary = VerdeTextoEnPrimario,
    secondary = VerdeSecundario,
    onSecondary = Blanco,
    background = VerdeFondo,
    onBackground = Blanco,
    surface = VerdeSuperficie,
    onSurface = Blanco
)

// Esquema de colores para el Tema Rojo
private val EsquemaRojo = lightColorScheme(
    primary = RojoPrimario,
    onPrimary = RojoTextoEnPrimario,
    secondary = RojoSecundario,
    onSecondary = Blanco,
    background = RojoFondo,
    onBackground = Blanco,
    surface = RojoSuperficie,
    onSurface = Blanco
)

// Esquema de colores para el Tema Azul
private val EsquemaAzul = lightColorScheme(
    primary = AzulPrimario,
    onPrimary = AzulTextoEnPrimario,
    secondary = AzulSecundario,
    onSecondary = Blanco,
    background = AzulFondo,
    onBackground = Blanco,
    surface = AzulSuperficie,
    onSurface = Blanco
)

/**
 * Tema principal de la aplicación que recibe el tema seleccionado (VERDE, ROJO, AZUL)
 * y aplica el esquema de colores correspondiente.
 */
@Composable
fun ExamenTheme(
    temaSeleccionado: TemaApp = TemaApp.VERDE,
    content: @Composable () -> Unit
) {
    val colorScheme = when (temaSeleccionado) {
        TemaApp.VERDE -> EsquemaVerde
        TemaApp.ROJO -> EsquemaRojo
        TemaApp.AZUL -> EsquemaAzul
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
