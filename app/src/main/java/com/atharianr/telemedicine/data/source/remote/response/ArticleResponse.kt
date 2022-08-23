package com.atharianr.telemedicine.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: List<ArticleData>? = null
)

data class ArticleDetailResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: ArticleData? = null
)

data class ArticleData(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("definition")
    val definition: String? = null,

    @field:SerializedName("symptom")
    val symptom: String? = null,

    @field:SerializedName("complication")
    val complication: String? = null,

    @field:SerializedName("treatment")
    val treatment: String? = null,

    @field:SerializedName("diagnosys")
    val diagnosis: String? = null,

    @field:SerializedName("cause")
    val cause: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null
)
