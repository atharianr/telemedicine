package com.atharianr.telemedicine.data.source.remote.request

data class InputProfileRequest(
    val name: String,
    val gender: Int,
    val birthdate: String,
    val bodyHeight: Int,
    val bodyWeight: Int,
    val bloodType: Int,
    val address: String,
)