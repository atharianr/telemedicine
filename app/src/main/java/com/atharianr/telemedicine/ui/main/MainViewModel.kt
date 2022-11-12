package com.atharianr.telemedicine.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.response.UserResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class MainViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getUserDetail(token: String): LiveData<ApiResponse<UserResponse>> =
        remoteDataSource.getUserDetail(token)

    suspend fun putTokenFCM(
        token: String,
        inputProfileRequest: InputProfileRequest
    ): ApiResponse<UserResponse> = remoteDataSource.putTokenFCM(token, inputProfileRequest)

    fun saveUserFcmToken(userId: String, fcmToken: String) {
        remoteDataSource.saveUserFcmToken(userId, fcmToken)
    }

    fun saveDoctorFcmToken(doctorId: String, fcmToken: String) {
        remoteDataSource.saveDoctorFcmToken(doctorId, fcmToken)
    }
}