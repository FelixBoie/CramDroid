package classes

class Encounter2(_activation: Float, _reaction_time: Float, _time: Float,_decay:Float) {
    //Encounter = namedtuple("Encounter", "activation, time, reaction_time, decay")
    var activation = _activation
    var time = _time
    var reaction_time = _reaction_time
    var decay : Float =  _decay
}