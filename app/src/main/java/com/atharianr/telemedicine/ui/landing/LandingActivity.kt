package com.atharianr.telemedicine.ui.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityLandingBinding
import com.atharianr.telemedicine.ui.main.profile.InputProfileActivity
import com.atharianr.telemedicine.utils.Constant

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLandingBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        checkToken()
    }

    private fun checkToken() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString(Constant.TOKEN, null)

        if (token != null) {
            with(Intent(this, InputProfileActivity::class.java)) {
                putExtra(Constant.FROM_REGISTER, true)
                startActivity(this)
                finish()
            }
        }
    }
}