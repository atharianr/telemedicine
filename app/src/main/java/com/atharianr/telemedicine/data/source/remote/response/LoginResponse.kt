package com.atharianr.telemedicine.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: LoginData? = null,

    @field:SerializedName("token")
    val token: String? = null
)

data class LoginData(

    @field:SerializedName("birthdate")
    val birthdate: Any? = null,

    @field:SerializedName("address")
    val address: Any? = null,

    @field:SerializedName("gender")
    val gender: Any? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,

    @field:SerializedName("body_height")
    val bodyHeight: Any? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("blood_type")
    val bloodType: Any? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: Any? = null,

    @field:SerializedName("body_weight")
    val bodyWeight: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null
)
