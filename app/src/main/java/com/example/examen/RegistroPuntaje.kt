package com.example.examen

/**
 * Clase de datos que almacena el resultado de una partida finalizada.
 * Permite serializar y deserializar registros a texto mediante "|" para ser guardados
 * en TinyDB usando putListString y getListString.
 */
data class RegistroPuntaje(
    val ganador: String,
    val perdedor: String,
    val fechaHora: String,
    val cartasGanadoras: String,
    val metaJugada: Int
) {
    /**
     * Convierte el registro a un formato de texto separado por "|".
     * Ejemplo: "Jugador 1|Jugador 2|2026-10-13 10:00|A de Corazones, K de Picas|21"
     */
    fun aTexto(): String {
        return "$ganador|$perdedor|$fechaHora|$cartasGanadoras|$metaJugada"
    }

    companion object {
        /**
         * Reconstruye un RegistroPuntaje a partir de una cadena con formato "ganador|perdedor|fechaHora|cartasGanadoras|metaJugada".
         */
        fun desdeTexto(cadena: String): RegistroPuntaje {
            val partes = cadena.split("|")
            if (partes.size >= 5) {
                return RegistroPuntaje(
                    ganador = partes[0],
                    perdedor = partes[1],
                    fechaHora = partes[2],
                    cartasGanadoras = partes[3],
                    metaJugada = partes[4].toIntOrNull() ?: 0
                )
            }
            return RegistroPuntaje("", "", "", "", 0)
        }
    }
}
