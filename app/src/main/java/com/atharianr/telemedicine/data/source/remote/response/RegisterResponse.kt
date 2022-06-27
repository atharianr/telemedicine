package com.atharianr.telemedicine.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)

data class Data(

	@field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null
)
