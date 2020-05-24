package br.com.farras.appzinho.helpers


import android.app.Notification
import android.app.Notification.*
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import br.com.farras.appzinho.R

class NotificationHelper(val context: Context) {

    private lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel : NotificationChannel
    private val channelId = "br.com.farras.appzinho"
    private val description = "Farras App Notification "

    fun show(title: String, message: String) {
        val notification = buildNotification()
        setupLayoutFor(notification, title, message)

        notificationManager.notify(1, notification.build())
    }

    private fun setupLayoutFor(notification: Builder, title: String, description: String) {
        notification
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))
            .also {
                it.style = Notification.BigTextStyle().bigText(description)
            }
    }

    private fun buildNotification(): Builder {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_MAX)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            Builder(context, channelId)
        } else {
            Builder(context)
        }
    }
}