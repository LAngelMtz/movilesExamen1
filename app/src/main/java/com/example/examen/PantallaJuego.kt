package com.example.examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen.ui.theme.ExamenTheme

/**
 * Actividad para la pantalla del juego.
 */
class JuegoActivity : ComponentActivity() {

    private val modeloVista: JuegoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenTheme(temaSeleccionado = modeloVista.temaActual) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaJuego(
                        viewModel = modeloVista,
                        alVolverAMenu = { finish() }
                    )
                }
            }
        }
    }
}

/**
 * Pantalla interactiva para jugar la partida de Blackjack.
 */
@Composable
fun PantallaJuego(
    viewModel: JuegoViewModel,
    alVolverAMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    val puntajeJugador = calcularPuntaje(viewModel.manoJugador, viewModel.metaPuntos)
    val puntajeCrupier = calcularPuntaje(viewModel.manoCrupier, viewModel.metaPuntos)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = alVolverAMenu) {
                Text(text = "Volver al Menú")
            }

            Text(
                text = "Meta actual: ${viewModel.metaPuntos} puntos",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección del Crupier
        Text(
            text = "Crupier (Dispositivo)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        if (!viewModel.ocultarCartaCrupier) {
            Text(text = "Puntaje: $puntajeCrupier", fontSize = 16.sp)
        } else {
            Text(text = "Puntaje: ?", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(viewModel.manoCrupier) { indice, carta ->
                val debeOcultar = (indice == 1 && viewModel.ocultarCartaCrupier)
                VistaCarta(carta = carta, oculta = debeOcultar)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mensaje de resultado o estado
        if (viewModel.mensajeResultado.isNotBlank()) {
            Text(
                text = viewModel.mensajeResultado,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sección del Jugador
        Text(
            text = "Jugador",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Tu Puntaje: $puntajeJugador", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(viewModel.manoJugador) { _, carta ->
                VistaCarta(carta = carta, oculta = false)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Botones de Acción inferior
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.pedirCartaJugador() },
                enabled = !viewModel.juegoTerminado
            ) {
                Text(text = "Pedir Carta")
            }

            Button(
                onClick = { viewModel.plantarse() },
                enabled = !viewModel.juegoTerminado
            ) {
                Text(text = "Plantarse")
            }

            if (viewModel.juegoTerminado) {
                Button(
                    onClick = { viewModel.iniciarJuego() }
                ) {
                    Text(text = "Jugar de Nuevo")
                }
            }
        }
    }
}
