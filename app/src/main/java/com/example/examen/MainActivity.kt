package com.example.examen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.examen.ui.theme.ExamenTheme

/**
 * Actividad principal que muestra directamente el menú del juego.
 * Administra la apertura de las otras actividades mediante Intents explícitos.
 */
class MainActivity : ComponentActivity() {

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
                    PantallaMenu(
                        viewModel = modeloVista,
                        alNavegarAJuego = {
                            startActivity(Intent(this, JuegoActivity::class.java))
                        },
                        alNavegarAPuntajes = {
                            startActivity(Intent(this, PuntajesActivity::class.java))
                        },
                        alNavegarAInstrucciones = {
                            startActivity(Intent(this, InstruccionesActivity::class.java))
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        modeloVista.cargarConfiguracion()
    }
}
