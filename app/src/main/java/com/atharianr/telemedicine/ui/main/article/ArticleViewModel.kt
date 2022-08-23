package com.atharianr.telemedicine.ui.main.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.ArticleDetailResponse
import com.atharianr.telemedicine.data.source.remote.response.ArticleResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class ArticleViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getAllArticles(): LiveData<ApiResponse<ArticleResponse>> = remoteDataSource.getAllArticle()
    fun getSearchArticles(keyword: String): LiveData<ApiResponse<ArticleResponse>> =
        remoteDataSource.getSearchArticles(keyword)

    fun getArticleDetail(articleId: String): LiveData<ApiResponse<ArticleDetailResponse>> =
        remoteDataSource.getArticleDetail(articleId)
}