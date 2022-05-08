package com.atharianr.telemedicine.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ItemsListHomeBinding
import com.atharianr.telemedicine.ui.main.consultation.ConsultationFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listImages = ArrayList<Int>()
    private var listTitles = ArrayList<String>()
    private var listDescriptions = ArrayList<String>()

    private lateinit var fm: FragmentManager

    fun setData(
        dataImage: List<Int>,
        dataTitle: List<String>,
        dataDescription: List<String>,
        fm: FragmentManager
    ) {
        this.listImages.clear()
        this.listImages.addAll(dataImage)

        this.listTitles.clear()
        this.listTitles.addAll(dataTitle)

        this.listDescriptions.clear()
        this.listDescriptions.addAll(dataDescription)

        this.fm = fm
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            listImages[position],
            listTitles[position],
            listDescriptions[position],
            fm
        )
    }

    override fun getItemCount(): Int = listTitles.size

    class ViewHolder(private val binding: ItemsListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Int, title: String, description: String, fm: FragmentManager) {
            binding.apply {
                Glide.with(itemView)
                    .load(image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerInside()
                    .into(ivHome)

                tvTitle.text = title
                tvDescription.text = description

                itemView.setOnClickListener {
                    when (title) {
                        "Konsultasi" -> {
                            val tag = ConsultationFragment()::class.java.simpleName
                            val ft = fm.beginTransaction()
                            ft.replace(R.id.fragment, ConsultationFragment(), tag)
                                .commit()
                        }
                    }
                }
            }
        }
    }
}