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
import com.opencsv.CSVReader
import models.loadDutEngWords
import java.io.FileInputStream
import java.io.FileReader
import kotlin.random.Random


class WordViewModel: ViewModel() {
    private val words: MutableLiveData<List<Word>> by lazy {
        MutableLiveData<List<Word>>().also{
            loadWords()
        }
    }



    fun getWords(): LiveData<List<Word>> {
        return words
    }

    private fun loadWords(): List<Word> {
        return loadDutEngWords()
    }

    private fun onCorrect(){

    }

    private fun onFalse(){

    }

    fun updateWord(): Word {
        val words = loadWords()
        return words[Random.nextInt(words.size)]
    }

    var curr_word = updateWord()

}