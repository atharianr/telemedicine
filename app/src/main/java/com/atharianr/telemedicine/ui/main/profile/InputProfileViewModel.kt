package com.atharianr.telemedicine.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.response.UserResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class InputProfileViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun inputProfile(
        token: String,
        inputProfileRequest: InputProfileRequest
    ): LiveData<ApiResponse<UserResponse>> =
        remoteDataSource.inputProfile(token, inputProfileRequest)
}