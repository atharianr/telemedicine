package com.atharianr.telemedicine.ui.main.consultation.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.DoctorDetailResponse
import com.atharianr.telemedicine.data.source.remote.response.DoctorResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class DoctorViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getAllDoctors(token: String): LiveData<ApiResponse<DoctorResponse>> =
        remoteDataSource.getAllDoctors(token)

    fun getSearchDoctors(
        token: String,
        keyword: String,
        filter: String
    ): LiveData<ApiResponse<DoctorResponse>> =
        remoteDataSource.getSearchDoctors(token, keyword, filter)

    fun getDoctorDetail(
        token: String,
        doctorId: String
    ): LiveData<ApiResponse<DoctorDetailResponse>> =
        remoteDataSource.getDoctorDetail(token, doctorId)
}