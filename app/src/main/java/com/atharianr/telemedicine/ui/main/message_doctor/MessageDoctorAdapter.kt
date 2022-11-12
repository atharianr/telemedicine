package com.atharianr.telemedicine.ui.main.message_doctor

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.remote.response.firebase.ChatResponse
import com.atharianr.telemedicine.databinding.ItemsListMessageBinding
import com.atharianr.telemedicine.ui.main.consultation.message.chatroom.ChatActivity
import com.atharianr.telemedicine.utils.Constant
import com.atharianr.telemedicine.utils.getDateFromString
import com.atharianr.telemedicine.utils.toFormat
import com.bumptech.glide.Glide

class MessageDoctorAdapter : RecyclerView.Adapter<MessageDoctorAdapter.ViewHolder>() {

    private var listData = ArrayList<ChatResponse>()

    fun setData(data: List<ChatResponse>) {
        this.listData.clear()
        this.listData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsListMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(private val binding: ItemsListMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ChatResponse) {
            binding.apply {
                val lastChat = data.chat?.toSortedMap(compareByDescending { it })?.entries?.first()?.value?.message
                Log.d(MessageDoctorAdapter::class.simpleName, lastChat.toString())
                val time = data.chat?.toSortedMap(compareByDescending { it })?.entries?.first()?.value?.time

                Glide.with(itemView)
                    .load(data.user_photo)
                    .placeholder(R.drawable.profile_pic_placeholder)
                    .centerCrop()
                    .into(ivDoctor)

                tvName.text = data.user_name
                tvMessage.text = lastChat
                if (time != null) {
                    tvTime.text = getDateFromString("yyyy-MM-dd HH:mm:ss", time).toFormat("HH:mm")
                }
            }

            itemView.setOnClickListener {
                with(Intent(itemView.context, ChatActivity::class.java)) {
                    putExtra(Constant.USER_ID, data.user_id)
                    putExtra(Constant.USER_NAME, data.user_name)
                    putExtra(Constant.USER_PHOTO, data.user_photo)

                    putExtra(Constant.DOCTOR_ID, data.doctor_id)
                    putExtra(Constant.DOCTOR_NAME, data.doctor_name)
                    putExtra(Constant.DOCTOR_PHOTO, data.doctor_photo)
                    itemView.context.startActivity(this)
                }
            }
        }
    }
}