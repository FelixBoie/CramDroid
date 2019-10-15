package viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import classes.Word
import models.CSVImport2
import kotlin.random.Random


class WordViewModel(application: Application) : AndroidViewModel(application) {

    var words = loadWords()
    var curr_word = updateWord()



    private fun loadWords(): ArrayList<Word> {
        var test = CSVImport2()
        // check if a file in was already created, if yes load that, otherwise use the csv from res

        return test.general_readCsv(getApplication<Application>().applicationContext)
    }


    fun writeWordsToCsv(){
        var test = CSVImport2()
        test.writeCSV(getApplication<Application>().applicationContext,words)
    }

    private fun onCorrect(){

    }

    private fun onFalse(){

    }
    fun updateWordList() {
        words.removeIf { it.english == curr_word.english }
        curr_word.prev_seen = true
        words.add(curr_word)
    }
    fun updateWord(): Word {
        return words[Random.nextInt(words.size)]
    }


}