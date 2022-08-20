package com.atharianr.telemedicine.ui.landing.register

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

        if (activity != null) {
            binding.apply {
                btnRegister.setOnClickListener {
                    isLoading(true)
                    checkValidation()
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
            incName.apply {
                til.hint = getString(R.string.name)
                et.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            }
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
                et.setPadding(et.paddingLeft, et.paddingTop, dpToPx(48), et.paddingBottom)
                et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                et.transformationMethod = PasswordTransformationMethod()
            }
            incPasswordConfirm.apply {
                til.hint = getString(R.string.password_confirmation)
                til.isPasswordVisibilityToggleEnabled = true
                til.setPasswordVisibilityToggleTintList(
                    AppCompatResources.getColorStateList(
                        requireActivity(),
                        android.R.color.darker_gray
                    )
                )
                et.setPadding(et.paddingLeft, et.paddingTop, dpToPx(48), et.paddingBottom)
                et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                et.transformationMethod = PasswordTransformationMethod()
            }
        }
    }

    private fun checkValidation() {
        binding.apply {
            // name
            if (incName.et.text.toString().isEmpty()) {
                incName.et.error = "Masukkan nama lengkap anda."
                isLoading(false)
                return
            } else {
                incName.et.error = null
            }

            // email
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

            // password
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

            // password confirmation
            if (incPasswordConfirm.et.text.toString().isEmpty()) {
                incPasswordConfirm.et.error = "Konfirmasi kata sandi anda."
                isLoading(false)
                return
            }
            if (incPasswordConfirm.et.text.toString() != incPassword.et.text.toString()) {
                incPasswordConfirm.et.error = "Kata sandi tidak sama."
                isLoading(false)
                return
            } else {
                incPasswordConfirm.et.error = null
            }
        }

        register()
    }

    private fun register() {
        val name = binding.incName.et.text.toString()
        val email = binding.incEmail.et.text.toString()
        val password = binding.incPassword.et.text.toString()

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

    private fun dpToPx(size: Int): Int {
        val scale = resources.displayMetrics.density
        return (size * scale + 0.5f).toInt()
    }
}