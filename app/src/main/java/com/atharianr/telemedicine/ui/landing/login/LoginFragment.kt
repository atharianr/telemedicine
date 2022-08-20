package com.atharianr.telemedicine.ui.landing.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.LoginRequest
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentLoginBinding
import com.atharianr.telemedicine.ui.main.MainActivity
import com.atharianr.telemedicine.ui.main.profile.InputProfileActivity
import com.atharianr.telemedicine.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding as FragmentLoginBinding

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            binding.apply {
                btnLogin.setOnClickListener {
                    isLoading(true)
                    checkValidation()
                }

                btnRegister.setOnClickListener {
                    view.findNavController()
                        .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                }
            }

            setupForm()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupForm() {
        binding.apply {
            incEmail.apply {
                til.hint = getString(R.string.email)
                et.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
            incPassword.apply {
                til.hint = getString(R.string.password)
                til.isPasswordVisibilityToggleEnabled = true
                til.setPasswordVisibilityToggleTintList(
                    AppCompatResources.getColorStateList(
                        requireActivity(),
                        android.R.color.darker_gray
                    )
                )
                et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                et.transformationMethod = PasswordTransformationMethod()
            }
        }
    }

    private fun login() {
        val email = binding.incEmail.et.text.toString()
        val password = binding.incPassword.et.text.toString()
        val loginRequest = LoginRequest(email, password)

        loginViewModel.login(loginRequest).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    if (it.body?.data?.emailVerifiedAt != null) {
                        checkIsProfileFilled(it.body.token)
                    } else {
                        val toVerifyFragment =
                            LoginFragmentDirections.actionLoginFragmentToVerifyFragment(
                                it.body?.token
                            )
                        view?.findNavController()?.navigate(toVerifyFragment)
                        isLoading(false)
                    }
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    isLoading(false)

                    return@observe
                }

                else -> isLoading(false)
            }
        }
    }

    private fun checkValidation() {
        binding.apply {
            if (incEmail.et.text.toString().isEmpty()) {
                incEmail.et.error = "Masukkan email anda."
                isLoading(false)
                return
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(incEmail.et.text.toString()).matches()) {
                incEmail.et.error = "Masukkan email yang sesuai."
                isLoading(false)
                return
            } else {
                incEmail.et.error = null
            }

            if (incPassword.et.text.toString().isEmpty()) {
                incPassword.et.error = "Masukkan kata sandi anda."
                isLoading(false)
                return
            }
            if (incPassword.et.text.toString().length < 8) {
                incPassword.et.error = "Kata sandi minimal 8 karakter"
                isLoading(false)
                return
            } else {
                incPassword.et.error = null
            }
        }

        login()
    }

    private fun checkIsProfileFilled(token: String?) {
        if (token != null) {
            val bearerToken = "Bearer $token"
            loginViewModel.getUserDetail(bearerToken).observe(requireActivity()) {
                when (it.status) {
                    StatusResponse.SUCCESS -> {
                        val name = it.body?.data?.name
                        val phoneNumber = it.body?.data?.phoneNumber
                        val gender = it.body?.data?.gender
                        val birthdate = it.body?.data?.birthdate
                        val bodyHeight = it.body?.data?.bodyHeight
                        val bodyWeight = it.body?.data?.bodyWeight
                        val bloodType = it.body?.data?.bloodType
                        val address = it.body?.data?.address

                        if (phoneNumber != null || gender != null || birthdate != null || bodyHeight != null || bodyWeight != null || bloodType != null || address != null) {
                            intentToMain()
                            saveToken(token)
                        } else {
                            intentToInputProfile(token, name)
                        }

                        isLoading(false)
                    }

                    else -> isLoading(false)
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                btnLogin.text = ""
                btnLogin.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                btnLogin.text = getString(R.string.login)
                btnLogin.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun saveToken(token: String) {
        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
                ?: return
        sharedPref.edit().putString(Constant.TOKEN, token).apply()
    }

    private fun intentToInputProfile(token: String, name: String?) {
        with(Intent(requireActivity(), InputProfileActivity::class.java)) {
            putExtra(Constant.FROM_AUTH, true)
            putExtra(Constant.TOKEN, token)
            putExtra(Constant.NAME, name)
            startActivity(this)
            requireActivity().finish()
        }
    }

    private fun intentToMain() {
        with(Intent(requireActivity(), MainActivity::class.java)) {
            startActivity(this)
            requireActivity().finish()
        }
    }
}