package com.example.newsapp.common.data.remote.entity

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)