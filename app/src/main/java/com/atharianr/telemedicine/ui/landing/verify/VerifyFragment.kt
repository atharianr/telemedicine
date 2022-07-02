package com.atharianr.telemedicine.ui.landing.verify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentVerifyBinding
import com.atharianr.telemedicine.ui.main.MainActivity
import com.atharianr.telemedicine.ui.main.profile.InputProfileActivity
import com.atharianr.telemedicine.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class VerifyFragment : Fragment() {
    private var _binding: FragmentVerifyBinding? = null
    private val binding get() = _binding as FragmentVerifyBinding

    private val verifyViewModel: VerifyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = VerifyFragmentArgs.fromBundle(arguments as Bundle).token
        val bearerToken = "Bearer $token"
        Log.d("VerifyFragment", bearerToken)

        binding.apply {
            btnNext.setOnClickListener {
                checkVerified(bearerToken)
            }

            tvSend.setOnClickListener {
                sendEmailVerification(bearerToken)
            }
        }

        isLoading(true)
        sendEmailVerification(bearerToken)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun sendEmailVerification(token: String) {
        verifyViewModel.verifyEmail(token).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    Toast.makeText(requireActivity(), it.body?.message, Toast.LENGTH_SHORT).show()
                    isLoading(false)
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    isLoading(false)

                    return@observe
                }
                else -> {
                    isLoading(false)
                }
            }
        }
        isLoading(true)
    }

    private fun checkVerified(token: String) {
        verifyViewModel.getUserDetail(token).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    if (it.body?.data?.emailVerifiedAt != null) {
                        checkIsProfileFilled(token)
                        isLoading(false)
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Email belum diverifikasi.",
                            Toast.LENGTH_SHORT
                        ).show()
                        isLoading(false)

                        return@observe
                    }

                    isLoading(true)
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    isLoading(false)
                }

                else -> {}
            }
        }
    }

    private fun checkIsProfileFilled(token: String) {
        verifyViewModel.getUserDetail(token).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val phoneNumber = it.body?.data?.phoneNumber
                    val gender = it.body?.data?.gender
                    val birthdate = it.body?.data?.birthdate
                    val bodyHeight = it.body?.data?.bodyHeight
                    val bodyWeight = it.body?.data?.bodyWeight
                    val bloodType = it.body?.data?.bloodType
                    val address = it.body?.data?.address

                    if (phoneNumber != null || gender != null || birthdate != null || bodyHeight != null || bodyWeight != null || bloodType != null || address != null) {
                        intentToMain()
                    } else {
                        intentToInputProfile(token, it.body?.data?.name)
                    }

                    isLoading(true)
                }

                else -> {}
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                btnNext.text = ""
                btnNext.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                btnNext.text = getString(R.string.next)
                btnNext.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
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