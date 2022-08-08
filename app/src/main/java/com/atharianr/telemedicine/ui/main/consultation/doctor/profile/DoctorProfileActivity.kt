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

        val name = intent.getStringExtra(Constant.DOCTOR_NAME)
        val sp = intent.getStringExtra(Constant.DOCTOR_SP)
        val edu = intent.getStringExtra(Constant.DOCTOR_EDU)
        val eduYear = intent.getStringExtra(Constant.DOCTOR_EDU_YEAR)
        val phone = intent.getStringExtra(Constant.DOCTOR_PHONE)

        binding.apply {
            tvName.text = name
            tvSpeciality.text = sp
            tvEducation.text = edu
            tvEducationYear.text = eduYear
            tvDoctorId.text = phone

            btnChat.setOnClickListener {
                with(Intent(this@DoctorProfileActivity, ChatActivity::class.java)) {
                    putExtra(Constant.NAME, name)
                    startActivity(this)
                }
            }
        }
    }
}