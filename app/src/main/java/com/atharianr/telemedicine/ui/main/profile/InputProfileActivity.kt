package com.atharianr.telemedicine.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityInputProfileBinding
import com.atharianr.telemedicine.ui.main.MainActivity
import com.atharianr.telemedicine.utils.Constant


class InputProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInputProfileBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        val fromRegister = intent.getBooleanExtra(Constant.FROM_REGISTER, true)

        binding.apply {
            val gender = arrayOf("Laki-laki", "Perempuan")
            val genderArrayAdapter =
                ArrayAdapter(this@InputProfileActivity, R.layout.spinner, gender)
            spinnerGender.adapter = genderArrayAdapter

            val blood = arrayOf("A", "B", "AB", "O")
            val bloodArrayAdapter = ArrayAdapter(this@InputProfileActivity, R.layout.spinner, blood)
            spinnerBlood.adapter = bloodArrayAdapter

            btnSave.setOnClickListener {
                if (fromRegister) {
                    val intent = Intent(this@InputProfileActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    onBackPressed()
                }
            }
        }
    }
}