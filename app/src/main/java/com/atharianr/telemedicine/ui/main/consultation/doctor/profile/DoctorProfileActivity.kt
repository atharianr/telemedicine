package com.atharianr.telemedicine.ui.main.consultation.doctor.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityChatBinding
import com.atharianr.telemedicine.databinding.ActivityDoctorProfileBinding

class DoctorProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDoctorProfileBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)


    }
}