package com.atharianr.telemedicine.ui.main.consultation.doctor.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityDoctorProfileBinding
import com.atharianr.telemedicine.ui.main.consultation.message.chatroom.ChatActivity
import com.atharianr.telemedicine.utils.Constant

class DoctorProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDoctorProfileBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        binding.apply {
            btnChat.setOnClickListener {
                val intent = Intent(this@DoctorProfileActivity, ChatActivity::class.java)
                intent.putExtra(Constant.NAME, "Nama Dokter")
                startActivity(intent)
            }
        }
    }
}