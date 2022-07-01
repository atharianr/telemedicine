package com.atharianr.telemedicine.data.source.remote.network

import com.atharianr.telemedicine.data.source.remote.request.LoginRequest
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.LoginResponse
import com.atharianr.telemedicine.data.source.remote.response.RegisterResponse
import com.atharianr.telemedicine.data.source.remote.response.UserResponse
import com.atharianr.telemedicine.data.source.remote.response.VerifyEmailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Accept: application/json")
    @POST("register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @Headers("Accept: application/json")
    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @Headers("Accept: application/json")
    @POST("email/verification-notification")
    fun verifyEmail(@Header("Authorization") token: String): Call<VerifyEmailResponse>

    @Headers("Accept: application/json")
    @GET("user")
    fun getUserDetail(@Header("Authorization") token: String): Call<UserResponse>
}