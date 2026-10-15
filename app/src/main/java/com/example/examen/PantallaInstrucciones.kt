package com.example.examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen.ui.theme.ExamenTheme

/**
 * Actividad para mostrar las instrucciones del juego.
 */
class InstruccionesActivity : ComponentActivity() {

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
                    PantallaInstrucciones(
                        alVolverAMenu = { finish() }
                    )
                }
            }
        }
    }
}

/**
 * Pantalla que explica las reglas del juego del 21 (Blackjack) y sus variantes.
 * Usa obligatoriamente verticalScroll para permitir desplazamiento continuo del contenido.
 */
@Composable
fun PantallaInstrucciones(
    alVolverAMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Botón de regreso al menú principal
        Button(onClick = alVolverAMenu) {
            Text(text = "Volver al Menú")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Instrucciones del Juego del 21",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sección 1: Objetivo del Juego
        Text(
            text = "1. Objetivo del Juego",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "El objetivo del juego es acumular una suma de puntos con tus cartas que esté lo más cerca posible de la meta elegida (21, 26 o 31 puntos) sin sobrepasarla. Si te pasas de la meta, pierdes automáticamente la ronda.",
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Sección 2: Valor de las Cartas
        Text(
            text = "2. Valor de las Cartas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "• Cartas numéricas (2 al 10): Valen exactamente su valor numérico.\n" +
                    "• Figuras (Jota 'J', Reina 'Q', Rey 'K'): Valen 10 puntos cada una.\n" +
                    "• As ('A'): Vale 11 puntos por defecto, pero si la suma de tu mano sobrepasa la meta, se convierte automáticamente en 1 punto para evitar que pierdas.",
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Sección 3: Dinámica de los Turnos
        Text(
            text = "3. Turnos y Crupier",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "• Al iniciar la ronda, el Jugador y el Crupier reciben 2 cartas cada uno. Una de las cartas del Crupier permanece oculta.\n" +
                    "• En tu turno puedes elegir 'Pedir Carta' para sumar más puntos o 'Plantarse' si consideras que tu puntaje actual es suficiente.\n" +
                    "• Al plantarte, el Crupier revela su carta oculta y debe pedir cartas obligatoriamente hasta alcanzar al menos (Meta - 4) puntos.",
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Sección 4: Variantes de Meta
        Text(
            text = "4. Variantes de Meta (21, 26 y 31 puntos)",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "En el menú principal puedes personalizar la meta de puntos de la partida:\n" +
                    "• 21 Puntos: Modo clásico de Blackjack.\n" +
                    "• 26 Puntos: Variante de meta intermedia.\n" +
                    "• 31 Puntos: Variante extendida donde se requiere acumular más cartas para ganar.",
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
