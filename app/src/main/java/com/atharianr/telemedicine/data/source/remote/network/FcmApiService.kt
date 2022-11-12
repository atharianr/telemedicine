package com.atharianr.telemedicine.data.source.remote.network

import com.atharianr.telemedicine.data.source.remote.request.FcmChatRequest
import com.atharianr.telemedicine.data.source.remote.response.FcmResponse
import retrofit2.Call
import retrofit2.http.*

interface FcmApiService {
    @Headers("Accept: application/json")
    @POST("send")
    fun sendFcmChat(
        @Header("Authorization") apiKey: String,
        @Body fcmChatRequest: FcmChatRequest
    ): Call<FcmResponse>
}