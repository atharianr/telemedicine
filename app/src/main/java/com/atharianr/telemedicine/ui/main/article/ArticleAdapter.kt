package com.atharianr.telemedicine.ui.main.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.atharianr.telemedicine.data.source.remote.response.ArticleData
import com.atharianr.telemedicine.databinding.ItemsListArticleBinding
import com.atharianr.telemedicine.ui.main.article.detail.ArticleDetailActivity
import com.atharianr.telemedicine.utils.Constant

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var listData = ArrayList<ArticleData>()

    fun setData(data: List<ArticleData>) {
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
        fun bind(data: ArticleData) {
            binding.apply {
                textView.text = data.name
            }

            itemView.setOnClickListener {
                with(Intent(itemView.context, ArticleDetailActivity::class.java)) {
                    putExtra(Constant.ARTICLE_TITLE, data.name)
                    putExtra(Constant.ARTICLE_IMAGE, data.imageUrl)
                    putExtra(Constant.ARTICLE_DEFINITION, data.definition)
                    putExtra(Constant.ARTICLE_SYMPTOM, data.symptom)
                    putExtra(Constant.ARTICLE_COMPLICATION, data.complication)
                    putExtra(Constant.ARTICLE_DIAGNOSIS, data.diagnosis)
                    putExtra(Constant.ARTICLE_TREATMENT, data.treatment)
                    itemView.context.startActivity(this)
                }
            }
        }
    }
}