package classes

class Word(
    _english: String,
    _dutch: String
) {
    val english = _english
    val dutch = _dutch
    var activation: Float = Float.NEGATIVE_INFINITY
    var prev_seen = false
    var encounters = mutableListOf<Encounter>()
    var previous_activation = Float.NEGATIVE_INFINITY
    var alpha = 0.3F
    var previous_alpha : Float = 0.3F
    var decay = 0.3F
}