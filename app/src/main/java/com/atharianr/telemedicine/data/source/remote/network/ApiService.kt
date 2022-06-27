package com.atharianr.telemedicine.data.source.remote.network

import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Accept: application/json")
    @POST("register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}