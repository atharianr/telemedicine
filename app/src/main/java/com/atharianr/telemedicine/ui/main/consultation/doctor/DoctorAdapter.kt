package com.atharianr.telemedicine.ui.main.consultation.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.data.source.local.entity.DoctorEntity
import com.atharianr.telemedicine.databinding.ItemsListDoctorBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DoctorAdapter : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    private var listData = ArrayList<DoctorEntity>()

    fun setData(data: List<DoctorEntity>) {
        this.listData.clear()
        this.listData.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsListDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(private val binding: ItemsListDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DoctorEntity) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.profilePic)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivDoctor)

                tvName.text = data.name
                tvCategory.text = data.category
                tvPercentage.text = "${data.percentage}%"
                btnPrice.text = "Rp ${data.price}"
            }
        }
    }
}