package com.atharianr.telemedicine.ui.main.article.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atharianr.telemedicine.R
import com.atharianr.telemedicine.databinding.ActivityArticleDetailBinding
import com.atharianr.telemedicine.databinding.ActivityChatBinding

class ArticleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_Telemedicine)
        setContentView(binding.root)
    }
}