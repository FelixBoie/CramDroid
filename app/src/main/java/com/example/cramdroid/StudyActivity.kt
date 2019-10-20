package com.example.cramdroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import classes.TrialInformation
import classes.Word
import viewmodels.WordViewModel

class StudyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)


        val model = ViewModelProviders.of(this).get(WordViewModel::class.java)
        var currentTime =  SystemClock.elapsedRealtime()// TODO: fix spacing
        println("!!!!!!!!!!!!!!!!!!!!!!!!!1" + currentTime)
        var actionConfirm = false
        var item = model.curr_word
        val itemText = findViewById<TextView>(R.id.study_item)
        val answer = findViewById<EditText>(R.id.study_answer)
        answer.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        val button = findViewById<Button>(R.id.study_submit_button)
        val feedback = findViewById<TextView>(R.id.study_feedback)
        //var trialInformation = TrialInformation(item, 0, false)
        /*model.getWords().observe(this, Observer<List<Word>> {
            words: List<Word>? ->  print(words)
        })*/

        //set current word to random word from word set
        model.curr_word = model.getRandomWord()
        item = model.curr_word
        itemText.text = item.english
        answer.setText("")
        feedback.visibility = View.VISIBLE
        feedback.text = item.dutch
        feedback.setTextColor(Color.BLUE)
        itemText.setTextColor(Color.BLUE)
        //model.updateWordList()
        answer.isEnabled = false
        button.text = "Next"

        button.setOnClickListener {
            itemText.setTextColor(Color.BLACK)

            // every time the button is pressed, add the information to the csv file
            model.writeToCsvFile()


            //currentTime =  SystemClock.elapsedRealtime()// TODO: fix spacing
            //println("!!!!!!!!!!!!!!!!!!!!!!!!!    " + currentTime)
            if (actionConfirm) {        //WHEN USER HAS TO TRANSLATE THE WORD
                model.updateSeenWords(item, currentTime)
                if (item.dutch == answer.text.toString().toLowerCase()){
                    feedback.text = "Correct!"
                    feedback.setTextColor(resources.getColor(R.color.colorCorrect))
                    answer.setTextColor(resources.getColor(R.color.colorCorrect))

                    //add the information of current trial to the model
                    model.spacingModel.trialInformation.addTrialInformation(item,
                        (SystemClock.elapsedRealtime()-currentTime).toFloat(), true)

                } else {
                    feedback.text = "False!"
                    feedback.setTextColor(resources.getColor(R.color.colorFalse))
                    answer.setText(item.dutch)
                    answer.setTextColor(resources.getColor(R.color.colorFalse))
                    //add the information of current trial to the model
                    model.spacingModel.trialInformation.addTrialInformation(item,(SystemClock.elapsedRealtime()-currentTime).toFloat(), false )
                }
                button.text = "Next"
                feedback.visibility = View.VISIBLE
                answer.isEnabled = false
                actionConfirm = false
                //ask the model for new word
                model.askForNewWord()
            } else {
//                println("1. " + model.curr_word.english +"  " + model.curr_word.activation + "  " + model.curr_word.encounters)
//                for (e in model.curr_word.encounters){
//                    println("encounters: " + e.activation + "  " + e.reaction_time + "   " +  + e.decay)
//                }
                //Update the current time
                currentTime = SystemClock.elapsedRealtime()
                //model.curr_word = model.updateWord()
                item = model.curr_word
                itemText.text = item.english
                answer.setText("")
                if (item.prev_seen){
                    actionConfirm = true
                    answer.isEnabled = true
                    answer.setTextColor(Color.BLACK)
                    button.text = "Submit"
                    feedback.visibility = View.INVISIBLE
                } else {                //ITEM WAS NOT SEEN BEFORE
                    feedback.visibility = View.VISIBLE
                    feedback.text = item.dutch
                    feedback.setTextColor(Color.BLUE)
                    itemText.setTextColor(Color.BLUE)
                    model.spacingModel.trialInformation.addTrialInformation(item,(SystemClock.elapsedRealtime()-currentTime).toFloat(), false )
                    model.updateSeenWords(item, currentTime)
                    //model.updateWordList()
                    answer.isEnabled = false
                    button.text = "Next"
                }
//                println("2. " + model.curr_word.english +"  " + model.curr_word.activation + "  " + model.curr_word.encounters)
//                for (e in model.curr_word.encounters){
//                    println("encounters: " + e.activation + "  " + e.reaction_time + "   " +  + e.decay)
//                }
                currentTime = SystemClock.elapsedRealtime()
            }


        }
    }
}


