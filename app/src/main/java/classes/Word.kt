package classes

class Word(
    _english: String,
    _dutch: String
) {
    val english = _english
    val dutch = _dutch
    var activation: Float = Float.NEGATIVE_INFINITY
    var prev_seen = false
    var encounters = ArrayList<Encounter>()
    var previous_activation = Float.NEGATIVE_INFINITY
    var alpha = 0.3F
    var previous_alpha : Float = 0.3F
    var decay = 0.3F

    fun getAsString():String{
        //returns all values a string, that way they are saved; lets add encounters last, should be easier to add them in later again
        return english + "," + dutch + "," + activation + "," + prev_seen +
                "," + previous_activation + "," + alpha +
                "," + previous_alpha + "," + decay + "," + getEncounterAsString() + "\n"
    }

    fun setStringAsWord(activation: Float, prev_seen: Boolean, previous_activation :Float, alpha : Float, previous_alpha : Float, decay: Float, encounter: ArrayList<Encounter>){
        this.activation = activation
        this.prev_seen = prev_seen
        this.encounters = encounter
        this.previous_activation = previous_activation
        this.alpha = alpha
        this.previous_alpha = previous_alpha
        this.decay = decay
    }

    private fun getEncounterAsString():String{
        //var activation = _activation
        //    var reaction_time = _reaction_time
        //    var decay : Float =  0.3F
        //    var time_of_encounter =

        var outputString = ""
        for (singleEncounter in encounters) {
            outputString = outputString + singleEncounter.activation + "," +
                    singleEncounter.reaction_time.toString() + "," +
                    singleEncounter.decay + "," + singleEncounter.time_of_encounter.toString() + ","
        }
        return outputString
    }
}