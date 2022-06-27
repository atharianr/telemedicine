package com.atharianr.telemedicine.ui.landing.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.data.source.remote.request.RegisterRequest
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentRegisterBinding
import com.atharianr.telemedicine.ui.main.profile.InputProfileActivity
import com.atharianr.telemedicine.utils.Constant
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
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                val registerRequest = RegisterRequest(name, email, password)

                registerViewModel.register(registerRequest).observe(requireActivity()) {
                    when (it.status) {
                        StatusResponse.SUCCESS -> {
                            Toast.makeText(requireActivity(), it.body?.message, Toast.LENGTH_SHORT)
                                .show()

                            // save token
                            val sharedPref =
                                activity?.getPreferences(Context.MODE_PRIVATE) ?: return@observe
                            with(sharedPref.edit()) {
                                putString(Constant.TOKEN, it.body?.token)
                                apply()
                            }

                            with(Intent(requireActivity(), InputProfileActivity::class.java)) {
                                putExtra(Constant.FROM_REGISTER, true)
                                putExtra(Constant.NAME, name)
                                startActivity(this)
                                requireActivity().finish()
                            }
                        }

                        StatusResponse.ERROR -> {
                            Toast.makeText(
                                requireActivity(),
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}