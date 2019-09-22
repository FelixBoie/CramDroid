package classes

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.cramdroid.MainActivity

/*class MyNotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        println(NOTIFICATION)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                MainActivity::NOTIFICATION_CHANNEL_ID.toString(),
                "Penis",
                importance
            )
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        println(NOTIFICATION_ID)
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        assert(notificationManager != null)
        notificationManager.notify(id, notification)
    }

    companion object {
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
    }
}*/