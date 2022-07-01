package com.atharianr.telemedicine.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class VerifyEmailResponse(

	@field:SerializedName("message")
	val message: String? = null
)
