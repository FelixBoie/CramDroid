package classes

class Word(
    _english: String,
    _dutch: String
) {
    val english = _english
    val dutch = _dutch
    var activation = -.9999
    var prev_seen = false
    var encounters = mutableListOf<Long>()
    var previous_activation = -0.99999

    fun add_encounter(encounter: Long){
        encounters.add(encounter)
    }
}