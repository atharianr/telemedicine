package com.atharianr.telemedicine.ui.landing.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()

                    formValidation(email, password)

                    val loginRequest = LoginRequest(email, password)

                    loginViewModel.login(loginRequest).observe(requireActivity()) {
                        when (it.status) {
                            StatusResponse.SUCCESS -> {
                                Toast.makeText(
                                    requireActivity(),
                                    "Login Berhasil",
                                    Toast.LENGTH_SHORT
                                ).show()

                                if (it.body?.data?.emailVerifiedAt != null) {
                                    saveToken(it.body.token)
                                    with(Intent(requireActivity(), MainActivity::class.java)) {
                                        startActivity(this)
                                        requireActivity().finish()
                                    }
                                } else {
                                    val toVerifyFragment =
                                        LoginFragmentDirections.actionLoginFragmentToVerifyFragment(
                                            it.body?.token
                                        )
                                    view.findNavController().navigate(toVerifyFragment)
                                }
                            }

                            StatusResponse.ERROR -> {
                                Toast.makeText(
                                    requireActivity(),
                                    it.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                return@observe
                            }

                            else -> {}
                        }
                    }
                }

                btnRegister.setOnClickListener {
                    view.findNavController()
                        .navigate(R.id.action_loginFragment_to_registerFragment)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun formValidation(email: String, password: String) {
        binding.apply {
            if (email.isEmpty()) {
                etEmail.error = "Masukkan email anda."
            }

            if (password.isEmpty()) {
                etPassword.error = "Masukkan kata sandi anda."
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