package com.atharianr.telemedicine.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.UserResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class ProfileViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getUserDetail(token: String): LiveData<ApiResponse<UserResponse>> =
        remoteDataSource.getUserDetail(token)
}