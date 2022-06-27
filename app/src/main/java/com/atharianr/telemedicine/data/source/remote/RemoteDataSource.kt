package com.atharianr.telemedicine.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atharianr.telemedicine.data.source.remote.network.ApiService
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.RegisterResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {
    fun register(registerRequest: RegisterRequest): LiveData<ApiResponse<RegisterResponse>> {
        val resultResponse = MutableLiveData<ApiResponse<RegisterResponse>>()

        apiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                when (response.code()) {
                    201 -> {
                        Log.d(TAG, "Register Success")
                        resultResponse.postValue(ApiResponse.success(response.body()))
                    }
                    else -> {
                        Log.d(TAG, "Error: ${response.message()}")
                        resultResponse.postValue(ApiResponse.error("Terjadi kesalahan, ulangi kembali."))
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d(TAG, t.message.toString())
                resultResponse.postValue(ApiResponse.error(t.message.toString()))
            }

        })

        return resultResponse
    }

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName
    }
}