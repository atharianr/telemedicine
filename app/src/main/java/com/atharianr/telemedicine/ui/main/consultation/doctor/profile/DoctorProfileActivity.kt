package com.atharianr.telemedicine.ui.main.consultation.doctor.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityDoctorProfileBinding
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

        binding.apply {
            btnChat.setOnClickListener {
                with(Intent(this@DoctorProfileActivity, ChatActivity::class.java)) {
                    startActivity(this)
                }
            }
        }

        getDoctorDetail(doctorId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getDoctorDetail(doctorId: String) {
        isLoading(loading = true)
        doctorViewModel.getDoctorDetail(doctorId).observe(this) {
            when (it.status) {
                StatusResponse.SUCCESS -> {
                    val data = it.body?.data
                    if (data != null) {
                        binding.apply {
                            if (data.photo != null || data.photo != "") {
                                Glide.with(this@DoctorProfileActivity)
                                    .load(data.photo)
                                    .centerCrop()
                                    .into(ivProfile)
                            }

                            tvName.text = data.name
                            tvSpeciality.text = data.specialist
                            tvEducation.text = data.education
                            tvEducationYear.text = data.educationYear
                            tvDoctorPhone.text = data.phoneNumber
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
}