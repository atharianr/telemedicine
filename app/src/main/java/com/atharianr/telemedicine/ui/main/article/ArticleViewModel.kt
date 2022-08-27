package com.atharianr.telemedicine.ui.main.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.atharianr.telemedicine.data.source.remote.RemoteDataSource
import com.atharianr.telemedicine.data.source.remote.response.ArticleDetailResponse
import com.atharianr.telemedicine.data.source.remote.response.ArticleResponse
import com.atharianr.telemedicine.data.source.remote.response.vo.ApiResponse

class ArticleViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {
    fun getAllArticles(token: String): LiveData<ApiResponse<ArticleResponse>> =
        remoteDataSource.getAllArticle(token)

    fun getSearchArticles(token: String, keyword: String): LiveData<ApiResponse<ArticleResponse>> =
        remoteDataSource.getSearchArticles(token, keyword)

    fun getArticleDetail(
        token: String,
        articleId: String
    ): LiveData<ApiResponse<ArticleDetailResponse>> =
        remoteDataSource.getArticleDetail(token, articleId)
}