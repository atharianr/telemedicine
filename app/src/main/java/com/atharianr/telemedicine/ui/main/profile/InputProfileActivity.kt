package com.atharianr.telemedicine.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityInputProfileBinding
import com.atharianr.telemedicine.ui.main.MainActivity

class InputProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInputProfileBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        binding.apply {
            btnSave.setOnClickListener {
                val intent = Intent(this@InputProfileActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}