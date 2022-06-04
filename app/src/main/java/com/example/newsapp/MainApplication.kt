package com.example.newsapp

import android.app.Application
import com.example.githubapp.core.di.component.AppComponent
import com.example.githubapp.core.di.component.DaggerAppComponent

class MainApplication : Application(){

    lateinit var component : AppComponent

    override fun onCreate() {
        super.onCreate()

        createComponent()
    }

    private fun createComponent() {
        component = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}