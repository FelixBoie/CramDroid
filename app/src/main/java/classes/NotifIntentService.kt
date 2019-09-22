package classes

import android.R
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.os.Process
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.widget.Toast
import com.example.cramdroid.MainActivity
import java.util.*
import kotlin.concurrent.timerTask

class NotifService: Service() {

    private var serviceLooper: Looper? = null


    override fun onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        fun createNotificationChannel() {
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

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        val delayedNotification = timerTask() {

            val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

            val builder = NotificationCompat.Builder(applicationContext, "Penis")
                .setSmallIcon(R.drawable.ic_dialog_alert)
                .setContentTitle("A perfect time to study!")
                .setContentText("Hey, knowledge is awaiting you!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLights(Color.argb(100, 255, 0, 255), 1000, 200)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(applicationContext)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }
        }
        val timer = Timer()
        createNotificationChannel()
        timer.schedule(delayedNotification, 10000)


        // If we get killed, after returning from here, restart
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }
}


class NotifIntentService: IntentService("HelloIntentService") {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun onHandleIntent(intent: Intent?) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        try {
            //Thread.sleep(50000)
            val delayedNotification = timerTask() {

                val pendingIntent: PendingIntent = PendingIntent.getActivity(this@NotifIntentService, 0, intent, 0)

                val builder = NotificationCompat.Builder(this@NotifIntentService, "Penis")
                    .setSmallIcon(R.drawable.ic_dialog_alert)
                    .setContentTitle("A perfect time to study!")
                    .setContentText("Hey, knowledge is awaiting you!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setLights(Color.argb(100, 255, 0, 255), 1000, 200)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

                with(NotificationManagerCompat.from(this@NotifIntentService)) {
                    // notificationId is a unique int for each notification that you must define
                    notify(1, builder.build())
                }
            }
            val timer = Timer()
            timer.schedule(delayedNotification, 10000)

        } catch (e: InterruptedException) {
            // Restore interrupt status.
            Thread.currentThread().interrupt()
        }

    }
}


private const val TAG = "MyBroadcastReceiver"

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val delayedNotification = timerTask() {

            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val builder = NotificationCompat.Builder(context, "Penis")
                .setSmallIcon(R.drawable.ic_dialog_alert)
                .setContentTitle("A perfect time to study!")
                .setContentText("Hey, knowledge is awaiting you!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLights(Color.argb(100, 255, 0, 255), 1000, 200)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }
        }
        val timer = Timer()
        timer.schedule(delayedNotification, 10000)
    }
}