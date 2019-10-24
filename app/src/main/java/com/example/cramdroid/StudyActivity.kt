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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import viewmodels.WordViewModel
import viewmodels.WordViewModel2
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationCompat
import classes.*
import models.SchedullingModel
import models.WorkWithCSV2


class StudyActivity : AppCompatActivity() {
    val NOTIFICATION_CHANNEL_ID = "10001"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        val model = ViewModelProviders.of(this).get(WordViewModel2::class.java)

        var startOfTrialTime = SystemClock.elapsedRealtime() // saves the time the the trials starts
        var currentTime =  SystemClock.elapsedRealtime()
        var variableSelectingLayout = 1 // can be 0= show trial, 1 = learn trial and 2 feedback trial
        var item = model.getFact(currentTime,  Fact("bullshit","bullshit")) // just needs a fact that will never appear
        val itemText = findViewById<TextView>(R.id.study_item)
        val answer = findViewById<EditText>(R.id.study_answer)
        answer.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        val button = findViewById<Button>(R.id.study_submit_button)
        val feedback = findViewById<TextView>(R.id.study_feedback)
        val correction = findViewById<TextView>(R.id.study_correction)


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


        // ToDo: decide if we want to use prior response over learning sessions
        model.loadResponses()


        // closes the view after X time; needs to be called after the model
        val finishTime = 3L // in seconds //TIME FOR A SESSION
        val handler = Handler()
        handler.postDelayed(Runnable {
            // A study activation was successfully finished
            println("Stop Study Activity")

            //set next schedule
            val delay = scheduleNotification(getNotification("This would be a perfect time to study!"))
            println("delay "+ delay)

            // save to csv times
            model.WorkWithCSV.saveCurrentTimeAndSuggestedTime(this.applicationContext,SystemClock.elapsedRealtime(),delay)
            // save to csv responses
            model.writeToCsvFile(model.spacingModel2.responses)
            // write email, only if there are not more testing sessions
            if(delay<=0){
                sendOutputViaEmail(delay)
            }

            this.finish() }, finishTime * 1000)




        button.setOnClickListener {
            println("responses for after click:"+ model.spacingModel2.responses)

            // update clock
            currentTime = SystemClock.elapsedRealtime()


            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Click!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            // read in response to csv; just for savefy, if the user exists the studying too early
            model.writeToCsvFile(model.spacingModel2.responses)


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
                answer.showKeyboard()
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
                item = model.getFact(currentTime,item.first)
                println("new item"+item)
                // if the item is new, show a test, otherwise run learning session
                if (item.second){
                    //item is new
                    variableSelectingLayout = 0
                } else {
                    variableSelectingLayout = 1
                }
            }

            // save when a word is presented
            startOfTrialTime = SystemClock.elapsedRealtime()
        }


    }

    fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun scheduleNotification(notification: Notification):Int {
        // decide here between different schedule methods
        var SchedullingModel = SchedullingModel()

        // one learning session is finished, so retract that one
        SchedullingModel.getNumberOfTestsLeft(this.applicationContext)
        SchedullingModel.reduceNumberOfTestsLeft(this.applicationContext)

        var delay = SchedullingModel.getSchedullingInHours(this.applicationContext)

        println("Next schedule in (hours):$delay")

        delay *= 60 * 60 * 1000 // delay in milliseconds

//        // Just for testing, delete row later
//        delay = 1000

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

    fun sendOutputViaEmail(decay:Int) {
        // taken from https://www.youtube.com/watch?v=tZ2YEw6SoBU
        val recipientList = "fbfelix@web.de,e.n.meijer@student.rug.nl,S.Steffen.2@student.rug.nl" //add here your email address, with "," between them
        val recipients = recipientList.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray() // email addresses to sent to

        val subject = "UserModel_data"

        var test = WorkWithCSV2()

        val message = test.getCSVResponsesAsString(this.applicationContext)+";;;"+test.getCurrentTimeAndSuggestedTime(this.applicationContext)// reads in the output from the trial ToDo: needs also the suggested times and current times

        println("could read in the message")
        println(message)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        intent.type = "message/rfc822" // only use Email apps

        startActivity(Intent.createChooser(intent, "Choose an email client"))
    }
}


