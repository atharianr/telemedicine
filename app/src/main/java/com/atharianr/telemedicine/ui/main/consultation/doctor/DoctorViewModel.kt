package com.atharianr.telemedicine.ui.main.consultation.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.DoctorDetailResponse
import com.atharianr.telemedicine.data.source.remote.response.DoctorResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class DoctorViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getAllDoctors(): LiveData<ApiResponse<DoctorResponse>> = remoteDataSource.getAllDoctors()
    fun getSearchDoctors(keyword: String, filter: String): LiveData<ApiResponse<DoctorResponse>> =
        remoteDataSource.getSearchDoctors(keyword, filter)

    fun getDoctorDetail(doctorId: String): LiveData<ApiResponse<DoctorDetailResponse>> =
        remoteDataSource.getDoctorDetail(doctorId)
}