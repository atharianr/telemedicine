package com.atharianr.telemedicine.ui.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.InputProfileRequest
import com.atharianr.telemedicine.databinding.FragmentProfileBinding
import com.atharianr.telemedicine.ui.landing.LandingActivity
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

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

        if (activity != null) {
            val window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor =
                ContextCompat.getColor(requireActivity(), R.color.white)

            binding.apply {
                btnLogout.setOnClickListener {
                    saveUserFcmToken(getUserId().toString(), "") // Clear User FCM Token
                    removeTokenOnServer(getBearerToken())
                    removeToken()
                    intentToLanding()
                }

                btnEdit.setOnClickListener {
                    intentToInputProfile()
                }
            }

            isLoading(true)
            getBundle()
        }
    }

    override fun onResume() {
        super.onResume()
        getBundle()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }

    private fun getBundle() {
        val name = arguments?.getString(Constant.USER_NAME, "")
        val email = arguments?.getString(Constant.USER_EMAIL, "")
        val gender = arguments?.getInt(Constant.USER_GENDER, 0)
        val birthdate = arguments?.getString(Constant.USER_BIRTHDATE, "")
        val height = arguments?.getInt(Constant.USER_HEIGHT, 0)
        val weight = arguments?.getInt(Constant.USER_WEIGHT, 0)
        val blood = arguments?.getInt(Constant.USER_BLOOD, 0)
        val address = arguments?.getString(Constant.USER_ADDRESS, "")
        val phone = arguments?.getString(Constant.USER_PHONE, "")
        val photo = arguments?.getString(Constant.USER_PHOTO, "")

        val genderArray = arrayOf("Laki-laki", "Perempuan")
        val bloodArray = arrayOf("A", "B", "AB", "O")

        binding.apply {
            if (phone != null || phone != "") {
                val ctx = activity
                if (ctx != null) {
                    Glide.with(ctx)
                        .load(Constant.USER_PHOTO_BASE_URL + photo)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.profile_pic_placeholder)
                        .centerCrop()
                        .into(ivProfile)
                }
            }

            tvName.text = name
            tvEmail.text = email
            tvPhoneNumber.text = phone.toString()
            tvGender.text = genderArray[gender!!]
            tvBirthday.text = birthdate?.let { date -> stringDateFormatter(date) }
            tvHeight.text = "${height.toString()} cm"
            tvWeight.text = "${weight.toString()} kg"
            tvBlood.text = bloodArray[blood!!]
            tvAddress.text = address
        }
        isLoading(false)
    }

    private fun removeTokenOnServer(bearerToken: String?) {
        val inputProfileRequest = InputProfileRequest(fcmToken = null)
        if (bearerToken != null && bearerToken != "") {
            CoroutineScope(Dispatchers.Main).launch {
                profileViewModel.putTokenFCM(bearerToken, inputProfileRequest)
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
            this.putExtra(Constant.TOKEN, getBearerToken())

            /* user data */
            val bundle = arguments
            if (bundle != null) {
                this.putExtras(bundle)
            }

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

    private fun getBearerToken(): String? {
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

    private fun saveUserFcmToken(userId: String, fcmToken: String) {
        profileViewModel.saveUserFcmToken(userId, fcmToken)
    }

    private fun getUserId(): String? {
        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.USER_ID, null)
    }
}