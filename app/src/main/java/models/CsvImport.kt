package models

import com.example.cramdroid.R
import java.io.File

fun csvListSplitter(path: String): MutableList<List<String>> {
    // R.raw.dutch_words    // ??? added out, because did not work for me.
    val input = File(path).readLines()
    val lines = input.takeLast(input.count() - 1)
    val head = input.first().split(";")

    val listOfLists = mutableListOf<List<String>>()
    for (line in lines) listOfLists.add(line.split(";"))

    return listOfLists
}