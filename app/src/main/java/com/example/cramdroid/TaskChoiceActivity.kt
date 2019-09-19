package com.example.cramdroid

import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationManagerCompat

class TaskChoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_choice)


    }

    fun startRecommendedTask(view: View) {
        val intent = Intent(this, StudyActivity::class.java)
        startActivity(intent)
    }


}
