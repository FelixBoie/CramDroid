package com.example.cramdroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        var actionConfirm = true
        var item = model.curr_word
        val itemText = findViewById<TextView>(R.id.study_item)
        val answer = findViewById<EditText>(R.id.study_answer)
        val button = findViewById<Button>(R.id.study_submit_button)

        model.getWords().observe(this, Observer<List<Word>> {
            words: List<Word>? ->  print(words)
        })

        itemText.text = item.english

        button.setOnClickListener {

            if (actionConfirm) {
                if (item.dutch == answer.text.toString().toLowerCase()){
                    itemText.text = "Correct!"
                    itemText.setTextColor(resources.getColor(R.color.colorCorrect))
                    answer.setTextColor(resources.getColor(R.color.colorCorrect))
                } else {
                    itemText.text = "False!"
                    itemText.setTextColor(resources.getColor(R.color.colorFalse))
                    answer.setText(item.dutch)
                    answer.setTextColor(resources.getColor(R.color.colorFalse))
                }
                button.text = "Next"
                answer.isEnabled = false
                actionConfirm = false
            } else {
                model.curr_word = model.updateWord()
                item = model.curr_word
                itemText.text = item.english
                itemText.setTextColor(Color.BLACK)
                actionConfirm = true
                answer.isEnabled = true
                answer.setText("")
                answer.setTextColor(Color.BLACK)
                button.text = "Submit"
            }


        }
    }
}
