package viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import classes.Encounter
import classes.Fact
import classes.Response
import classes.Word
import models.SpacingModel2
import models.WorkWithCSV
import models.WorkWithCSV2
import kotlin.random.Random

class WordViewModel2 (application: Application) : AndroidViewModel(application) {
    val spacingModel2 = SpacingModel2()
    init {
        loadWords() // updates the words
    }



    /*fun getWords(): LiveData<MutableList<Word>> {
        return words
    }*/

    private fun loadWords() {
        var test = WorkWithCSV2()
        // check if a file in was already created, if yes load that, otherwise use the csv from res
        spacingModel2.addAllFacts(test.initializeFacts_readInCsv(getApplication<Application>().applicationContext))
    }

    fun writeToCsvFile(){
//        System.out.println("Hurra, wrote to csv file!!!!!!!!!!!!!!!!!!!!!!!")
        var test = WorkWithCSV2()
    }

    private fun onCorrect(){

    }

    private fun onFalse(){

    }

    fun getFact(time:Long):Pair<Fact,Boolean>{
        // get a new fact, and if it has been already seen
        val fact = spacingModel2.get_next_fact(time)
        return fact
    }

    fun register_response(fact:Fact,time:Long,_reactionTime:Float,_correct:Boolean){
        spacingModel2.register_response(Response(fact,time, _reactionTime,_correct))
    }
}