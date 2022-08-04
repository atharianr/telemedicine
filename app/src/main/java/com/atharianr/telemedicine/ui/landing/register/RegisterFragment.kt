package com.atharianr.telemedicine.ui.landing.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding as FragmentRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnRegister.setOnClickListener {
                isLoading(true)
                checkValidation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkValidation() {
        binding.apply {
            // name
            if (etName.text.toString().isEmpty()) {
                etName.error = "Masukkan nama lengkap anda."
                isLoading(false)
                return
            } else {
                etName.error = null
            }

            // email
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

            // password
            if (etPassword.text.toString().isEmpty()) {
                etPassword.error = "Masukkan kata sandi anda."
                isLoading(false)
                return
            }
            if (etPassword.text.toString().length < 8) {
                etPassword.error = "Kata sandi minimal 8 karakter"
                isLoading(false)
                return
            } else {
                etPassword.error = null
            }

            // password confirmation
            if (etPasswordConfirm.text.toString().isEmpty()) {
                etPasswordConfirm.error = "Konfirmasi kata sandi anda."
                isLoading(false)
                return
            }
            if (etPasswordConfirm.text.toString() != etPassword.text.toString()) {
                etPasswordConfirm.error = "Kata sandi tidak sama."
                isLoading(false)
                return
            } else {
                etPasswordConfirm.error = null
            }
        }

        register()
    }

    private fun register() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        val registerRequest = RegisterRequest(name, email, password)

        registerViewModel.register(registerRequest).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    Toast.makeText(requireActivity(), it.body?.message, Toast.LENGTH_SHORT)
                        .show()

                    val toVerifyFragment =
                        RegisterFragmentDirections.actionRegisterFragmentToVerifyFragment(it.body?.token)
                    view?.findNavController()?.navigate(toVerifyFragment)
                    isLoading(false)
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

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                btnRegister.text = ""
                btnRegister.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                btnRegister.text = getString(R.string.register)
                btnRegister.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
    }
}