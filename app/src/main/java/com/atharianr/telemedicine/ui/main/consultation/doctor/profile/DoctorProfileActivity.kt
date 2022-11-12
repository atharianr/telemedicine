package com.atharianr.telemedicine.ui.main.consultation.doctor.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.DoctorExperience
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityDoctorProfileBinding
import com.atharianr.telemedicine.ui.main.consultation.doctor.DoctorExperienceAdapter
import com.atharianr.telemedicine.ui.main.consultation.doctor.DoctorViewModel
import com.atharianr.telemedicine.ui.main.consultation.message.chatroom.ChatActivity
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DoctorProfileActivity : AppCompatActivity() {

    private var _binding: ActivityDoctorProfileBinding? = null
    private val binding get() = _binding as ActivityDoctorProfileBinding

    private val doctorViewModel: DoctorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDoctorProfileBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        val doctorId = intent.getIntExtra(Constant.DOCTOR_ID, 0).toString()

        getDoctorDetail(getBearerToken(), doctorId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getDoctorDetail(token: String?, doctorId: String) {
        isLoading(loading = true)
        if (token != null) {
            doctorViewModel.getDoctorDetail(token, doctorId).observe(this) {
                when (it.status) {
                    StatusResponse.SUCCESS -> {
                        val data = it.body?.data
                        if (data != null) {
                            binding.apply {
                                if (data.photo != null || data.photo != "") {
                                    Glide.with(this@DoctorProfileActivity)
                                        .load(data.photo)
                                        .placeholder(R.drawable.profile_pic_placeholder)
                                        .centerCrop()
                                        .into(ivProfile)
                                }

                                tvName.text = data.name
                                tvSpeciality.text = data.specialist
                                tvEducation.text = data.education
                                tvEducationYear.text = data.educationYear
                                tvDoctorPhone.text = data.phoneNumber

                                val experience = data.experience
                                if (experience != null) {
                                    if (experience.isNotEmpty()) {
                                        initRecyclerView(data.experience)
                                        tvExperienceInfo.visibility = View.GONE
                                        rvExperience.visibility = View.VISIBLE
                                    } else {
                                        tvExperienceInfo.visibility = View.VISIBLE
                                        rvExperience.visibility = View.GONE
                                    }
                                }

                                btnChat.setOnClickListener {
                                    with(
                                        Intent(
                                            this@DoctorProfileActivity,
                                            ChatActivity::class.java
                                        )
                                    ) {
                                        val sharedPref = getSharedPreferences(
                                            Constant.USER_DATA,
                                            Context.MODE_PRIVATE
                                        )
                                        val userId =
                                            sharedPref.getString(Constant.USER_ID, "") ?: ""
                                        val userName =
                                            sharedPref.getString(Constant.USER_NAME, "") ?: ""
                                        val userPhoto =
                                            sharedPref.getString(Constant.USER_PHOTO, "") ?: ""

                                        Log.d("cobacoba dpa", "$userId, $userName, $userPhoto")

                                        putExtra(Constant.USER_ID, userId)
                                        putExtra(Constant.USER_NAME, userName)
                                        putExtra(Constant.USER_PHOTO, userPhoto)

                                        putExtra(Constant.DOCTOR_ID, doctorId)
                                        putExtra(Constant.DOCTOR_NAME, data.name)
                                        putExtra(Constant.DOCTOR_PHOTO, data.photo)
                                        startActivity(this)
                                    }
                                }
                            }
                            isLoading(loading = false)
                        }
                    }

                    StatusResponse.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        isLoading(loading = false)
                        return@observe
                    }

                    else -> {
                        isLoading(loading = false)
                        return@observe
                    }
                }
            }
        }
    }

    private fun initRecyclerView(listExperience: List<DoctorExperience>) {
        val doctorExperienceAdapter = DoctorExperienceAdapter()
        doctorExperienceAdapter.setData(listExperience)

        binding.rvExperience.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = doctorExperienceAdapter
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
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.TOKEN, "")
    }
}