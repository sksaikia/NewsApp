package com.example.newsapp.core.di.modules

import com.example.newsapp.common.data.local.ArticleDatabase
import com.example.newsapp.common.presentation.NewsRepository
import com.example.newsapp.core.network.NewsAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(db : ArticleDatabase,
                           api : NewsAPI) : NewsRepository{
        return NewsRepository(db, api)
    }

}