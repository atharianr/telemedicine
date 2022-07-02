package com.atharianr.telemedicine.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentProfileBinding
import com.atharianr.telemedicine.ui.landing.LandingActivity
import com.atharianr.telemedicine.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        val token = sharedPref.getString(Constant.TOKEN, null)

        binding.apply {
            btnLogout.setOnClickListener {
                removeToken()
                intentToLanding()
            }

            btnEdit.setOnClickListener {
                intentToInputProfile()
            }
        }

        getUserDetail(token)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getUserDetail(token: String?) {
        val bearerToken = "Bearer $token"
        profileViewModel.getUserDetail(bearerToken).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val genderArray = arrayOf("Laki-laki", "Perempuan")
                    val bloodArray = arrayOf("A", "B", "AB", "O")

                    val name = it.body?.data?.name
                    val email = it.body?.data?.email
                    val phoneNumber = it.body?.data?.phoneNumber
                    val gender = it.body?.data?.gender
                    val birthdate = it.body?.data?.birthdate
                    val bodyHeight = it.body?.data?.bodyHeight
                    val bodyWeight = it.body?.data?.bodyWeight
                    val bloodType = it.body?.data?.bloodType
                    val address = it.body?.data?.address

                    binding.tvName.text = name
                    binding.tvEmail.text = email
                    binding.tvPhoneNumber.text = phoneNumber.toString()
                    binding.tvGender.text = genderArray[gender!!]
                    binding.tvBirthday.text = birthdate
                    binding.tvHeight.text = bodyHeight.toString()
                    binding.tvWeight.text = bodyWeight.toString()
                    binding.tvBlood.text = bloodArray[bloodType!!]
                    binding.tvAddress.text = address
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    return@observe
                }

                else -> return@observe
            }
        }
    }

    private fun removeToken() {
        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        sharedPref.edit().clear().apply()
    }

    private fun intentToLanding() {
        with(Intent(requireActivity(), LandingActivity::class.java)) {
            startActivity(this)
            requireActivity().finish()
        }
    }

    private fun intentToInputProfile() {
        with(Intent(requireActivity(), LandingActivity::class.java)) {
            this.putExtra(Constant.FROM_AUTH, false)
            startActivity(this)
        }
    }
}