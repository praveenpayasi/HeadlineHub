package com.praveenpayasi.headlinehub.di.module

import com.praveenpayasi.headlinehub.ui.country.CountryListAdapter
import com.praveenpayasi.headlinehub.ui.language.LanguageListAdapter
import com.praveenpayasi.headlinehub.ui.news.NewsListAdapter
import com.praveenpayasi.headlinehub.ui.sources.NewsSourceAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())
    @Provides
    fun provideNewsAdapter() = NewsListAdapter(ArrayList())
    @Provides
    fun provideCountryListAdapter() = CountryListAdapter(ArrayList())
    @Provides
    fun provideLanguageListAdapter() = LanguageListAdapter(ArrayList())
}