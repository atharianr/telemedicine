package com.atharianr.telemedicine.ui.landing.login_doctor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atharianr.telemedicine.databinding.FragmentLoginDoctorBinding
import com.atharianr.telemedicine.ui.main.message_doctor.MessageDoctorActivity
import com.atharianr.telemedicine.utils.Constant
import com.atharianr.telemedicine.utils.Constant.DOCTOR_ID

class LoginDoctorFragment : Fragment() {

    private var _binding: FragmentLoginDoctorBinding? = null
    private val binding get() = _binding as FragmentLoginDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            incDoctorId.til.hint = "ID Dokter"
            btnLogin.setOnClickListener {
                saveDoctorId(incDoctorId.et.text.toString())
                intentToMessageDoctor(incDoctorId.et.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun intentToMessageDoctor(doctorId: String) {
        with(Intent(requireActivity(), MessageDoctorActivity::class.java)) {
            putExtra(DOCTOR_ID, doctorId)
            startActivity(this)
            requireActivity().finish()
        }
    }

    private fun saveDoctorId(doctorId: String) {
        val sharedPref =
            requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
                ?: return
        sharedPref.edit().putString(Constant.DOCTOR_ID, doctorId).apply()
    }
}