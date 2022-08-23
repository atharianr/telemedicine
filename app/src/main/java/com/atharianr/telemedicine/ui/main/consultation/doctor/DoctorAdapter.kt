package com.atharianr.telemedicine.ui.main.consultation.doctor

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.data.source.remote.response.DoctorData
import com.atharianr.telemedicine.databinding.ItemsListDoctorBinding
import com.atharianr.telemedicine.ui.main.consultation.doctor.profile.DoctorProfileActivity
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide

class DoctorAdapter : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    private var listData = ArrayList<DoctorData>()

    fun setData(data: List<DoctorData>) {
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

        fun bind(data: DoctorData) {
            binding.apply {
                if (data.photo != null || data.photo != "") {
                    Glide.with(itemView)
                        .load(data.photo)
                        .centerCrop()
                        .into(ivDoctor)
                }

                tvName.text = data.name
                tvCategory.text = data.specialist

                btnChoose.setOnClickListener {
                    with(Intent(itemView.context, DoctorProfileActivity::class.java)) {
                        this.putExtra(Constant.DOCTOR_ID, data.id)
                        itemView.context.startActivity(this)
                    }
                }
            }
        }
    }
}