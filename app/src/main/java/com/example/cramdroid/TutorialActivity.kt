package com.example.cramdroid

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import classes.Fact

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        var item = Fact("Welcome", "Karibu")
        val itemText = findViewById<TextView>(R.id.tut_item)
        val answer = findViewById<EditText>(R.id.tut_answer)
        answer.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        val button = findViewById<Button>(R.id.tut_submit_button)
        val feedback = findViewById<TextView>(R.id.tut_feedback)
        val correction = findViewById<TextView>(R.id.tut_correction)
        val info = findViewById<TextView>(R.id.tut_info)
        val cont = findViewById<Button>(R.id.tut_continue)

        itemText.text = item.question
        answer.setText("")
        feedback.visibility = View.VISIBLE
        feedback.text = item.answer
        feedback.setTextColor(Color.BLUE)
        correction.visibility = View.INVISIBLE
        itemText.setTextColor(Color.BLUE)
        answer.isEnabled = false
        button.text = "Next"
        button.isEnabled = false
        info.text = "Welcome, or Karibu, to the CramDroid Tutorial! To make you comfortable using " +
                "this app, you will be introduced to its main features here. Continue the tutorial " +
                "by clicking the button below."

    }
}
