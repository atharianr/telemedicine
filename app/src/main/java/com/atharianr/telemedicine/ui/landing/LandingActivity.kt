package com.atharianr.telemedicine.ui.landing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityLandingBinding
import com.atharianr.telemedicine.ui.landing.landing.LandingFragment

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLandingBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        loadFragment(LandingFragment())
    }

    private fun loadFragment(fragment: Fragment?) {
        if (fragment != null) {
            val tag = fragment::class.java.simpleName
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_landing, fragment, tag)
                .commit()
        }
    }
}