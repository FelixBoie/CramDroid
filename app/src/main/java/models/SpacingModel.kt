package models
import classes.Word

import kotlin.math.pow
import kotlin.math.exp

class SpacingModel() {
    // var seenWords = MutableList<Word>()
    //var currentWord: Word = _word
    private var nextAction: Word = Word("new word", "new word")


   // # Model constants
   // val LOOKAHEAD_TIME = 15000
    val FORGET_THRESHOLD = -0.8
    val DEFAULT_ALPHA = 0.3
    val C = 0.25
    //val F = 1.0


//    private fun setCurWord(word: Word){
//        currentWord = word
//    }

    fun selectAction(seen_words: MutableList<Word>): Word{
        val lowestActivatedWord = calculateLowestActivations(seen_words)
        if(lowestActivatedWord.activation < FORGET_THRESHOLD ){
            return lowestActivatedWord
        }else{
            return nextAction
        }
    }

    //calculate Lowest activation of all seen words
    fun calculateLowestActivations(seen_words: MutableList<Word>): Word{
        var lowestActivationWord = Word("default", "default")
        lowestActivationWord.activation = 10000.0
        var lowest_activation = -999999999.9
        for (i in seen_words){
            i.activation = calcActivationOfOneWord(i)
            if (i.activation < lowest_activation) {
                lowest_activation = i.activation
                lowestActivationWord = i
            }

        }

        return lowestActivationWord

    }

    //calculate activation of one word
    private fun calcActivationOfOneWord(word: Word): Double{
        var activation = 0.0
        val decay = estimateDecay(word)
        for(i in word.encounters){
            val elapsedTimeSinceEncounter= (System.currentTimeMillis()-i).toDouble()
            activation+= elapsedTimeSinceEncounter.pow(decay)
        }

        return activation

    }

    //estimate the decay of one word pair
    private fun estimateDecay(word: Word): Double{
        val decay = C * exp(word.previous_activation)+ DEFAULT_ALPHA
        return decay
    }


}