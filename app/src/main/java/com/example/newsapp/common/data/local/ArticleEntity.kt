package com.example.newsapp.common.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.common.data.remote.entity.Article
import com.example.newsapp.common.data.remote.entity.Source
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Serializable {

    fun toArticle(): Article {
        return Article(
            author, content, description, publishedAt, source, title, url, urlToImage
        )
    }
}