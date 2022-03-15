package com.atharianr.telemedicine.ui.landing

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityLandingBinding
import com.atharianr.telemedicine.ui.loginregister.LoginRegisterActivity
import com.atharianr.telemedicine.utils.Constant

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLandingBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val appName =
                    getColoredSpanned("Tele", "#D44090") + getColoredSpanned("medicine", "#407CD4")
                tvAppName.text = Html.fromHtml(appName, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    private fun getColoredSpanned(text: String, color: String): String? {
        return "<font color=$color>$text</font>"
    }
}