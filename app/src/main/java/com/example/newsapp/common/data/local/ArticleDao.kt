package com.example.newsapp.common.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.common.data.remote.entity.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: ArticleEntity) : Long

    @Query("SELECT * FROM articles")
    fun getAllArticles() : LiveData<List<ArticleEntity>>

    @Query("DELETE FROM articles WHERE url = :url")
    suspend fun deleteArticle(url : String)

}