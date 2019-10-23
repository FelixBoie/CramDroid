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
import classes.Response
import classes.TrialInformation
import classes.Word
import viewmodels.WordViewModel
import viewmodels.WordViewModel2

class StudyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)


        val model = ViewModelProviders.of(this).get(WordViewModel2::class.java)
        var startOfTrialTime = SystemClock.elapsedRealtime() // saves the time the the trials starts
        var currentTime =  SystemClock.elapsedRealtime()
        var variableSelectingLayout = 1 // can be 0= show trial, 1 = learn trial and 2 feedback trial
        var item = model.getFact(currentTime)
        val itemText = findViewById<TextView>(R.id.study_item)
        val answer = findViewById<EditText>(R.id.study_answer)
        answer.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        val button = findViewById<Button>(R.id.study_submit_button)
        val feedback = findViewById<TextView>(R.id.study_feedback)
        val correction = findViewById<TextView>(R.id.study_correction)
        //var trialInformation = TrialInformation(item, 0, false)
        /*model.getWords().observe(this, Observer<List<Word>> {
            words: List<Word>? ->  print(words)
        })*/

        //set current word to random word from word set
        itemText.text = item.first.question
        answer.setText("")
        feedback.visibility = View.VISIBLE
        feedback.text = item.first.answer
        feedback.setTextColor(Color.BLUE)
        correction.visibility = View.INVISIBLE
        itemText.setTextColor(Color.BLUE)
        //model.updateWordList()
        answer.isEnabled = false
        button.text = "Next"
        itemText.setTextColor(Color.BLACK)



        button.setOnClickListener {
            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Click!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")


            if (variableSelectingLayout==0){
                // show a test session
                // when the user sees question and answer

                //Set up the right layout
                // set question (blue or black)
                itemText.text = item.first.question
                itemText.setTextColor(Color.BLUE)

                // set feedback (Correct or False)
                feedback.visibility = View.INVISIBLE
                feedback.text = ""
                feedback.setTextColor(Color.BLUE)

                // set correction: the correct answer
                correction.visibility = View.VISIBLE
                correction.text = item.first.answer
                correction.setTextColor(Color.BLUE)

                // set answer
                answer.setText("")
                answer.isEnabled = false
                answer.setTextColor(Color.BLACK)

                //set button
                button.text = "Next"

                variableSelectingLayout = 1 // to to learning session

            } else if(variableSelectingLayout==1) {
                // show a learning session

                // save when a word is presented
                startOfTrialTime = SystemClock.elapsedRealtime()

                //Set up the right layout
                // set question (blue or black)
                itemText.text = item.first.question
                itemText.setTextColor(Color.BLACK)

                // set feedback (Correct or False)
                feedback.visibility = View.INVISIBLE
                feedback.text = ""
                feedback.setTextColor(Color.BLACK)

                // set correction, so correct answer
                correction.visibility = View.INVISIBLE
                correction.text = item.first.answer
                correction.setTextColor(Color.BLUE)

                // set answer
                answer.setText("")
                answer.isEnabled = true
                answer.setTextColor(Color.BLACK)

                //set button
                button.text = "Submit"
                variableSelectingLayout = 2 // go to feedback afterwards
            } else {
                // show a feedback session

                itemText.text = item.first.question
                itemText.setTextColor(Color.BLACK)

                // set feedback (Correct or False)
                feedback.visibility = View.VISIBLE
                feedback.text = item.first.answer
                feedback.setTextColor(Color.BLUE)

                // set correction: the correct answer
                correction.visibility = View.VISIBLE
                correction.text = item.first.answer
                correction.setTextColor(Color.BLACK)


                // set answer
                answer.isEnabled = false

                //set button
                button.text = "Next"

                // updata current time
                currentTime = SystemClock.elapsedRealtime()

                // if the user was correct
                if (item.first.answer == answer.text.toString().toLowerCase()){
                    // set feedback
                    feedback.text = "Correct!"
                    feedback.setTextColor(resources.getColor(R.color.colorCorrect))
                    // set users answer
                    answer.setTextColor(resources.getColor(R.color.colorCorrect))
                    model.register_response(item.first, currentTime,(currentTime-startOfTrialTime).toFloat(),true)
                } else {// if the user was incorrect
                    feedback.text = "False!"
                    feedback.setTextColor(resources.getColor(R.color.colorFalse))
                    // set users answer
                    answer.setTextColor(resources.getColor(R.color.colorFalse))
                    model.register_response(item.first,currentTime, (currentTime-startOfTrialTime).toFloat(),false)

                }

                // get a new item
                item = model.getFact(currentTime)
                println("new item"+item)
                // if the item is new, show a test, otherwise run learning session
                if (item.second){
                    //item is new
                    variableSelectingLayout = 0
                } else {
                    variableSelectingLayout = 1
                }
            }
        }


    }

}


