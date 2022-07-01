package com.atharianr.telemedicine.ui.landing.verify

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.UserResponse
import com.atharianr.telemedicine.data.source.remote.response.VerifyEmailResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class VerifyViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun verifyEmail(token: String): LiveData<ApiResponse<VerifyEmailResponse>> =
        remoteDataSource.verifyEmail(token)

    fun getUserDetail(token: String): LiveData<ApiResponse<UserResponse>> =
        remoteDataSource.getUserDetail(token)
}