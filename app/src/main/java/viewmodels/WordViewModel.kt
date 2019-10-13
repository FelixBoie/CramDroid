package viewmodels

import android.app.Application
import android.content.Context
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import classes.Word
import com.example.cramdroid.R
import models.csvListSplitter
import java.io.File
import models.loadDutEngWords
import java.io.FileInputStream
import java.io.FileReader
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

    //Felix added changed ViewModel() to AndroidViewModel, which knows the context
//?? felix added
class WordViewModel(application: Application) : AndroidViewModel(application) {


    /*private val words: MutableLiveData<MutableList<Word>> by lazy {
        MutableLiveData<MutableList<Word>>().also{
            loadWords()
        }
    }*/

    val words = loadWords()

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

        //??? Felix Added
    fun saveWords_felix (){

        var context  = getApplication<Application>().applicationContext
        var filename = "saveFileWords.csv"
        var fileContext = "Hello world123"
        context.openFileOutput(filename,Context.MODE_PRIVATE).use{it.write(fileContext.toByteArray())}
    }
        //??? Felix Added
    fun loadWords_felix(){
        var context  = getApplication<Application>().applicationContext
        var filename = "saveFileWords.csv"
        var fileContext = "Hello world123"
        val file = File(context.filesDir,filename)
        val contents = file.readText() // read file
        println(contents)
    }




    fun updateWordList() {
        words.removeIf{ it.english == curr_word.english }
        curr_word.prev_seen = true
        words.add(curr_word)
    }

    fun updateWord(): Word {
        return words[Random.nextInt(words.size)]
    }

    var curr_word = updateWord()

}