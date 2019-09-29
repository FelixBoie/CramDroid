package classes

class Word(
    _english: String,
    _dutch: String
) {
    val english = _english
    val dutch = _dutch
    val activation = -.9999
    val prev_seen = false
}