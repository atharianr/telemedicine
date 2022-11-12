package com.atharianr.telemedicine.ui.main.consultation.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.firebase.ChatResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class MessageViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getRecentChat(userId: String): LiveData<ApiResponse<List<ChatResponse>>> =
        remoteDataSource.getRecentChat(userId)

    fun getRecentChatDoctor(doctorId: String): LiveData<ApiResponse<List<ChatResponse>>> =
        remoteDataSource.getRecentChatDoctor(doctorId)
}