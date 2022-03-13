package com.atharianr.telemedicine.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.databinding.ActivityLandingBinding
import com.atharianr.telemedicine.ui.loginregister.LoginRegisterActivity
import com.atharianr.telemedicine.utils.Constant

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            val intent = Intent(this@LandingActivity, LoginRegisterActivity::class.java)

            btnRegister.setOnClickListener {
                intent.putExtra(Constant.TYPE, Constant.TYPE_REGISTER)
                startActivity(intent)
            }

            btnLogin.setOnClickListener {
                intent.putExtra(Constant.TYPE, Constant.TYPE_REGISTER)
                startActivity(intent)
            }
        }
    }
}