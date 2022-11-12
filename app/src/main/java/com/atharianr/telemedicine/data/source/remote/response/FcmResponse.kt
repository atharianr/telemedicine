package com.atharianr.telemedicine.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class FcmResponse(

    @field:SerializedName("canonical_ids")
    val canonicalIds: Int? = null,

    @field:SerializedName("success")
    val success: Int? = null,

    @field:SerializedName("failure")
    val failure: Int? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null,

    @field:SerializedName("multicast_id")
    val multicastId: Long? = null
)

data class ResultsItem(

    @field:SerializedName("message_id")
    val messageId: String? = null
)
