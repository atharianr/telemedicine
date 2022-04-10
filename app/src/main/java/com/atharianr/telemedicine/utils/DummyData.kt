package com.atharianr.telemedicine.utils

import com.atharianr.telemedicine.data.source.local.entity.ArticleEntity
import com.atharianr.telemedicine.data.source.local.entity.DoctorEntity
import com.atharianr.telemedicine.data.source.local.entity.MessageEntity

object DummyData {
    fun getMessage(): List<MessageEntity> {
        return listOf(
            MessageEntity(
                "Atharian Rahmadani",
                "halo..",
                "10:20 PM",
                "https://avatars.githubusercontent.com/u/50353365?v=4"
            ),
            MessageEntity(
                "Elon Musk",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                "12:24 AM",
                "https://image.cnbcfm.com/api/v1/image/106926995-1628885360355-elon2.jpg?v=1641217650"
            )
        )
    }

    fun getDoctor(): List<DoctorEntity> {
        return listOf(
            DoctorEntity(
                "Atharian Rahmadani",
                "Dokter Umum",
                95,
                25000,
                "https://avatars.githubusercontent.com/u/50353365?v=4"
            ),
            DoctorEntity(
                "Elon Musk",
                "Psikolog",
                90,
                35000,
                "https://image.cnbcfm.com/api/v1/image/106926995-1628885360355-elon2.jpg?v=1641217650"
            )
        )
    }

    fun getArticle(): List<ArticleEntity> {
        return listOf(
            ArticleEntity("AIDS"),
            ArticleEntity("Amnesia"),
            ArticleEntity("Batu Ginjal"),
        )
    }
}