package com.example.examen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Pantalla principal del menú de la aplicación.
 */
@Composable
fun PantallaMenu(
    viewModel: JuegoViewModel,
    alNavegarAJuego: () -> Unit,
    alNavegarAPuntajes: () -> Unit,
    alNavegarAInstrucciones: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Juego del 21 - Blackjack",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botones de navegación principal
        Button(
            onClick = alNavegarAJuego,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 4.dp)
        ) {
            Text(text = "Jugar", fontSize = 18.sp)
        }

        Button(
            onClick = alNavegarAPuntajes,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 4.dp)
        ) {
            Text(text = "Ver Scores", fontSize = 18.sp)
        }

        Button(
            onClick = alNavegarAInstrucciones,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 4.dp)
        ) {
            Text(text = "Instrucciones", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Selección de Meta de Puntos
        Text(
            text = "Jugar a:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.cambiarMeta(21) },
                enabled = viewModel.metaPuntos != 21
            ) {
                Text(text = "21")
            }

            Button(
                onClick = { viewModel.cambiarMeta(26) },
                enabled = viewModel.metaPuntos != 26
            ) {
                Text(text = "26")
            }

            Button(
                onClick = { viewModel.cambiarMeta(31) },
                enabled = viewModel.metaPuntos != 31
            ) {
                Text(text = "31")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Selección de Tema de Color
        Text(
            text = "Tema de color:",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.cambiarTema(TemaApp.VERDE) },
                enabled = viewModel.temaActual != TemaApp.VERDE
            ) {
                Text(text = "Verde")
            }

            Button(
                onClick = { viewModel.cambiarTema(TemaApp.ROJO) },
                enabled = viewModel.temaActual != TemaApp.ROJO
            ) {
                Text(text = "Rojo")
            }

            Button(
                onClick = { viewModel.cambiarTema(TemaApp.AZUL) },
                enabled = viewModel.temaActual != TemaApp.AZUL
            ) {
                Text(text = "Azul")
            }
        }
    }
}
