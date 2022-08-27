package com.atharianr.telemedicine.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.UserResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class MainViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getUserDetail(token: String): LiveData<ApiResponse<UserResponse>> =
        remoteDataSource.getUserDetail(token)
}