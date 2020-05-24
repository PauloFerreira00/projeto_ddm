package br.com.farras.appzinho.services

import br.com.farras.appzinho.FarrasApplication
import br.com.farras.appzinho.helpers.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject


class MyFirebaseMessagingService(): FirebaseMessagingService() {

    private val notificationHelper: NotificationHelper by inject()

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        sendNotification(remoteMessage)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { notification ->
            if (notification.title.isNullOrEmpty().not() && notification.body.isNullOrEmpty().not()) {
                notificationHelper.show(notification.title!!, notification.body!!)
            }
        }

    }
}