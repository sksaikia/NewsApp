package com.example.githubapp.core.di.component

import android.app.Application
import com.example.newsapp.core.di.modules.NetworkModule
import com.example.newsapp.core.di.modules.ViewModelModule
import com.example.newsapp.core.di.modules.RepositoryModule
import com.example.newsapp.core.di.modules.RoomDBModule
import com.example.newsapp.ui.ArticleFragment
import com.example.newsapp.ui.BreakingNewsFragment
import com.example.newsapp.ui.SavedNewsFragment
import com.example.newsapp.ui.SearchNewsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    ViewModelModule::class,
    RoomDBModule::class,
    RepositoryModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(breakingNewsFragment : BreakingNewsFragment)

    fun inject(articleFragment: ArticleFragment)

    fun inject(savedNewsFragment: SavedNewsFragment)

    fun inject(searchNewsFragment: SearchNewsFragment)

}