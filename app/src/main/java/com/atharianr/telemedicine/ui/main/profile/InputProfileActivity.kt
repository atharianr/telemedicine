package com.atharianr.telemedicine.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityInputProfileBinding
import com.atharianr.telemedicine.ui.main.MainActivity
import com.atharianr.telemedicine.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel


class InputProfileActivity : AppCompatActivity() {

    private var _binding: ActivityInputProfileBinding? = null
    private val binding get() = _binding as ActivityInputProfileBinding

    private val inputProfileViewModel: InputProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInputProfileBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        val fromAuth = intent.getBooleanExtra(Constant.FROM_AUTH, true)
        val token = intent.getStringExtra(Constant.TOKEN)
        var name = intent.getStringExtra(Constant.NAME)

        binding.apply {
            val genderArray = arrayOf("Laki-laki", "Perempuan")
            val genderArrayAdapter =
                ArrayAdapter(this@InputProfileActivity, R.layout.spinner, genderArray)
            spinnerGender.adapter = genderArrayAdapter

            val bloodArray = arrayOf("A", "B", "AB", "O")
            val bloodArrayAdapter =
                ArrayAdapter(this@InputProfileActivity, R.layout.spinner, bloodArray)
            spinnerBlood.adapter = bloodArrayAdapter

            etName.setText(name)

            btnSave.setOnClickListener {
                name = etName.text.toString()
                val gender = spinnerGender.selectedItemPosition
                val birthdate = etBirthdate.text.toString()
                var bodyHeight = etBodyHeight.text.toString()
                var bodyWeight = etBodyWeight.text.toString()
                val bloodType = spinnerBlood.selectedItemPosition
                val address = etAddress.text.toString()

                if (bodyHeight == "") {
                    bodyHeight = "0"
                }

                if (bodyWeight == "") {
                    bodyWeight = "0"
                }

                val inputProfileRequest = InputProfileRequest(
                    name!!,
                    gender,
                    birthdate,
                    bodyHeight.toInt(),
                    bodyWeight.toInt(),
                    bloodType,
                    address
                )

                inputProfile(token, inputProfileRequest, fromAuth)
                isLoading(true)
            }
        }
    }

    private fun inputProfile(
        token: String?,
        inputProfileRequest: InputProfileRequest,
        fromAuth: Boolean
    ) {
        val bearerToken = "Bearer $token"
        inputProfileViewModel.inputProfile(bearerToken, inputProfileRequest).observe(this) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    if (fromAuth) {
                        saveToken(token)
                        intentToMain()
                    } else {
                        onBackPressed()
                    }

                    isLoading(false)
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    isLoading(false)

                    return@observe
                }

                else -> {
                    isLoading(false)
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                btnSave.text = ""
                btnSave.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                btnSave.text = getString(R.string.save)
                btnSave.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun saveToken(token: String?) {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE) ?: return
        sharedPref.edit().putString(Constant.TOKEN, token).apply()
    }

    private fun intentToMain() {
        with(Intent(this, MainActivity::class.java)) {
            startActivity(this)
            finish()
        }
    }
}