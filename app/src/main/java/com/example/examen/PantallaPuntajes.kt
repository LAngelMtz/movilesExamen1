package com.example.examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen.ui.theme.ExamenTheme

/**
 * Actividad  para mostrar el historial de partidas guardadas.
 */
class PuntajesActivity : ComponentActivity() {

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
                    PantallaPuntajes(
                        viewModel = modeloVista,
                        alVolverAMenu = { finish() }
                    )
                }
            }
        }
    }
}

/**
 * Pantalla que despliega el historial de puntuaciones guardadas en TinyDB.
 */
@Composable
fun PantallaPuntajes(
    viewModel: JuegoViewModel,
    alVolverAMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón volver
        Button(
            onClick = alVolverAMenu,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = "Volver al Menú")
        }

        // Título de la pantalla
        Text(
            text = "Historial de Partidas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (viewModel.listaPuntajes.isEmpty()) {
            Text(
                text = "No hay partidas registradas aún.",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.listaPuntajes) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "GANADOR: ${item.ganador}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )

                        Text(
                            text = "PERDEDOR: ${item.perdedor}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFC62828)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Fecha y hora: ${item.fechaHora} (Meta: ${item.metaJugada})",
                            fontSize = 14.sp
                        )

                        Text(
                            text = "Cartas ganadoras: ${item.cartasGanadoras}",
                            fontSize = 14.sp
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}
