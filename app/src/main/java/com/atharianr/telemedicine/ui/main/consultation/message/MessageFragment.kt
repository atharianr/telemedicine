package com.atharianr.telemedicine.ui.main.consultation.message

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atharianr.telemedicine.data.source.remote.response.vo.StatusResponse
import com.atharianr.telemedicine.databinding.FragmentMessageBinding
import com.atharianr.telemedicine.ui.main.consultation.message.chatroom.ChatActivity
import com.atharianr.telemedicine.utils.Constant
import com.google.firebase.database.FirebaseDatabase
import org.koin.androidx.viewmodel.ext.android.viewModel


class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding as FragmentMessageBinding

    private val messageViewModel: MessageViewModel by viewModel()

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val sharedPref =
                requireActivity().getSharedPreferences(Constant.USER_DATA, Context.MODE_PRIVATE)
            val userId = sharedPref.getString(Constant.USER_ID, "")

            messageAdapter = MessageAdapter()
            firebaseDatabase = FirebaseDatabase.getInstance()

            binding.rvMessage.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = messageAdapter
            }

            getRecentChat(userId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRecentChat(userId: String?) {
        if (userId != null && userId != "") {
            isLoading(loading = true, empty = true)
            messageViewModel.getRecentChat(userId).observe(viewLifecycleOwner) {
                when (it.status) {
                    StatusResponse.SUCCESS -> {
                        val listChat = it.body
                        Log.d(ChatActivity::class.simpleName, listChat.toString())
                        if (listChat != null) {
                            if (listChat.isNotEmpty()) {
                                messageAdapter.setData(listChat)
                                isLoading(loading = false, empty = false)

                            } else {
                                isLoading(loading = false, empty = true)
                            }
                        }
                    }
                    StatusResponse.ERROR -> {
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
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
}