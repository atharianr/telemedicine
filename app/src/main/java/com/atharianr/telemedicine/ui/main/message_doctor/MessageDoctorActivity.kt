package com.atharianr.telemedicine.ui.main.message_doctor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityMessageDoctorBinding
import com.atharianr.telemedicine.ui.main.MainViewModel
import com.atharianr.telemedicine.ui.main.consultation.message.MessageViewModel
import com.atharianr.telemedicine.ui.main.consultation.message.chatroom.ChatActivity
import com.atharianr.telemedicine.utils.Constant
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageDoctorActivity : AppCompatActivity() {

    private var _binding: ActivityMessageDoctorBinding? = null
    private val binding get() = _binding as ActivityMessageDoctorBinding

    private val messageViewModel: MessageViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var messageDoctorAdapter: MessageDoctorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMessageDoctorBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        messageDoctorAdapter = MessageDoctorAdapter()
        firebaseDatabase = FirebaseDatabase.getInstance()

        binding.rvMessage.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = messageDoctorAdapter
        }

        saveDoctorFcmToken(getDoctorId().toString(), getFCMToken().toString())
        getRecentChatDoctor(getDoctorId())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun saveDoctorFcmToken(doctorId: String, fcmToken: String) {
        mainViewModel.saveDoctorFcmToken(doctorId, fcmToken)
    }

    private fun getRecentChatDoctor(doctorId: String?) {
        if (doctorId != null && doctorId != "") {
            isLoading(loading = true, empty = true)
            messageViewModel.getRecentChatDoctor(doctorId).observe(this) {
                when (it.status) {
                    StatusResponse.SUCCESS -> {
                        val listChat = it.body
                        Log.d(ChatActivity::class.simpleName, listChat.toString())
                        if (listChat != null) {
                            if (listChat.isNotEmpty()) {
                                messageDoctorAdapter.setData(listChat)
                                isLoading(loading = false, empty = false)

                            } else {
                                isLoading(loading = false, empty = true)
                            }
                        }
                    }
                    StatusResponse.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        isLoading(loading = false, empty = true)
                        return@observe
                    }
                    else -> {
                        isLoading(loading = false, empty = true)
                        return@observe
                    }
                }
            }
        }
    }

    private fun isLoading(loading: Boolean, empty: Boolean) {
        binding.apply {
            if (loading) {
                rvMessage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                tvEmptyChat.visibility = View.GONE
            } else {
                if (empty) {
                    rvMessage.visibility = View.GONE
                    tvEmptyChat.visibility = View.VISIBLE
                } else {
                    rvMessage.visibility = View.VISIBLE
                    tvEmptyChat.visibility = View.GONE
                }
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun getDoctorId(): String? {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.DOCTOR_ID, null)
    }

    private fun getFCMToken(): String? {
        val sharedPref = getSharedPreferences(Constant.DEVICE_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.FCM_TOKEN, "")
    }
}