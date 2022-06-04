package com.example.newsapp.core.di.modules

import android.app.Application
import androidx.room.Room
import com.example.newsapp.common.data.local.ArticleDatabase
import com.example.newsapp.common.data.local.converter.Converters
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDBModule {

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app : Application): ArticleDatabase{
        return Room.databaseBuilder(
            app, ArticleDatabase::class.java,"article_db.db"
        ).addTypeConverter(Converters()).build()
    }
}