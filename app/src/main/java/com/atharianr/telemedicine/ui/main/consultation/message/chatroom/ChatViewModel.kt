package com.atharianr.telemedicine.ui.main.consultation.message.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.request.firebase.Chat
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class ChatViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun createChatRoom(doctorId: String, doctorName: String, doctorPhoto: String, userId: String) {
        remoteDataSource.createChatRoom(doctorId, doctorName, doctorPhoto, userId)
    }

    fun sendChat(doctorId: String, userId: String, chatBody: String) {
        remoteDataSource.sendChat(doctorId, userId, chatBody)
    }

    fun getChat(doctorId: String, userId: String): LiveData<ApiResponse<List<Chat>>> =
        remoteDataSource.getChat(doctorId, userId)
}