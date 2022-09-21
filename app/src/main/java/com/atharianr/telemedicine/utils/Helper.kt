package com.atharianr.telemedicine.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentTimeStamp(pattern: String = "yyyy-MM-dd HH:mm:ss"): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(Date())

fun getDateFromString(pattern: String, date: String): Date {
    val parser = SimpleDateFormat(pattern, Locale.getDefault())
    return parser.parse(date) ?: Date()
}

fun Date.toFormat(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}