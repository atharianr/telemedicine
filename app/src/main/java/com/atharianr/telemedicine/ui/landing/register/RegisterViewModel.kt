package com.atharianr.telemedicine.ui.landing.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.RegisterResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class RegisterViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun register(registerRequest: RegisterRequest): LiveData<ApiResponse<RegisterResponse>> =
        remoteDataSource.register(registerRequest)
}