package com.atharianr.telemedicine.ui.loginregister

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityLoginRegisterBinding
import com.atharianr.telemedicine.ui.loginregister.login.LoginFragment
import com.atharianr.telemedicine.ui.loginregister.register.RegisterFragment
import com.atharianr.telemedicine.utils.Constant

class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        when (intent.getIntExtra(Constant.TYPE, 0)) {
            Constant.TYPE_REGISTER -> {
                loadFragment(RegisterFragment())
            }

            Constant.TYPE_LOGIN -> {
                loadFragment(LoginFragment())
            }
        }

    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            val tag = fragment::class.java.simpleName
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_login, fragment, tag)
                .commit()
            return true
        }
        return false
    }
}