package com.example.newsapp.common.data.remote.entity

import com.example.newsapp.common.data.local.ArticleEntity
import kotlin.random.Random

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    fun toArticleEntity() : ArticleEntity {
        //Find a better strategy than this
        return ArticleEntity(
            id = (0..Int.MAX_VALUE).random(),
            author = author!!,
            content = content!!,
            description = description!!,
            publishedAt = publishedAt!!,
            source = Source(1,source?.name!!),
            title = title!!,
            url = url!!,
            urlToImage = urlToImage!!
        )
    }
}
