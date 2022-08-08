package com.atharianr.telemedicine.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DoctorResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DoctorData>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DoctorData(

	@field:SerializedName("education")
	val education: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("specialist")
	val specialist: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("education_year")
	val educationYear: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
