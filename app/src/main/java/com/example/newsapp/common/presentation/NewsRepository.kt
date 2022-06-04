package com.example.newsapp.common.presentation

import com.example.newsapp.common.data.local.ArticleDatabase
import com.example.newsapp.common.data.local.ArticleEntity
import com.example.newsapp.core.network.NewsAPI

class NewsRepository(
    val db : ArticleDatabase,
    val api : NewsAPI
) {
    suspend fun getBreakingNews(
        countryCode : String,
        pageNumber : Int
    )  = api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(
        searchQuery : String,
        pageNumber: Int
    ) = api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article : ArticleEntity) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: ArticleEntity) = db.getArticleDao().deleteArticle(article.url)

}