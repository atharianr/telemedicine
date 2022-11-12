package com.atharianr.telemedicine.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class FcmChatRequest(
    @field:SerializedName("registration_ids")
    val registrationIds: List<String?>? = null,

    @field:SerializedName("data")
    val data: Data? = null
)

data class Notification(

    @field:SerializedName("notification_type")
    val notificationType: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("user_name")
    val userName: String? = null,

    @field:SerializedName("user_photo")
    val userPhoto: String? = null,

    @field:SerializedName("doctor_id")
    val doctorId: String? = null,

    @field:SerializedName("doctor_name")
    val doctorName: String? = null,

    @field:SerializedName("doctor_photo")
    val doctorPhoto: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class Data(

    @field:SerializedName("notification")
    val notification: Notification? = null
)
