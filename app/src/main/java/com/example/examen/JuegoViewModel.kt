package com.example.examen

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Temas visuales disponibles para la aplicación.
 */
enum class TemaApp {
    VERDE,
    ROJO,
    AZUL
}

/**
 * ViewModel que contiene el estado global del juego y administra la lógica
 * de turnos, barajado, puntajes y persistencia de configuración en TinyDB.
 */
class JuegoViewModel(application: Application) : AndroidViewModel(application) {

    private val tinyDB = TinyDB(application)
    private val baraja = Baraja()

    // Meta de puntos seleccionada (21, 26 o 31)
    var metaPuntos by mutableIntStateOf(21)
        private set

    // Tema de color actual de la interfaz
    var temaActual by mutableStateOf(TemaApp.VERDE)
        private set

    // Manos del jugador y del crupier
    val manoJugador = mutableStateListOf<Carta>()
    val manoCrupier = mutableStateListOf<Carta>()

    // Estado del juego
    var ocultarCartaCrupier by mutableStateOf(true)
        private set

    var juegoTerminado by mutableStateOf(false)
        private set

    var mensajeResultado by mutableStateOf("")
        private set

    // Historial de puntuaciones
    val listaPuntajes = mutableStateListOf<RegistroPuntaje>()

    init {
        cargarConfiguracion()
        cargarPuntajes()
        iniciarJuego()
    }

    /**
     * Lee la configuración de metaPuntos y temaActual guardados en TinyDB.
     */
    fun cargarConfiguracion() {
        val metaGuardada = tinyDB.getInt("meta_puntos")
        metaPuntos = if (metaGuardada in listOf(21, 26, 31)) metaGuardada else 21

        val temaGuardado = tinyDB.getString("tema_app")
        temaActual = try {
            if (temaGuardado.isNotBlank()) TemaApp.valueOf(temaGuardado) else TemaApp.VERDE
        } catch (e: Exception) {
            TemaApp.VERDE
        }
    }

    /**
     * Lee la lista de registros guardados en TinyDB y llena listaPuntajes.
     */
    fun cargarPuntajes() {
        listaPuntajes.clear()
        val textosGuardados = tinyDB.getListString("puntajes") ?: ArrayList()
        for (texto in textosGuardados) {
            if (texto.isNotBlank()) {
                listaPuntajes.add(RegistroPuntaje.desdeTexto(texto))
            }
        }
    }

    /**
     * Crea un nuevo registro de puntaje y lo almacena tanto en memoria como en TinyDB.
     */
    fun guardarNuevoPuntaje(ganador: String, perdedor: String, cartas: List<Carta>) {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val fechaActual = formatoFecha.format(Date())
        val textoCartas = cartas.joinToString(", ") { it.obtenerTextoCarta() }

        val nuevoRegistro = RegistroPuntaje(
            ganador = ganador,
            perdedor = perdedor,
            fechaHora = fechaActual,
            cartasGanadoras = textoCartas,
            metaJugada = metaPuntos
        )

        listaPuntajes.add(0, nuevoRegistro)

        val listaTextos = ArrayList(listaPuntajes.map { it.aTexto() })
        tinyDB.putListString("puntajes", listaTextos)
    }

    /**
     * Reinicia el juego: mezcla la baraja, limpia las manos y reparte 2 cartas a cada participante.
     */
    fun iniciarJuego() {
        baraja.reiniciar()
        manoJugador.clear()
        manoCrupier.clear()
        ocultarCartaCrupier = true
        juegoTerminado = false
        mensajeResultado = ""

        // Repartir 2 cartas al jugador
        manoJugador.add(baraja.robarCarta())
        manoJugador.add(baraja.robarCarta())

        // Repartir 2 cartas al crupier
        manoCrupier.add(baraja.robarCarta())
        manoCrupier.add(baraja.robarCarta())
    }

    /**
     * El jugador solicita una carta adicional.
     * Si supera la meta de puntos, pierde automáticamente la partida.
     */
    fun pedirCartaJugador() {
        if (juegoTerminado) return

        manoJugador.add(baraja.robarCarta())
        val puntajeJugador = calcularPuntaje(manoJugador, metaPuntos)

        if (puntajeJugador > metaPuntos) {
            juegoTerminado = true
            ocultarCartaCrupier = false
            mensajeResultado = "¡Te has pasado de $metaPuntos puntos! Gana el Crupier."
            guardarNuevoPuntaje("Crupier", "Jugador", manoCrupier)
        }
    }

    /**
     * El jugador se planta. El crupier revela su carta y roba cartas hasta alcanzar al menos (metaPuntos - 4).
     * Finalmente se compara quién se acercó más a la meta sin pasarse.
     */
    fun plantarse() {
        if (juegoTerminado) return

        ocultarCartaCrupier = false
        val limiteCrupier = metaPuntos - 4

        while (calcularPuntaje(manoCrupier, metaPuntos) < limiteCrupier) {
            manoCrupier.add(baraja.robarCarta())
        }

        val puntajeJugador = calcularPuntaje(manoJugador, metaPuntos)
        val puntajeCrupier = calcularPuntaje(manoCrupier, metaPuntos)

        juegoTerminado = true

        if (puntajeCrupier > metaPuntos) {
            mensajeResultado = "¡El Crupier se pasó de $metaPuntos puntos! ¡Has ganado!"
            guardarNuevoPuntaje("Jugador", "Crupier", manoJugador)
        } else if (puntajeJugador > puntajeCrupier) {
            mensajeResultado = "¡Ganaste! Te acercaste más a $metaPuntos puntos ($puntajeJugador vs $puntajeCrupier)."
            guardarNuevoPuntaje("Jugador", "Crupier", manoJugador)
        } else if (puntajeCrupier > puntajeJugador) {
            mensajeResultado = "Gana el Crupier con $puntajeCrupier puntos contra tus $puntajeJugador."
            guardarNuevoPuntaje("Crupier", "Jugador", manoCrupier)
        } else {
            mensajeResultado = "¡Empate! Ambos tienen $puntajeJugador puntos."
        }
    }

    /**
     * Cambia la meta de puntos del juego (21, 26, 31), guarda en TinyDB y reinicia la partida.
     */
    fun cambiarMeta(nuevaMeta: Int) {
        metaPuntos = nuevaMeta
        tinyDB.putInt("meta_puntos", nuevaMeta)
        iniciarJuego()
    }

    /**
     * Cambia el tema visual activo en la aplicación y lo guarda en TinyDB.
     */
    fun cambiarTema(nuevoTema: TemaApp) {
        temaActual = nuevoTema
        tinyDB.putString("tema_app", nuevoTema.name)
    }
}
