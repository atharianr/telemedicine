package com.atharianr.telemedicine.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.ui.main.consultation.message.chatroom.ChatActivity
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.util.*

class FCMService : FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var intent: Intent

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(FCMService::class.simpleName, token)
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE) ?: return
        sharedPref.edit().putString(Constant.FCM_TOKEN, token).apply()
        sendBroadcast(Intent(Constant.ON_NEW_TOKEN).putExtra(Constant.FCM_TOKEN, token))
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(FCMService::class.simpleName, "onMessageReceived()")
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val jsonObject = JSONObject(message.data[NOTIFICATION].toString())
        when (jsonObject.getString(NOTIFICATION_TYPE)) {
            TYPE_CHAT -> {
                val body = jsonObject.getString(MESSAGE)
                val doctorId = jsonObject.getString(Constant.DOCTOR_ID)
                val doctorName = jsonObject.getString(Constant.DOCTOR_NAME)
                val doctorPhoto = jsonObject.getString(Constant.DOCTOR_PHOTO)
                buildChatNotification(doctorId, doctorName, doctorPhoto, body)
            }
        }
    }

    private fun buildChatNotification(
        doctorId: String,
        doctorName: String,
        doctorPhoto: String,
        body: String
    ) {
        try {
            val notificationId = doctorId.toInt()

            intent = Intent(this, ChatActivity::class.java).apply {
                putExtra(Constant.DOCTOR_ID, doctorId)
                putExtra(Constant.DOCTOR_NAME, doctorName)
                putExtra(Constant.DOCTOR_PHOTO, doctorPhoto)
            }

            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            val sender = Person.Builder()
                .setName(doctorName)
                .build()

            val futureTarget = Glide.with(this)
                .asBitmap()
                .circleCrop()
                .load(doctorPhoto)
                .submit()

            val bitmap = futureTarget.get()

            val notificationBuilder = NotificationCompat.Builder(this@FCMService, CHANNEL_ID_CHAT)
                .setSmallIcon(R.drawable.ic_telemedicine_logo)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setLargeIcon(bitmap)
                .setStyle(
                    NotificationCompat.MessagingStyle(sender)
                        .addMessage(body, Date().time, sender)
                )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID_CHAT,
                    CHANNEL_NAME_CHAT,
                    NotificationManager.IMPORTANCE_HIGH
                )

                notificationBuilder.setChannelId(CHANNEL_ID_CHAT)
                notificationManager.createNotificationChannel(channel)
            }

            Glide.with(this).clear(futureTarget)
            notificationManager.notify(notificationId, notificationBuilder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val NOTIFICATION = "notification"
        private const val CHANNEL_ID_CHAT = "channel_id_chat"
        private const val CHANNEL_NAME_CHAT = "channel_name_chat"
        private const val NOTIFICATION_TYPE = "notification_type"
        private const val MESSAGE = "message"
        private const val TYPE_CHAT = "chat"
    }
}