package classes

class Word(
    _swahili: String,
    _english: String
){
    val swahili = _swahili
    val english = _english
    val activation = -.9999
    var prev_seen = false

}