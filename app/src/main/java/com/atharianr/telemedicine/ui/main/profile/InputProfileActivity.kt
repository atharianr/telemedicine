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
import java.util.regex.Pattern


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

                isLoading(true)
                checkValidation(token, inputProfileRequest, fromAuth)
            }
        }
    }

    private fun checkValidation(
        token: String?,
        inputProfileRequest: InputProfileRequest,
        fromAuth: Boolean
    ) {
        val formattedDateMatcher = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$")

        val gregorianDateMatcher = Pattern.compile(
            "^(29/02/(2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26])))$"
                    + "|^((0[1-9]|1[0-9]|2[0-8])/02/((19|2[0-9])[0-9]{2}))$"
                    + "|^((0[1-9]|[12][0-9]|3[01])/(0[13578]|10|12)/((19|2[0-9])[0-9]{2}))$"
                    + "|^((0[1-9]|[12][0-9]|30)/(0[469]|11)/((19|2[0-9])[0-9]{2}))$"
        )

        binding.apply {
            // name
            if (etName.text.toString().isEmpty()) {
                etName.error = "Masukkan nama lengkap anda."
                isLoading(false)
                return
            } else {
                etName.error = null
            }

            // birthdate
            if (etBirthdate.text.toString().isEmpty()) {
                etBirthdate.error = "Masukkan tanggal lahir anda."
                isLoading(false)
                return
            }
            if (!formattedDateMatcher.matcher(etBirthdate.text.toString()).matches()) {
                etBirthdate.error = "Format tanggal tidak tepat."
                isLoading(false)
                return
            }
            if (!gregorianDateMatcher.matcher(etBirthdate.text.toString()).matches()) {
                etBirthdate.error = "Masukkan tanggal yang sesuai."
                isLoading(false)
                return
            } else {
                etBirthdate.error = null
            }

            // bodyHeight
            if (etBodyHeight.text.toString().isEmpty()) {
                etBodyHeight.error = "Masukkan tinggi badan anda."
                isLoading(false)
                return
            }
            if (etBodyHeight.text.toString() == "\\d{4}-\\d{2}-\\d{2}") {

            } else {
                etBodyHeight.error = null
            }

            // bodyWeight
            if (etBodyWeight.text.toString().isEmpty()) {
                etBodyWeight.error = "Masukkan berat badan anda."
                isLoading(false)
                return
            } else {
                etBodyWeight.error = null
            }

            // address
            if (etAddress.text.toString().isEmpty()) {
                etAddress.error = "Masukkan alamat anda."
                isLoading(false)
                return
            } else {
                etAddress.error = null
            }
        }

        inputProfile(token, inputProfileRequest, fromAuth)
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