package classes

class TrialInformation(_word: Word, _reactionTime: Float, _correct: Boolean ) {
     var curWord: Word = _word
    var reactionTime: Float = _reactionTime
    var correctNess: Boolean = _correct

    fun addTrialInformation(_word: Word, _reactionTime: Float, _correct: Boolean){
        correctNess = _correct
        reactionTime= _reactionTime
        curWord = _word

        if(correctNess == false){
            reactionTime = 60000F
        }
    }
}

