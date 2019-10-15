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
import models.spacingModel


class MainActivity : AppCompatActivity() {
    val NOTIFICATION_CHANNEL_ID = "10001"
    private val default_notification_channel_id = "Penis"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    // pressed start button
    fun openTaskChoice(view: View) {
        val intent = Intent(this, TaskChoiceActivity::class.java)
        startActivity(intent)
    }

    // pressed Settings button
    fun settingsPress(view: View) {
        scheduleNotification(getNotification("This would be a perfect time to study!"))
    }

    //sets the notification
    private fun scheduleNotification(notification: Notification) {
        val notificationIntent = Intent(this, StudyNotificationPublisher::class.java)
        notificationIntent.putExtra(StudyNotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(StudyNotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //calculate the delay with the spacing model
        var spacingModel = spacingModel()
        spacingModel.updateLastTest() // update to the current time
        // println(spacingModel.getNextMessageInXHours())
        // println(spacingModel.nextMessageTimePoint.toString())
        val delay = spacingModel.nextMessageInMS_test // only for testing
        //val delay = spacingModel.nextMessageInXHours*60*60*1000 // delay in milliseconds

        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        println("Scheduling...")
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    //creates notification
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

}

