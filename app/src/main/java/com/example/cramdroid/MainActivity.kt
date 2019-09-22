package com.example.cramdroid

import android.content.Context
import android.content.Intent
import android.graphics.Color.argb
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import classes.NotifIntentService
import classes.NotifService
import java.util.*
import kotlin.concurrent.timerTask
import android.app.*
import android.os.SystemClock
import classes.MyNotificationPublisher
import android.R.drawable.ic_dialog_alert


class MainActivity : AppCompatActivity() {
    public val NOTIFICATION_CHANNEL_ID = "10001"
    private val default_notification_channel_id = "Penis"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //createNotificationChannel()
    }

    fun openTaskChoice(view: View) {
        val intent = Intent(this, TaskChoiceActivity::class.java)
        startActivity(intent)
    }

    fun settingsPress(view: View) {
        scheduleNotification(getNotification("This is Strange"), 20000)
        /*Intent(this, NotifService::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also {
            intent ->  sendBroadcast(intent)
        }*/
    }
    private fun scheduleNotification(notification: Notification, delay: Int) {
        val notificationIntent = Intent(this, MyNotificationPublisher::class.java)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        println("Scheduling...")
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    private fun getNotification(content: String): Notification {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentTitle("Scheduled Notification")
        builder.setContentText(content)
        builder.setSmallIcon(ic_dialog_alert)
        builder.setAutoCancel(true)
        builder.setContentIntent(pendingIntent)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
        println("built")
        return builder.build()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "That Name"
            val descriptionText = "CHannel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Penis", name, importance).apply {
                description = descriptionText
            }
            channel.enableLights(true)
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}

