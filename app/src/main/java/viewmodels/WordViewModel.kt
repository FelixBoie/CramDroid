package viewmodels

import android.content.Context
import android.os.Environment
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
import kotlin.random.Random


class WordViewModel: ViewModel() {
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