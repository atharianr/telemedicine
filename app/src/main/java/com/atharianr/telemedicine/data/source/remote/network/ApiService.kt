package com.atharianr.telemedicine.data.source.remote.network

import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.request.LoginRequest
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.*
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

    @Headers("Accept: application/json")
    @PUT("user/edit")
    fun inputProfile(
        @Header("Authorization") token: String,
        @Body inputProfileRequest: InputProfileRequest
    ): Call<UserResponse>

    @Headers("Accept: application/json")
    @GET("doctor")
    fun getAllDoctors(): Call<DoctorResponse>

    @Headers("Accept: application/json")
    @GET("doctor/search")
    fun getSearchDoctors(
        @Query("keyword") keyword: String,
        @Query("filter") filter: String
    ): Call<DoctorResponse>

    @Headers("Accept: application/json")
    @GET("article")
    fun getAllArticles(): Call<ArticleResponse>

    @Headers("Accept: application/json")
    @GET("article/search")
    fun getSearchArticles(@Query("keyword") keyword: String): Call<ArticleResponse>

}