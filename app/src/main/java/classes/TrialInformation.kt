package classes

class TrialInformation(_word: Word, _reactionTime: Long, _correct: Boolean ) {
     var curWord: Word = _word
    var reactionTime: Long = _reactionTime
    var correctNess: Boolean = _correct

    fun addTrialInformation(_word: Word, _reactionTime: Long, _correct: Boolean){
        correctNess = _correct
        reactionTime= _reactionTime
        curWord = _word
    }
}

