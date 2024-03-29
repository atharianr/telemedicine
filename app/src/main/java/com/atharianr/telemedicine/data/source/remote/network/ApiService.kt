package com.atharianr.telemedicine.data.source.remote.network

import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.request.LoginRequest
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.*
import retrofit2.Call
import retrofit2.Response
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
    @PUT("user/edit/fcm_token")
    suspend fun putTokenFCM(
        @Header("Authorization") token: String,
        @Body inputProfileRequest: InputProfileRequest
    ): Response<UserResponse>

    @Headers("Accept: application/json")
    @GET("doctor")
    fun getAllDoctors(@Header("Authorization") token: String): Call<DoctorResponse>

    @Headers("Accept: application/json")
    @GET("doctor/search")
    fun getSearchDoctors(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String,
        @Query("filter") filter: String
    ): Call<DoctorResponse>

    @Headers("Accept: application/json")
    @GET("doctor/{doctorId}")
    fun getDoctorDetail(
        @Header("Authorization") token: String,
        @Path("doctorId") doctorId: String
    ): Call<DoctorDetailResponse>

    @Headers("Accept: application/json")
    @GET("article")
    fun getAllArticles(@Header("Authorization") token: String): Call<ArticleResponse>

    @Headers("Accept: application/json")
    @GET("article/search")
    fun getSearchArticles(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String
    ): Call<ArticleResponse>

    @Headers("Accept: application/json")
    @GET("article/{articleId}")
    fun getArticleDetail(
        @Header("Authorization") token: String,
        @Path("articleId") articleId: String
    ): Call<ArticleDetailResponse>
}