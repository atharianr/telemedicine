package com.atharianr.telemedicine.ui.main.profile

import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.response.UserResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class ProfileViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    suspend fun putTokenFCM(
        token: String,
        inputProfileRequest: InputProfileRequest
    ): ApiResponse<UserResponse> = remoteDataSource.putTokenFCM(token, inputProfileRequest)
}