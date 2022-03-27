package com.atharianr.telemedicine.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.databinding.ItemsListHomeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listImages = ArrayList<Int>()
    private var listTitles = ArrayList<String>()
    private var listDescriptions = ArrayList<String>()

    fun setData(dataImage: List<Int>, dataTitle: List<String>, dataDescription: List<String>) {
        this.listImages.clear()
        this.listImages.addAll(dataImage)

        this.listTitles.clear()
        this.listTitles.addAll(dataTitle)

        this.listDescriptions.clear()
        this.listDescriptions.addAll(dataDescription)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listImages[position], listTitles[position], listDescriptions[position])
    }

    override fun getItemCount(): Int = listTitles.size

    class ViewHolder(private val binding: ItemsListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Int, title: String, description: String) {
            binding.apply {
                Glide.with(itemView)
                    .load(image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerInside()
                    .into(ivHome)

                tvTitle.text = title
                tvDescription.text = description
            }
        }
    }
}