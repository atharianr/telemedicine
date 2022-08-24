package com.atharianr.telemedicine.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class InputProfileRequest(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("gender")
    val gender: Int? = null,

    @field:SerializedName("birthdate")
    val birthdate: String? = null,

    @field:SerializedName("body_height")
    val bodyHeight: Int? = null,

    @field:SerializedName("body_weight")
    val bodyWeight: Int? = null,

    @field:SerializedName("blood_type")
    val bloodType: Int? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null
)
