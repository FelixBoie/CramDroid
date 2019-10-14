package classes

class Encounter(_activation: Float, _reaction_time: Float, _time_of_encounter: Float){
    var activation = _activation
    var reaction_time = _reaction_time
    var decay : Float =  0.3F
    var time_of_encounter = _time_of_encounter
}