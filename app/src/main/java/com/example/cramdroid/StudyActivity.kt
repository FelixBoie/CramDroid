package com.example.cramdroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import viewmodels.WordViewModel

class StudyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)


        // set up of viewModel
        val model = ViewModelProviders.of(this).get(WordViewModel::class.java)
        model.curr_word = model.updateWord()

        // set up text views
        val itemText = findViewById<TextView>(R.id.study_item)
        val answer = findViewById<EditText>(R.id.study_answer)
        answer.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        val feedback = findViewById<TextView>(R.id.study_feedback)

        // set up button
        val button = findViewById<Button>(R.id.study_submit_button)
        answer.isEnabled = false
        button.text = "Next"

        // set up variables
        var actionConfirm = false
        var item = model.curr_word



        // intializing
        itemText.text = item.swahili
        answer.setText("")
        feedback.visibility = View.VISIBLE
        feedback.text = item.english
        feedback.setTextColor(Color.BLUE)
        itemText.setTextColor(Color.BLUE)
        model.updateWordList()



        // clicked button for next/Submit
        button.setOnClickListener {
            itemText.setTextColor(Color.BLACK)


            //??? test if writing to csv works, should be applied, when you are going back to the prior page
            model.writeWordsToCsv()





            if (actionConfirm) {
                if (item.swahili == answer.text.toString().toLowerCase()){
                    feedback.text = "Correct!"
                    // This lead to errors for me, so I just directly set the colors, Felix ???
                    //feedback.setTextColor(resources.getColor(R.color.colorCorrect))
                    //answer.setTextColor(resources.getColor(R.color.colorCorrect))
                    feedback.setTextColor(Color.GREEN)
                    answer.setTextColor(Color.GREEN)
                } else {
                    feedback.text = "False!"
                    //feedback.setTextColor(resources.getColor(R.color.colorFalse))
                    feedback.setTextColor(Color.RED)
                    answer.setText(item.swahili)
                    //answer.setTextColor(resources.getColor(R.color.colorFalse))
                    answer.setTextColor(Color.RED)
                }
                button.text = "Next"
                feedback.visibility = View.VISIBLE
                answer.isEnabled = false
                actionConfirm = false
            } else {
                model.curr_word = model.updateWord()
                item = model.curr_word
                itemText.text = item.english
                answer.setText("")
                if (item.prev_seen){
                    actionConfirm = true
                    answer.isEnabled = true
                    answer.setTextColor(Color.BLACK)
                    button.text = "Submit"
                    feedback.visibility = View.INVISIBLE
                } else {
                    feedback.visibility = View.VISIBLE
                    feedback.text = item.swahili
                    feedback.setTextColor(Color.BLUE)
                    itemText.setTextColor(Color.BLUE)
                    model.updateWordList()
                    answer.isEnabled = false
                    button.text = "Next"
                }
            }
        }
    }
}
