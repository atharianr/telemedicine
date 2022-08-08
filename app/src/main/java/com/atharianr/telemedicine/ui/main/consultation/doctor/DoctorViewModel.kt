package com.atharianr.telemedicine.ui.main.consultation.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.DoctorResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class DoctorViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getAllDoctors(): LiveData<ApiResponse<DoctorResponse>> = remoteDataSource.getAllDoctors()
}