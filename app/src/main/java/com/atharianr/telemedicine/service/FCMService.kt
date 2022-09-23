package com.atharianr.telemedicine.service

import android.content.Context
import android.util.Log
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.utils.Constant
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FCMService(private val remoteDataSource: RemoteDataSource) : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(FCMService::class.simpleName, token)
        sendTokenToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    private fun sendTokenToServer(token: String) {
        val bearerToken = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE).getString(Constant.TOKEN, "")
        if (bearerToken != null){
            val inputProfileRequest = InputProfileRequest(fcmToken = token)
            CoroutineScope(Dispatchers.IO).launch {
                remoteDataSource.putTokenFCM(bearerToken, inputProfileRequest)
            }
        }
    }
}