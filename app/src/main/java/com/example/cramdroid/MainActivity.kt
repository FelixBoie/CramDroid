package com.example.cramdroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import android.app.*
import android.os.SystemClock
import android.R.drawable.ic_dialog_alert
import android.graphics.Color
import classes.StudyNotificationPublisher
import classes.Word
import models.SchedullingModel
import models.WorkWithCSV
import models.WorkWithCSV2


class MainActivity : AppCompatActivity() {
    public val NOTIFICATION_CHANNEL_ID = "10001"
    private val default_notification_channel_id = "Penis"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val words: List<Word>
        //createNotificationChannel()
    }

    fun openTaskChoice(view: View) {
        val intent = Intent(this, TaskChoiceActivity::class.java)
        startActivity(intent)
    }

    fun settingsPress(view: View) {
        scheduleNotification(getNotification("This would be a perfect time to study!"))
    }
    private fun scheduleNotification(notification: Notification) {
        // decide here between different schedule methods
        var SchedullingModel = SchedullingModel()
        SchedullingModel.updateLastTest()

        // schedule just for TESTING
//         var delay = SchedullingModel.nextMessageInMS_test // ??? needs to be changed, later but this helps with just keeoing it in the loop

        // schdedule for PERCENTILE Spacing
//        var delay = SchedullingModel.getNextMessageInXHours_percentileSpacing()

        // schedule for CONSTANT spacing
        var delay = SchedullingModel.nextMessageInXHours_constantSpacing(this.applicationContext)

        println("Next schedule in (hours):$delay")

        delay *= 60 * 60 * 1000 // delay in milliseconds

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
        SchedullingModel.reduceNumberOfTestsLeft(this.applicationContext)
    }

    private fun getNotification(content: String): Notification {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentTitle("Studytime!")
        builder.setContentText(content)
        builder.setSmallIcon(ic_dialog_alert)
        builder.setLights(Color.MAGENTA, 1000, 200)
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
        println("built")
        return builder.build()
    }

    // Save the important information for later analysis
    fun sendOutputViaEmail(view: View) {
        // taken from https://www.youtube.com/watch?v=tZ2YEw6SoBU
        val recipientList = "fbfelix@web.de,e.n.meijer@student.rug.nl,S.Steffen.2@student.rug.nl" //add here your email address, with "," between them
        val recipients = recipientList.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray() // email addresses to sent to

        val subject = "UserModel_data"

        var test = WorkWithCSV2()

        val message = test.getCSVResponsesAsString(this.applicationContext) // reads in the output from the trial

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

