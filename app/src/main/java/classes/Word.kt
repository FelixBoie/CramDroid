package classes

class Word(
    _english: String,
    _dutch: String
) {
    val english = _english
    val dutch = _dutch
    val activation = -.9999
    var prev_seen = false
}