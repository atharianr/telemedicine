package com.atharianr.telemedicine.data.source.remote.response.firebase

import com.atharianr.telemedicine.data.source.remote.request.firebase.Chat
import java.util.*

data class ChatResponse(
    val doctor_id: String? = null,
    val doctor_name: String? = null,
    val doctor_photo: String? = null,
    val user_id: String? = null,
    val time: String? = null,
    val chat: Map<String, Chat>? = null
)