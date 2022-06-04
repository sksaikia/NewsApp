package com.example.newsapp.common.data.remote.entity

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)