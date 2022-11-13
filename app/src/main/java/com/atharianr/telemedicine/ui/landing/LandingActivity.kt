package com.atharianr.telemedicine.ui.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityLandingBinding
import com.atharianr.telemedicine.ui.main.MainActivity
import com.atharianr.telemedicine.ui.main.message_doctor.MessageDoctorActivity
import com.atharianr.telemedicine.utils.Constant

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLandingBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        if (getToken() != null && getDoctorId() == null) {
            with(Intent(this, MainActivity::class.java)) {
                startActivity(this)
                finish()
            }
        } else if (getToken() == null && getDoctorId() != null) {
            with(Intent(this, MessageDoctorActivity::class.java)) {
                startActivity(this)
                finish()
            }
        }
    }

    private fun getToken(): String? {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.TOKEN, null)
    }

    private fun getDoctorId(): String? {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.DOCTOR_ID, null)
    }
}