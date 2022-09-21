package com.atharianr.telemedicine.ui.main.consultation.message.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.data.source.remote.request.firebase.Chat
import com.atharianr.telemedicine.databinding.ItemsListChatReceivedBinding
import com.atharianr.telemedicine.databinding.ItemsListChatSentBinding
import com.atharianr.telemedicine.utils.getDateFromString
import com.atharianr.telemedicine.utils.toFormat

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listChat = ArrayList<Chat>()

    fun setData(data: List<Chat>) {
        this.listChat.clear()
        this.listChat.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SENDER) {
            SenderViewHolder(
                ItemsListChatSentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ReceiverViewHolder(
                ItemsListChatReceivedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_SENDER -> (holder as SenderViewHolder).bind(listChat[position])
            TYPE_RECEIVER -> (holder as ReceiverViewHolder).bind(listChat[position])
        }
    }

    override fun getItemCount(): Int = listChat.size

    override fun getItemViewType(position: Int): Int {
        return if (listChat[position].sender == "user") TYPE_SENDER else TYPE_RECEIVER
    }

    private inner class SenderViewHolder(private val binding: ItemsListChatSentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Chat) {
            with(binding) {
                tvMessage.text = data.message
                if (data.time != null) {
                    tvTime.text =
                        getDateFromString("yyyy-MM-dd HH:mm:ss", data.time).toFormat("HH:mm")
                }
            }
        }
    }

    private inner class ReceiverViewHolder(private val binding: ItemsListChatReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Chat) {
            with(binding) {
                tvMessage.text = data.message
                if (data.time != null) {
                    tvTime.text =
                        getDateFromString("yyyy-MM-dd HH:mm:ss", data.time).toFormat("HH:mm")
                }
            }
        }
    }

    companion object {
        private const val TYPE_SENDER = 0
        private const val TYPE_RECEIVER = 1
    }
}