package com.example.githubapp.core.di.component

import com.example.githubapp.core.di.scopes.PerActivity
import com.example.newsapp.core.di.modules.ActivityModule
import dagger.Component


@PerActivity
@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent