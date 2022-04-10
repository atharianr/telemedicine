package com.atharianr.telemedicine.ui.main.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.data.source.local.entity.ArticleEntity
import com.atharianr.telemedicine.databinding.ItemsListArticleBinding
import com.atharianr.telemedicine.ui.main.article.detail.ArticleDetailActivity

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var listData = ArrayList<ArticleEntity>()

    fun setData(data: List<ArticleEntity>) {
        this.listData.clear()
        this.listData.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(private val binding: ItemsListArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticleEntity) {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ArticleDetailActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }
}