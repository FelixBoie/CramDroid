package models
import android.os.SystemClock
import classes.Encounter
import classes.TrialInformation
import classes.Word
import java.lang.Math.abs
import java.lang.Math.log
import java.util.EnumSet.range
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.exp

class SpacingModel() {
    // var seenWords = MutableList<Word>()
    //var currentWord: Word = _word
    private var nextAction: Word = Word("new word", "new word")
    //TODO: Implement ENCOUNTERS
    var trialInformation = TrialInformation(nextAction, 0F, false)
   // # Model constants
   // val LOOKAHEAD_TIME = 15000
    val FORGET_THRESHOLD : Float = -0.8F
    val DEFAULT_ALPHA = 0.3
    val C : Float= 0.25F
    val F = 1.0


//    private fun setCurWord(word: Word){
//        currentWord = word
//    }

    fun selectAction(seen_words: MutableList<Word>): Word{
//        for(word in seen_words){
//            println("3 Word: " + word.english)
//            println("Activation: " + word.activation)
//            println("Decay: " + word.decay)
//            for (e in word.encounters){
//                println("Encounter: " + e.time_of_encounter  + "   " + e.reaction_time + "   " + e.activation)
//            }
//        }
        val lowestActivatedWord = calculateLowestActivations(seen_words)
        for (e in seen_words){
            println(e.english + " activation:  " + e.activation)
        }

        if(lowestActivatedWord.activation < FORGET_THRESHOLD ){
            return lowestActivatedWord
        }else{
            return nextAction
        }
    }

    //calculate Lowest activation of all seen words
    fun calculateLowestActivations(seen_words: MutableList<Word>): Word{
        var lowestActivationWord = Word("default", "default")
        lowestActivationWord.activation = Float.POSITIVE_INFINITY
        var lowest_activation  = Float.POSITIVE_INFINITY
        for (i in seen_words){
            i.activation = calcActivationOfOneWord(i)
            if (i.activation < lowest_activation) {
                println("activation: " + i.english + "  " + i.activation)
                lowest_activation = i.activation
                lowestActivationWord = i
            }

        }

        return lowestActivationWord

    }

    //calculate activation of one word
    private fun calcActivationOfOneWord(word: Word): Float{
        val decay = estimateDecay(word)
        word.decay = decay
        word.activation = calculate_activation_from_encounters(word.encounters, SystemClock.elapsedRealtime().toFloat())
        return word.activation

    }

    //estimate the decay of one word pair
    private fun estimateDecay(word: Word): Float{
        val decay = C * exp(word.previous_activation)+ estimateAlpha(word)
        return decay
    }

    //estimate the alpha
    fun estimateAlpha(word: Word): Float {

        //if less than 3 encounters, use default alpha
        if (word.encounters.size < 3){
            return DEFAULT_ALPHA.toFloat()
        }



        //fit the alpha
        var a_fit = word.previous_alpha
        var reading_time = get_reading_time(word.english)
        var estimated_reaction_time = estimate_reaction_time_from_activation(word.activation, reading_time)
        var estimated_difference = estimated_reaction_time - normalized_reaction_time()
       // declare a0 and a1
        var a0 = 0F
        var a1 = 0F

        if (estimated_difference < 0) {
            // Estimated RT was too short (estimated activation too high), so actual decay was larger
             a0 = a_fit
            a1 = (a_fit + 0.05).toFloat()
        }
        else {
            // Estimated RT was too long (estimated activation too low), so actual decay was smaller
            a0 = (a_fit - 0.05).toFloat()
            a1 = a_fit
        }

        // Binary search between previous fit and proposed alpha
        for (i in 1..6) {
           // Adjust all decays to use the new alpha
            var  a0_diff = a0 - a_fit
            var  a1_diff = a1 - a_fit

            var d_a0 = word.encounters
            var d_a1 = word.encounters
            for (e in d_a0){
                e.decay += a0_diff
            }
            for (e in d_a1){
                e.decay += a1_diff
            }
            // Calculate the reaction times from activation and compare against observed RTs
            if(word.encounters.size > 6) {
                var encounter_window =
                    word.encounters.subList(word.encounters.size - 5, word.encounters.size)
                var total_a0_error = calculate_predicted_reaction_time_error(
                    encounter_window,
                    d_a0,
                    reading_time
                ) ///!! TODO: calculate_precdicted_reaction_time_error()
                var total_a1_error =
                    calculate_predicted_reaction_time_error(encounter_window, d_a1, reading_time)

                //Adjust the search area based on the lowest total error
                var ac = (a0 + a1) / 2
                if (total_a0_error < total_a1_error) {
                    a1 = ac
                } else {
                    a0 = ac
                }
            }
            // The new alpha estimate is the average value in the remaining bracket
            return((a0 + a1) / 2)
        }
        return DEFAULT_ALPHA.toFloat()
    }

    fun get_reading_time(text: String): Float{
        var word_count = text.split(" ")

        if (word_count.size > 1) {
            var character_count = text.length
            var test:Float = (-157.9 + character_count * 19.5).toFloat()
            var test2 = maxOf(test, (300.0).toFloat())
            return test2
        }
        return((300).toFloat())
    }

    fun estimate_reaction_time_from_activation(activation: Float, readingTime: Float): Float{
        return ((F* exp(-activation) + (readingTime / 1000))*1000).toFloat()
    }

    fun normalized_reaction_time(): Float{
//       # type: (Response) -> float
//        """
//        Cut off extremely long responses to keep the reaction time within reasonable bounds
//        """
        var rt: Float = 0F
        if (trialInformation.correctNess){
        rt = trialInformation.reactionTime}
        else 60000
        val max_rt = get_max_reaction_time_for_fact(trialInformation.curWord)
        return(minOf(rt, max_rt))
    }

    fun get_max_reaction_time_for_fact(word:Word):Float{
//        # type: (Fact) -> float
//        """
//        Return the highest response time we can reasonably expect for a given fact
//        """
        val reading_time = get_reading_time(word.english)
        val max_rt = 1.5 * estimate_reaction_time_from_activation(FORGET_THRESHOLD, reading_time)
        return(max_rt).toFloat()

    }

    fun calculate_predicted_reaction_time_error(test_set: MutableList<Encounter>, decay_adjusted_encounters: MutableList<Encounter>,readingTime: Float) :Float{
//        # type: ([Encounter], [Encounter], Fact) -> float
//        """
//        Calculate the summed absolute difference between observed response times and those predicted based on a decay adjustment.
//        """
        var activations = mutableListOf<Float>()
        if(test_set.lastIndex > 0){
            for (e in 1..test_set.lastIndex)
            {
                activations.add(calculate_activation_from_encounters(decay_adjusted_encounters, test_set[e].time_of_encounter))

            }
        }else{
            return 0F
        }

        var rt = 0F
        var rt_errors = 0F
        for (a in 1..activations.lastIndex){
            rt = estimate_reaction_time_from_activation(activations[a],readingTime )
            rt_errors += abs(test_set[a].reaction_time - rt)
        }

        return rt_errors
    }

    fun calculate_activation_from_encounters(encounters: MutableList<Encounter>, time_of_encounter: Float): Float{
        var included_encounters = mutableListOf<Encounter>()
        included_encounters.add(Encounter(0F,0F,0F))
        included_encounters.removeAt(0)
            for (e in encounters){
                if (e.time_of_encounter < time_of_encounter){
                    included_encounters.add(e)
                }
            }

        if (included_encounters.size == 0 ){
            return(-9999999999999).toFloat()
        }

        var activation = 0.0

        for(e in included_encounters){
           activation += ((SystemClock.elapsedRealtime() - e.time_of_encounter)/1000).pow(-e.decay)
        }
//        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111    " + activation )
//
//        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111    " + log(activation) )

        return ln(activation).toFloat()
    }


}