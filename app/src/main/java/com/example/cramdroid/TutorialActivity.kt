package com.example.cramdroid

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.NotificationCompat
import classes.Fact
import classes.StudyNotificationPublisher
import models.SchedullingModel

class TutorialActivity : AppCompatActivity() {
    val NOTIFICATION_CHANNEL_ID = "10001"
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
        var tut_state = 0

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
        cont.setOnClickListener {
            if (tut_state==0) {
                cont.isEnabled = false
                button.isEnabled = true
                tut_state+=1
                info.text="Whenever you are introduced to a new pair of items, the pair is shown " +
                        "in a blue-colored font, as seen above! At this point, you do not have to type " +
                        "any feedback in yet, but rather just try to remember the word pair and press " +
                        "the button right to it. Go on and click the 'Next'-Button now!"
            } else if (tut_state==6){
                cont.text = "Finish"
                info.text="Phew, that was quite a session! Now, last but not least, to know when your next session is due, " +
                        "a timed notification will appear on your android device. Whenever this appears, " +
                        "you know that it's time to study again and you can open the app by simply clicking on it! " +
                        "To finish the tutorial and show an example notification, just press 'Finish' below!"
                tut_state+=1
            } else if (tut_state==7) {
                val delay = scheduleNotification(getNotification("Horray, you have finished the Tutorial! Well done!"))
                this.finish()
            }
        }
        button.setOnClickListener {
            if (tut_state==1) {
                button.text = "Submit"
                itemText.setTextColor(Color.BLACK)
                feedback.visibility = View.INVISIBLE
                answer.isEnabled = true
                tut_state+=1
                info.text="Have you remembered the word pair? Good! Because now it is time for you to " +
                        "type in that translation! As you can see, CramDroid asks you for the Swahili translation " +
                        "to 'Welcome', so click on the 'Your Answer' field, type in 'Karibu' and press 'Submit'!"
            } else if (tut_state==2) {
                if(answer.text.toString().toLowerCase() == item.answer.toLowerCase()){
                    button.text = "Next"
                    feedback.text = "Correct!"
                    feedback.setTextColor(resources.getColor(R.color.colorCorrect))
                    feedback.visibility = View.VISIBLE
                    answer.setTextColor(resources.getColor(R.color.colorCorrect))
                    answer.isEnabled = false
                    correction.visibility = View.VISIBLE
                    correction.text = item.answer
                    tut_state+=1
                    info.text="Yaaay! You got it correct! And if you have it correct, CramDroid lets " +
                            "you know by telling you (and marking your answer green). Press 'Next' to " +
                            "get to the next word pair!"
                } else {
                    info.text="No, sorry, type in the CORRECT translation, which is 'Karibu' and " +
                            "then press 'Submit'!"
                }
            } else if (tut_state==3) {
                item = Fact("Learning", "Kujifunza")
                itemText.text = item.question
                itemText.setTextColor(Color.BLUE)
                answer.setText("")
                answer.setTextColor(Color.BLACK)
                answer.isEnabled = false
                feedback.text = item.answer
                feedback.setTextColor(Color.BLUE)
                correction.visibility =View.INVISIBLE
                button.text = "Next"
                tut_state += 1
                info.text = "Great, now that you have learned your first word in Swahili, it is time " +
                        "for another word pair. As you already now by know, new word pairs are being shown " +
                        "in blue and whenever you want to continue, you just have to press 'Next', so do that now!"
            } else if (tut_state==4) {
                itemText.setTextColor(Color.BLACK)
                answer.isEnabled = true
                feedback.visibility = View.INVISIBLE
                button.text="Submit"
                tut_state+=1
                info.text = "Again, you are being asked to provide the translation to the previously " +
                        "introduced word. However, this time we want to see what will happen if you " +
                        "type in the wrong translation. So, just type in any bogus answer that comes " +
                        "to your mind and press 'Submit'! Don't worry, we won't tell anyone if it's dirty! :)"
            } else if (tut_state==5){
                if (answer.text.toString().toLowerCase() == item.answer.toLowerCase()) {
                    info.text = "No, no, no! I mean, yes, it IS the correct translation, but you're " +
                            "supposed to enter a WRONG translation! So, try again and press 'Submit'!"
                } else {
                    button.text = "Next"
                    button.isEnabled = false
                    feedback.text = "False!"
                    feedback.setTextColor(resources.getColor(R.color.colorFalse))
                    feedback.visibility = View.VISIBLE
                    answer.setTextColor(resources.getColor(R.color.colorFalse))
                    answer.isEnabled = false
                    correction.visibility = View.VISIBLE
                    correction.text = item.answer
                    tut_state+=1
                    cont.isEnabled = true
                    info.text = "Well done, you're wrong! If you make a mistake, CramDroid lets you " +
                            "know by showing you a big fat 'False!' and marking your provided answer red. " +
                            "It also shows you what would have been the correct answer. Press the button " +
                            "below to continue."
                }
            }
        }
    }
    private fun scheduleNotification(notification: Notification):Int {
        val delay = 10

        //only do something if there is not a negative delay
        if (delay >= 0) {

            val notificationIntent = Intent(this, StudyNotificationPublisher::class.java)
            notificationIntent.putExtra(StudyNotificationPublisher.NOTIFICATION_ID, 1)
            notificationIntent.putExtra(StudyNotificationPublisher.NOTIFICATION, notification)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


            val futureInMillis = SystemClock.elapsedRealtime() + delay // delay in milliseconds
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            println("Scheduling...")
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
        }

        return delay
    }

    private fun getNotification(content: String): Notification {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentTitle("Studytime!")
        builder.setContentText(content)
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert)
        builder.setLights(Color.MAGENTA, 1000, 200)
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
        println("built")
        return builder.build()
    }

}
