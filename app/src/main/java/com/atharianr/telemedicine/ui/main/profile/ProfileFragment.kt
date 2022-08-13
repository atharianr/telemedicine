package com.atharianr.telemedicine.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentProfileBinding
import com.atharianr.telemedicine.ui.landing.LandingActivity
import com.atharianr.telemedicine.utils.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModel()

    private var name: String? = null
    private var email: String? = null
    private var gender: Int? = null
    private var birthdate: String? = null
    private var bodyHeight: Int? = null
    private var bodyWeight: Int? = null
    private var bloodType: Int? = null
    private var phoneNumber: String? = null
    private var address: String? = null

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

        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity!!.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor =
                    ContextCompat.getColor(requireActivity(), R.color.white)
            }

            binding.apply {
                btnLogout.setOnClickListener {
                    removeToken()
                    intentToLanding()
                }

                btnEdit.setOnClickListener {
                    intentToInputProfile()
                }
            }

            getUserDetail(getToken())
        }
    }

    override fun onResume() {
        super.onResume()
        getUserDetail(getToken())
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }

    private fun getUserDetail(token: String?) {
        isLoading(true)
        val bearerToken = "Bearer $token"
        profileViewModel.getUserDetail(bearerToken).observe(requireActivity()) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val genderArray = arrayOf("Laki-laki", "Perempuan")
                    val bloodArray = arrayOf("A", "B", "AB", "O")

                    name = it.body?.data?.name
                    email = it.body?.data?.email
                    phoneNumber = it.body?.data?.phoneNumber
                    gender = it.body?.data?.gender
                    birthdate = it.body?.data?.birthdate
                    bodyHeight = it.body?.data?.bodyHeight
                    bodyWeight = it.body?.data?.bodyWeight
                    bloodType = it.body?.data?.bloodType
                    address = it.body?.data?.address

                    binding.tvName.text = name
                    binding.tvEmail.text = email
                    binding.tvPhoneNumber.text = phoneNumber.toString()
                    binding.tvGender.text = genderArray[gender!!]
                    binding.tvBirthday.text = birthdate
                    binding.tvHeight.text = bodyHeight.toString()
                    binding.tvWeight.text = bodyWeight.toString()
                    binding.tvBlood.text = bloodArray[bloodType!!]
                    binding.tvAddress.text = address

                    isLoading(false)
                }

                StatusResponse.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                    isLoading(false)
                    return@observe
                }

                else -> {
                    isLoading(false)
                    return@observe
                }
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
        with(Intent(requireActivity(), InputProfileActivity::class.java)) {
            this.putExtra(Constant.FROM_AUTH, false)
            this.putExtra(Constant.TOKEN, getToken())

            /* user data */
            this.putExtra(Constant.USER_NAME, name)
            this.putExtra(Constant.USER_EMAIL, email)
            this.putExtra(Constant.USER_GENDER, gender)
            this.putExtra(Constant.USER_BIRTHDATE, birthdate)
            this.putExtra(Constant.USER_HEIGHT, bodyHeight)
            this.putExtra(Constant.USER_WEIGHT, bodyWeight)
            this.putExtra(Constant.USER_BLOOD, bloodType)
            this.putExtra(Constant.USER_PHONE, phoneNumber)
            this.putExtra(Constant.USER_ADDRESS, address)

            startActivity(this)
        }
    }

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                layoutData.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                layoutData.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun getToken(): String? {
        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.TOKEN, "")
    }
}