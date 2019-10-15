package viewmodels

import android.content.Context
import android.os.Environment
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import classes.Encounter
import classes.TrialInformation
import classes.Word
import com.example.cramdroid.R
import models.SpacingModel
import models.csvListSplitter
import java.io.File
import models.loadDutEngWords
import java.io.FileInputStream
import java.io.FileReader
import kotlin.random.Random


class WordViewModel: ViewModel() {
    /*private val words: MutableLiveData<MutableList<Word>> by lazy {
        MutableLiveData<MutableList<Word>>().also{
            loadWords()
        }
    }*/
    val words = loadWords()
    val spacingModel = SpacingModel()
     var curr_word = getRandomWord()
    private val seenWords = MutableList<Word>(1){curr_word}


    /*fun getWords(): LiveData<MutableList<Word>> {
        return words
    }*/

    private fun loadWords(): MutableList<Word> {
        return loadDutEngWords()
    }

    private fun onCorrect(){

    }

    private fun onFalse(){

    }

//    fun updateWordList() {
//        words.removeIf{ it.english == curr_word.english }
//        curr_word.prev_seen = true
//        words.add(curr_word)
//    }

    fun getRandomWord(): Word {
        return words[Random.nextInt(words.size)]
    }




    fun updateSeenWords(currentWord:Word, currentTime: Long){
        //update model to current word
        curr_word = currentWord
        //add current word to seen words if isn't there already
        if (!curr_word.prev_seen){
            currentWord.prev_seen = true
            seenWords.add(curr_word)
            //words.removeIf{it.english == curr_word.english}
        }
        //add encounter of the word
        for (i in seenWords) {
            if (i.english == currentWord.english) {
                i.encounters.add(Encounter(currentWord.activation, spacingModel.trialInformation.reactionTime, currentTime.toFloat()))
            }
        }
    }
    fun askForNewWord(){
        //first
        var nextword : Word = spacingModel.selectAction(seenWords)

//        for(word in seenWords){
//            println("3 Word: " + word.english)
//            println("Activation: " + word.activation)
//            println("Decay: " + word.decay)
//            for (e in word.encounters){
//                println("Encounter: " + e.time_of_encounter  + " reaction time:  " + e.reaction_time + " activation " + e.decay)
//            }
//        }


        if (nextword.english == "new word"){ // IF ALL WORDS ARE ABOVE ACTIVATION THRESHOLD
            curr_word = getRandomWord()

        }else{
            curr_word = nextword
        }


    }



}