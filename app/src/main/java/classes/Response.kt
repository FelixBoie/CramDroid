package classes

import android.os.SystemClock

class Response (fact:Fact,_time:Long, _reactionTime:Float,_correct:Boolean){
    //Response = namedtuple("Response", "fact, start_time, rt, correct")
    val fact = fact;
    val startTime = _time // time set to time of creation
    val reactionTime = _reactionTime
    val correct = _correct
}