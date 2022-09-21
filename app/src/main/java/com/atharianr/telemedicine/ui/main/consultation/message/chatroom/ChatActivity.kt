package com.atharianr.telemedicine.ui.main.consultation.message.chatroom

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.request.firebase.Chat
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.ActivityChatBinding
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

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

        firebaseDatabase = FirebaseDatabase.getInstance()
        chatAdapter = ChatAdapter()

        val sharedPref = getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
        val userId = sharedPref.getString(Constant.USER_ID, "")
        val doctorId = intent.getStringExtra(Constant.DOCTOR_ID)
        val doctorName = intent.getStringExtra(Constant.DOCTOR_NAME)
        val doctorPhoto = intent.getStringExtra(Constant.DOCTOR_PHOTO)

        binding.apply {
            Glide.with(this@ChatActivity)
                .load(doctorPhoto)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.profile_pic_placeholder)
                .centerCrop()
                .into(ivDoctor)

            btnSend.setOnClickListener {
                val message = etMessage.text.toString().trim()
                if (message != "") {
                    sendChat(doctorId, userId, etMessage.text.toString())
                }
                etMessage.text = null
            }
            tvName.text = doctorName
        }

        createChatRoom(doctorId, userId)
        getChat(doctorId, userId)
        initRecyclerView()
//        postMessage("4", "1")
//        getMessage("4", "1")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun createChatRoom(doctorId: String?, userId: String?) {
        if (doctorId != null && userId != null) chatViewModel.createChatRoom(doctorId, userId)
    }

    private fun sendChat(doctorId: String?, userId: String?, chatBody: String) {
        if (doctorId != null && userId != null) chatViewModel.sendChat(doctorId, userId, chatBody)
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
                        Toast.makeText(this, "success bos", Toast.LENGTH_SHORT).show()
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

    private fun postMessage(doctorId: String, userId: String) {
        val dbRef = firebaseDatabase.getReference("chatroom")
        val chatRoomRef = dbRef.child("$doctorId-$userId")
        chatRoomRef.child("id_doctor").setValue("4")
        chatRoomRef.child("id_user").setValue("1")

        val messageId = chatRoomRef.child("chat").push().key
        messageId?.let {
            val message = Chat("user", "pesan", Date().toString())
            chatRoomRef.child("chat").child(it).setValue(message)
        }
    }

    private fun getMessage(doctorId: String, userId: String) {
        val dbRef = firebaseDatabase.getReference("chatroom")
        val chatRef = dbRef.child("$doctorId-$userId").child("chat")
        chatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(ChatActivity::class.simpleName, snapshot.value.toString())
                val list = mutableListOf<Chat>()
                for (d in snapshot.children) {
                    val data = d.getValue(Chat::class.java)
                    data?.let { list.add(it) }
                }
                Log.d(ChatActivity::class.simpleName, list.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ChatActivity::class.simpleName, "loadPost:onCancelled", error.toException())
            }
        })
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
}