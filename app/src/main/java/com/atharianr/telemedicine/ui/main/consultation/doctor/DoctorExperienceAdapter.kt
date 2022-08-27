package com.atharianr.telemedicine.ui.main.consultation.doctor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.data.source.remote.response.DoctorExperience
import com.atharianr.telemedicine.databinding.ItemsListExperienceBinding

class DoctorExperienceAdapter : RecyclerView.Adapter<DoctorExperienceAdapter.ViewHolder>() {

    private var listData = ArrayList<DoctorExperience>()

    fun setData(data: List<DoctorExperience>) {
        this.listData.clear()
        this.listData.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsListExperienceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(private val binding: ItemsListExperienceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DoctorExperience) {
            binding.apply {
                tvExperience.text = data.location
                tvExperienceYear.text = "${data.firstYear} - ${data.lastYear}"
            }
        }
    }
}