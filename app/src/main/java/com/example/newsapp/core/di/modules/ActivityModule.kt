package com.example.newsapp.core.di.modules

import android.app.Activity
import android.content.Context
import com.example.githubapp.core.di.context.ActivityContext
import com.example.githubapp.core.di.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity : Activity) {

    @PerActivity
    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

}