package com.example.examen

/**
 * Representa el palo de una carta de la baraja inglesa.
 */
enum class Palo(val nombreIngles: String, val nombreEspanol: String) {
    TREBOLES("clubs", "Tréboles"),
    DIAMANTES("diamonds", "Diamantes"),
    CORAZONES("hearts", "Corazones"),
    PICAS("spades", "Picas")
}

/**
 * Representa el valor de una carta con su número para el recurso de imagen,
 * su valor numérico en puntos y su etiqueta visible.
 */
enum class ValorCarta(val numeroImagen: Int, val valorPuntos: Int, val etiqueta: String) {
    AS(1, 11, "A"),
    DOS(2, 2, "2"),
    TRES(3, 3, "3"),
    CUATRO(4, 4, "4"),
    CINCO(5, 5, "5"),
    SEIS(6, 6, "6"),
    SIETE(7, 7, "7"),
    OCHO(8, 8, "8"),
    NUEVE(9, 9, "9"),
    DIEZ(10, 10, "10"),
    JOTA(11, 10, "J"),
    REINA(12, 10, "Q"),
    REY(13, 10, "K")
}

/**
 * Clase de datos que combina un palo y un valor para formar una carta de juego.
 */
data class Carta(
    val palo: Palo,
    val valor: ValorCarta
) {
    /**
     * Devuelve el nombre del recurso de imagen correspondiente en res/drawable.
     * Ejemplo: "clubs_1", "hearts_13".
     */
    fun obtenerNombreImagen(): String {
        return "${palo.nombreIngles}_${valor.numeroImagen}"
    }

    /**
     * Devuelve una representación en texto ejecutable de la carta.
     * Ejemplo: "A de Tréboles", "K de Corazones".
     */
    fun obtenerTextoCarta(): String {
        return "${valor.etiqueta} de ${palo.nombreEspanol}"
    }
}
