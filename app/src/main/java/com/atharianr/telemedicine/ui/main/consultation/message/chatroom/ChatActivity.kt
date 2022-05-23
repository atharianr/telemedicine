package com.atharianr.telemedicine.ui.main.consultation.message.chatroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityChatBinding
import com.atharianr.telemedicine.utils.Constant

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)

        val doctorName = intent.getStringExtra(Constant.NAME)

        binding.apply {
            btnSend.setOnClickListener {
                etMessage.text = null
            }

            tvName.text = doctorName
        }
    }
}