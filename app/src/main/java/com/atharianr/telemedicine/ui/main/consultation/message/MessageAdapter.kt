package com.atharianr.telemedicine.ui.main.consultation.message

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.data.source.local.entity.MessageEntity
import com.atharianr.telemedicine.databinding.ItemsListMessageBinding
import com.atharianr.telemedicine.ui.main.consultation.message.chatroom.ChatActivity
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var listData = ArrayList<MessageEntity>()

    fun setData(data: List<MessageEntity>) {
        this.listData.clear()
        this.listData.addAll(data)
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

        fun bind(data: MessageEntity) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.profilePic)
                    .placeholder(R.drawable.profile_pic_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivDoctor)

                tvName.text = data.name
                tvMessage.text = data.message
                tvTime.text = data.time
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChatActivity::class.java)
                intent.putExtra(Constant.NAME, data.name)
                itemView.context.startActivity(intent)
            }
        }
    }
}