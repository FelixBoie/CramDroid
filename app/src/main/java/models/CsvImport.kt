package models

import classes.Word
import com.example.cramdroid.R
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

fun csvListSplitter(path: String): MutableList<List<String>> {
    // no longer needed I think ???
    // R.raw.dutch_words    // ??? added out, because did not work for me.
    val input = File(path).readLines()
    val lines = input.takeLast(input.count() - 1)
    val head = input.first().split(";")

    val listOfLists = mutableListOf<List<String>>()
    for (line in lines) listOfLists.add(line.split(";"))

    return listOfLists
}