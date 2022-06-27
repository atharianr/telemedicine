package com.atharianr.telemedicine.ui.landing.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.LoginRequest
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentLoginBinding
import com.atharianr.telemedicine.ui.main.MainActivity
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val loginRequest = LoginRequest(email, password)

        loginViewModel.login(loginRequest).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    if (it.body?.data?.emailVerifiedAt != null) {
                        isLoading(false)
                        saveToken(it.body.token)
                        with(Intent(requireActivity(), MainActivity::class.java)) {
                            startActivity(this)
                            requireActivity().finish()
                        }
                    } else {
                        isLoading(false)
                        val toVerifyFragment =
                            LoginFragmentDirections.actionLoginFragmentToVerifyFragment(
                                it.body?.token
                            )
                        view?.findNavController()?.navigate(toVerifyFragment)
                    }
                }

                StatusResponse.ERROR -> {
                    isLoading(false)
                    Toast.makeText(
                        requireActivity(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    return@observe
                }

                else -> {
                    isLoading(false)
                }
            }
        }
    }

    private fun checkValidation() {
        binding.apply {
            if (etEmail.text.toString().isEmpty()) {
                etEmail.error = "Masukkan email anda."
                isLoading(false)
                return
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                etEmail.error = "Masukkan email yang sesuai."
                isLoading(false)
                return
            } else {
                etEmail.error = null
            }

            if (etPassword.text.toString().isEmpty()) {
                etPassword.error = "Masukkan kata sandi anda."
                isLoading(false)
                return
            } else {
                etPassword.error = null
            }
        }

        login()
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

    private fun saveToken(token: String?) {
        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
                ?: return
        sharedPref.edit().putString(Constant.TOKEN, token).apply()
    }
}