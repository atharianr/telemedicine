package com.atharianr.telemedicine.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ItemsListHomeBinding
import com.atharianr.telemedicine.ui.main.consultation.ConsultationFragment
import com.atharianr.telemedicine.ui.webview.WebViewActivity
import com.atharianr.telemedicine.utils.Constant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var listImages = ArrayList<Int>()
    private var listTitles = ArrayList<String>()
    private var listDescriptions = ArrayList<String>()

    private lateinit var ctx: Context
    private lateinit var fm: FragmentManager

    fun setData(
        dataImage: List<Int>,
        dataTitle: List<String>,
        dataDescription: List<String>,
        ctx: Context,
        fm: FragmentManager
    ) {
        this.listImages.clear()
        this.listImages.addAll(dataImage)

        this.listTitles.clear()
        this.listTitles.addAll(dataTitle)

        this.listDescriptions.clear()
        this.listDescriptions.addAll(dataDescription)

        this.ctx = ctx
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
            ctx,
            fm
        )
    }

    override fun getItemCount(): Int = listTitles.size

    class ViewHolder(private val binding: ItemsListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            image: Int,
            title: String,
            description: String,
            ctx: Context,
            fm: FragmentManager
        ) {
            binding.apply {
                Glide.with(itemView)
                    .load(image)
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

                        "Resum Medis" -> {
                            with(Intent(ctx, WebViewActivity::class.java)) {
                                putExtra(Constant.WEB_URL, Constant.WEB_URL_BPJS)
                                ctx.startActivity(this)
                            }
                        }

                        "Rumah Sakit" -> {
                            with(Intent(ctx, WebViewActivity::class.java)) {
                                putExtra(Constant.WEB_URL, Constant.WEB_URL_RS)
                                ctx.startActivity(this)
                            }
                        }

                        "Apotek" -> {
                            with(Intent(ctx, WebViewActivity::class.java)) {
                                putExtra(Constant.WEB_URL, Constant.WEB_URL_APOTEK)
                                ctx.startActivity(this)
                            }
                        }
                    }
                }
            }
        }
    }
}