package com.atharianr.telemedicine.ui.main.consultation.message.chatroom

import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource

class ChatViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun createChatRoom(doctorId: String, userId: String) {
        remoteDataSource.createChatRoom(doctorId, userId)
    }

    fun sendChat(doctorId: String, userId: String, chatBody: String) {
        remoteDataSource.sendChat(doctorId, userId, chatBody)
    }
}