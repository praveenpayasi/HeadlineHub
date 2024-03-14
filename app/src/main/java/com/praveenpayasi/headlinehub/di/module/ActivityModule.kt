package com.praveenpayasi.headlinehub.di.module

import com.praveenpayasi.headlinehub.ui.news.NewsListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun provideNewsAdapter() = NewsListAdapter(ArrayList())

}