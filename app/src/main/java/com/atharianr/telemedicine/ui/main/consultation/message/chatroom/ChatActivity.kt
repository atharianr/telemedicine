package com.atharianr.telemedicine.ui.main.consultation.message.chatroom

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.Data
import com.atharianr.telemedicine.data.source.remote.request.FcmChatRequest
import com.atharianr.telemedicine.data.source.remote.request.Notification
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityChatBinding
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {

    private var _binding: ActivityChatBinding? = null
    private val binding get() = _binding as ActivityChatBinding

    private val chatViewModel: ChatViewModel by viewModel()

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityChatBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        val appType = if (getToken() != null && getToken() != "") Constant.USER else Constant.DOCTOR

        firebaseDatabase = FirebaseDatabase.getInstance()
        chatAdapter = ChatAdapter(appType)

        val userId = intent.getStringExtra(Constant.USER_ID) ?: ""
        val userName = intent.getStringExtra(Constant.USER_NAME) ?: ""
        val userPhoto = intent.getStringExtra(Constant.USER_PHOTO) ?: ""

        val doctorId = intent.getStringExtra(Constant.DOCTOR_ID) ?: ""
        val doctorName = intent.getStringExtra(Constant.DOCTOR_NAME) ?: ""
        val doctorPhoto = intent.getStringExtra(Constant.DOCTOR_PHOTO) ?: ""

        Log.d("cobacoba ca", "$userId, $userName, $userPhoto")

        binding.apply {
            Glide.with(this@ChatActivity)
                .load(if (appType == Constant.USER) doctorPhoto else userPhoto)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.profile_pic_placeholder)
                .centerCrop()
                .into(ivDoctor)

            tvName.text = if (appType == Constant.USER) doctorName else userName

            btnSend.setOnClickListener {
                val message = etMessage.text.toString().trim()
                if (message != "") {
                    sendChat(doctorId, userId, etMessage.text.toString(), appType) {
                        if (appType == Constant.USER) {
                            getDoctorFcmToken(
                                doctorId,
                                doctorName,
                                doctorPhoto,
                                userId,
                                userName,
                                userPhoto,
                                message
                            )
                        } else {
                            getUserFcmToken(
                                doctorId,
                                doctorName,
                                doctorPhoto,
                                userId,
                                userName,
                                userPhoto,
                                message
                            )
                        }
                    }
                }
                etMessage.text = null
            }
        }

        createChatRoom(doctorId, doctorName, doctorPhoto, userId, userName, userPhoto)
        getChat(doctorId, userId)
        initRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createChatRoom(
        doctorId: String,
        doctorName: String,
        doctorPhoto: String,
        userId: String,
        userName: String,
        userPhoto: String
    ) {
        chatViewModel.createChatRoom(
            doctorId,
            doctorName,
            doctorPhoto,
            userId,
            userName,
            userPhoto
        )
    }

    private fun sendChat(
        doctorId: String?,
        userId: String?,
        chatBody: String,
        appType: String,
        callback: ((String) -> Unit)?
    ) {
        if (doctorId != null && userId != null) {
            chatViewModel.sendChat(doctorId, userId, chatBody, appType).observe(this) { success ->
                if (success) {
                    callback?.invoke(chatBody)
                }
            }
        }
    }

    private fun sendFcmChat(
        fcmToken: String,
        doctorId: String,
        doctorName: String,
        doctorPhoto: String,
        userId: String,
        userName: String,
        userPhoto: String,
        message: String
    ) {
        val listRegistrationIds = listOf(fcmToken)
        val data = Data(
            Notification(
                Constant.CHAT,
                userId,
                userName,
                userPhoto,
                doctorId,
                doctorName,
                doctorPhoto,
                message
            )
        )
        val fcmChatRequest = FcmChatRequest(listRegistrationIds, data)
        chatViewModel.sendFcmChat(Constant.FCM_API_KEY, fcmChatRequest)
    }

    private fun getChat(doctorId: String?, userId: String?) {
        if (doctorId != null && userId != null) {
            isLoading(true)
            chatViewModel.getChat(doctorId, userId).observe(this) {
                when (it.status) {
                    StatusResponse.SUCCESS -> {
                        val listChat = it.body
                        Log.d(ChatActivity::class.simpleName, listChat.toString())
                        if (listChat != null) {
                            chatAdapter.setData(listChat)
                            binding.rvChat.scrollToPosition(listChat.size - 1)
                        }
                        isLoading(false)
                    }
                    StatusResponse.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
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
    }

    private fun initRecyclerView() {
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = chatAdapter
        }
    }

    private fun isLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                rvChat.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                rvChat.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun getToken(): String? {
        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.TOKEN, null)
    }

    private fun getFCMToken(): String? {
        val sharedPref = getSharedPreferences(Constant.DEVICE_DATA, Context.MODE_PRIVATE)
        return sharedPref.getString(Constant.FCM_TOKEN, "")
    }

    private fun getUserFcmToken(
        doctorId: String,
        doctorName: String,
        doctorPhoto: String,
        userId: String,
        userName: String,
        userPhoto: String,
        message: String
    ) {
        chatViewModel.getUserFcmToken(userId).observe(this) {
            sendFcmChat(
                it,
                doctorId,
                doctorName,
                doctorPhoto,
                userId,
                userName,
                userPhoto,
                message
            )
        }
    }

    private fun getDoctorFcmToken(
        doctorId: String,
        doctorName: String,
        doctorPhoto: String,
        userId: String,
        userName: String,
        userPhoto: String,
        message: String
    ) {
        chatViewModel.getDoctorFcmToken(doctorId).observe(this) {
            Log.d("cobacoba fcmtoken", it)
            sendFcmChat(
                it,
                doctorId,
                doctorName,
                doctorPhoto,
                userId,
                userName,
                userPhoto,
                message
            )
        }
    }
}