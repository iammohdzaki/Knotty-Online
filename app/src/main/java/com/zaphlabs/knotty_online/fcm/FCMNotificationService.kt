package com.zaphlabs.knotty_online.fcm

import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMNotificationService : FirebaseMessagingService() {

    private val TAG = "MyFMService"
    private val DEFAULT_CHANNEL_ID = "default_01"
    private val NOTIFICATION_VIBRATION_PATTERN =
        longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
    var mNotificationManager: NotificationManager? = null


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
    }

}