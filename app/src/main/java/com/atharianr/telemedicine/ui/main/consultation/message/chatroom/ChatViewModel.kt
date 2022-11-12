package com.atharianr.telemedicine.ui.main.consultation.message.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.request.FcmChatRequest
import com.atharianr.telemedicine.data.source.remote.request.firebase.Chat
import com.atharianr.telemedicine.data.source.remote.response.FcmResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class ChatViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun createChatRoom(
        doctorId: String,
        doctorName: String,
        doctorPhoto: String,
        userId: String,
        userName: String,
        userPhoto: String
    ) = remoteDataSource.createChatRoom(
        doctorId,
        doctorName,
        doctorPhoto,
        userId,
        userName,
        userPhoto
    )

    fun sendChat(
        doctorId: String,
        userId: String,
        chatBody: String,
        appType: String
    ): LiveData<Boolean> =
        remoteDataSource.sendChat(doctorId, userId, chatBody, appType)

    fun getChat(doctorId: String, userId: String): LiveData<ApiResponse<List<Chat>>> =
        remoteDataSource.getChat(doctorId, userId)

    fun sendFcmChat(
        apiKey: String,
        fcmChatRequest: FcmChatRequest
    ): LiveData<ApiResponse<FcmResponse>> = remoteDataSource.sendFcmChat(apiKey, fcmChatRequest)

    fun getUserFcmToken(userId: String) = remoteDataSource.getUserFcmToken(userId)

    fun getDoctorFcmToken(doctorId: String) = remoteDataSource.getDoctorFcmToken(doctorId)
}