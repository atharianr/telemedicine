package com.atharianr.telemedicine.ui.main.profile

import android.content.Context
import android.content.Intent
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
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

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
            val window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.white)

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

                    val data = it.body?.data
                    name = data?.name
                    email = data?.email
                    phoneNumber = data?.phoneNumber
                    gender = data?.gender
                    birthdate = data?.birthdate
                    bodyHeight = data?.bodyHeight
                    bodyWeight = data?.bodyWeight
                    bloodType = data?.bloodType
                    address = data?.address

                    binding.apply {
                        if (data?.photo != null || data?.photo != "") {
                            Glide.with(requireActivity())
                                .load(Constant.USER_PHOTO_BASE_URL + data?.photo)
                                .centerCrop()
                                .into(ivProfile)
                        }

                        tvName.text = name
                        tvEmail.text = email
                        tvPhoneNumber.text = phoneNumber.toString()
                        tvGender.text = genderArray[gender!!]
                        tvBirthday.text = birthdate?.let { date -> stringDateFormatter(date) }
                        tvHeight.text = "${bodyHeight.toString()} cm"
                        tvWeight.text = "${bodyWeight.toString()} kg"
                        tvBlood.text = bloodArray[bloodType!!]
                        tvAddress.text = address
                    }

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

    private fun stringDateFormatter(
        dateStr: String,
        format: String = "dd/MM/yyyy",
        newFormat: String = "dd MMMM yyyy"
    ): String? {
        val dateFormat = SimpleDateFormat(format, Locale("id", "ID"))
        val newDateFormat = SimpleDateFormat(newFormat, Locale("id", "ID"))
        val date = dateFormat.parse(dateStr)

        return date?.let { newDateFormat.format(it) }
    }
}