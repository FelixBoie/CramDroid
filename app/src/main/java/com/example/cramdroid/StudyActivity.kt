package com.example.cramdroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import classes.Word
import viewmodels.WordViewModel

class StudyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)


        val model = ViewModelProviders.of(this).get(WordViewModel::class.java)

        var actionConfirm = false
        var item = model.curr_word
        val itemText = findViewById<TextView>(R.id.study_item)
        val answer = findViewById<EditText>(R.id.study_answer)
        answer.inputType = 0x21
        val button = findViewById<Button>(R.id.study_submit_button)
        val feedback = findViewById<TextView>(R.id.study_feedback)

        /*model.getWords().observe(this, Observer<List<Word>> {
            words: List<Word>? ->  print(words)
        })*/

        model.curr_word = model.updateWord()
        item = model.curr_word
        itemText.text = item.english
        answer.setText("")
        feedback.visibility = View.VISIBLE
        feedback.text = item.dutch
        feedback.setTextColor(Color.BLUE)
        itemText.setTextColor(Color.BLUE)
        model.updateWordList()
        answer.isEnabled = false
        button.text = "Next"

        button.setOnClickListener {
            itemText.setTextColor(Color.BLACK)
            if (actionConfirm) {
                if (item.dutch == answer.text.toString().toLowerCase()){
                    feedback.text = "Correct!"
                    feedback.setTextColor(resources.getColor(R.color.colorCorrect))
                    answer.setTextColor(resources.getColor(R.color.colorCorrect))
                } else {
                    feedback.text = "False!"
                    feedback.setTextColor(resources.getColor(R.color.colorFalse))
                    answer.setText(item.dutch)
                    answer.setTextColor(resources.getColor(R.color.colorFalse))
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
                    feedback.text = item.dutch
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
