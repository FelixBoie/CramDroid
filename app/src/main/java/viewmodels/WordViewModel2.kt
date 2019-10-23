package viewmodels

import android.app.Application
import android.content.Context
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
    var WorkWithCSV = WorkWithCSV2()
    init {
        loadFacts() // updates the words
    }


    private fun loadFacts() {
        // facts are working, responses can only be added later
        spacingModel2.addAllFacts(WorkWithCSV.readInFacts(getApplication<Application>().applicationContext))
    }
    fun loadResponses(){
        spacingModel2.addAllResponses(WorkWithCSV.readInPriorResponses(getApplication<Application>().applicationContext))
    }

    fun writeToCsvFile(responses:ArrayList<Response>){
        System.out.println("Hurra, wrote to csv file!!!!!!!!!!!!!!!!!!!!!!!")
        var test = WorkWithCSV2()
        test.writeCSV(getApplication<Application>().applicationContext, responses)
    }

    private fun onCorrect(){

    }

    private fun onFalse(){

    }

    fun getFact(time:Long, previous_fact:Fact):Pair<Fact,Boolean>{
        // get a new fact, and if it has been already seen
        val fact = spacingModel2.get_next_fact(time,previous_fact)
        return fact
    }

    fun register_response(fact:Fact,time:Long,_reactionTime:Float,_correct:Boolean){
        spacingModel2.register_response(Response(fact,time, _reactionTime,_correct))
    }
}