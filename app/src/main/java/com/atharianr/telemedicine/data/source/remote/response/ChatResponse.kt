package com.atharianr.telemedicine.data.source.remote.response

import com.atharianr.telemedicine.data.source.remote.request.firebase.Chat

data class ChatResponse(
    val id_doctor: String? = null,
    val id_user: String? = null,
    val message: List<Chat>? = null,
)