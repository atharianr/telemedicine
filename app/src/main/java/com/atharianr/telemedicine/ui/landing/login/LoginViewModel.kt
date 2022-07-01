package com.atharianr.telemedicine.ui.landing.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.request.LoginRequest
import com.atharianr.telemedicine.data.source.remote.response.LoginResponse
import com.atharianr.telemedicine.data.source.remote.response.UserResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class LoginViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun login(loginRequest: LoginRequest): LiveData<ApiResponse<LoginResponse>> =
        remoteDataSource.login(loginRequest)

    fun getUserDetail(token: String): LiveData<ApiResponse<UserResponse>> =
        remoteDataSource.getUserDetail(token)
}