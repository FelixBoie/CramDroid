package viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import classes.Encounter
import classes.Word
import models.SpacingModel
import models.WorkWithCSV
import kotlin.random.Random


class WordViewModel(application: Application) : AndroidViewModel(application) {
    /*private val words: MutableLiveData<MutableList<Word>> by lazy {
        MutableLiveData<MutableList<Word>>().also{
            loadWords()
        }
    }*/
    val words = loadWords()
    val spacingModel = SpacingModel()
    var curr_word = getRandomWord()
    private val seenWords = MutableList<Word>(1){curr_word} // ??? maybe we can change this one to ArrayList, I do not know how to work with mutable list in java


    /*fun getWords(): LiveData<MutableList<Word>> {
        return words
    }*/

    private fun loadWords(): ArrayList<Word> {
        var test = WorkWithCSV()
        // check if a file in was already created, if yes load that, otherwise use the csv from res
        return test.general_readCsv(getApplication<Application>().applicationContext)
    }

    fun writeToCsvFile(){
        System.out.println("Hurra, wrote to csv file!!!!!!!!!!!!!!!!!!!!!!!")
        var test = WorkWithCSV()
        test.writeCSV(getApplication<Application>().applicationContext,words)
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
        //add encounter of the word
        for (i in seenWords) {
            if (i.english == currentWord.english) {
                i.encounters.add(Encounter(currentWord.activation, spacingModel.trialInformation.reactionTime, currentTime.toFloat()))
            }
        }
        //add current word to seen words if isn't there already
        if (!curr_word.prev_seen){
            currentWord.prev_seen = true
            seenWords.add(curr_word)
            //words.removeIf{it.english == curr_word.english}
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