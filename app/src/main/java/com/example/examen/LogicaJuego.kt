package com.example.examen

/**
 * Clase que gestiona el mazo de 52 cartas de la baraja inglesa.
 */
class Baraja {
    val cartas: MutableList<Carta> = mutableListOf()

    init {
        reiniciar()
    }

    /**
     * Crea las 52 cartas posibles combinando los 4 palos y los 13 valores,
     * y las mezcla aleatoriamente.
     */
    fun reiniciar() {
        cartas.clear()
        for (palo in Palo.entries) {
            for (valor in ValorCarta.entries) {
                cartas.add(Carta(palo, valor))
            }
        }
        cartas.shuffle()
    }

    /**
     * Extrae y devuelve la carta superior de la baraja.
     * Si no quedan cartas, se reinicia y mezcla la baraja automáticamente.
     */
    fun robarCarta(): Carta {
        if (cartas.isEmpty()) {
            reiniciar()
        }
        return cartas.removeAt(0)
    }
}

/**
 * Calcula el puntaje total de una mano de cartas teniendo en cuenta la meta de puntos.
 * Si la suma supera la meta y hay Ases (que valen 11 por defecto), ajusta el valor de
 * cada As a 1 (restando 10 al total) mientras el puntaje siga superando la meta.
 */
fun calcularPuntaje(mano: List<Carta>, meta: Int): Int {
    var total = mano.sumOf { it.valor.valorPuntos }
    var ases = mano.count { it.valor == ValorCarta.AS }

    while (total > meta && ases > 0) {
        total -= 10
        ases--
    }

    return total
}
