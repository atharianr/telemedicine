package com.atharianr.telemedicine.utils

import com.atharianr.telemedicine.BuildConfig

object Constant {
    const val NAME = "name"
    const val FROM_AUTH = "from_auth"
    const val API_BASE_URL = "http://202.157.176.228:9001/api/"
    const val API_FCM_BASE_URL = "https://fcm.googleapis.com/fcm/"
    const val FCM_API_KEY = BuildConfig.FCM_API_KEY
    const val TOKEN = "token"
    const val REQUEST_CODE = 100
    const val SELECT_PICTURE = 200
    const val THERE_IS_IMAGE = "there_is_image"
    const val LAST_CHAT = "last_chat"
    const val USER_DATA = "user_data"
    const val DEVICE_DATA = "device_data"
    const val FCM_TOKEN = "fcm_token"
    const val ON_NEW_TOKEN = "on_new_token"

    const val DOCTOR = "doctor"
    const val DOCTOR_ID = "doctor_id"
    const val DOCTOR_NAME = "doctor_name"
    const val DOCTOR_SP = "doctor_sp"
    const val DOCTOR_EDU = "doctor_edu"
    const val DOCTOR_EDU_YEAR = "doctor_edu_year"
    const val DOCTOR_EXP = "doctor_exp"
    const val DOCTOR_EXP_YEAR = "doctor_exp_year"
    const val DOCTOR_PHONE = "doctor_phone"
    const val DOCTOR_PHOTO = "doctor_photo"
    const val DOCTOR_FCM_TOKEN = "doctor_fcm_token"

    const val USER = "user"
    const val USER_ID = "user_id"
    const val USER_NAME = "user_name"
    const val USER_EMAIL = "user_email"
    const val USER_GENDER = "user_gender"
    const val USER_BIRTHDATE = "user_birthdate"
    const val USER_HEIGHT = "user_height"
    const val USER_WEIGHT = "user_weight"
    const val USER_BLOOD = "user_blood"
    const val USER_PHONE = "user_phone"
    const val USER_ADDRESS = "user_address"
    const val USER_PHOTO = "user_photo"
    const val USER_FCM_TOKEN = "user_fcm_token"
    const val USER_PHOTO_BASE_URL = "http://202.157.176.228:9001/storage/"

    const val ARTICLE_ID = "article_id"
    const val ARTICLE_TITLE = "article_title"
    const val ARTICLE_IMAGE = "article_image"
    const val ARTICLE_DEFINITION = "article_definition"
    const val ARTICLE_SYMPTOM = "article_symptom"
    const val ARTICLE_COMPLICATION = "article_complication"
    const val ARTICLE_DIAGNOSIS = "article_diagnosis"
    const val ARTICLE_TREATMENT = "article_treatment"

    const val WEB_URL = "web_url"
    const val WEB_URL_BPJS = "https://fiki.gerda.my.id"
    const val WEB_URL_APOTEK = "https://m.k24klik.com"
    const val WEB_URL_RS = "https://www.siloamhospitals.com"

    const val CHATROOM = "chatroom"
    const val CHAT = "chat"
}