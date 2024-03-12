package com.praveenpayasi.headlinehub.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.praveenpayasi.headlinehub.data.repository.NewsRepository
import com.praveenpayasi.headlinehub.data.repository.NewsSourceRepository
import com.praveenpayasi.headlinehub.data.repository.TopHeadlinePaginationRepository
import com.praveenpayasi.headlinehub.data.repository.TopHeadlineRepository
import com.praveenpayasi.headlinehub.di.ActivityContext
import com.praveenpayasi.headlinehub.ui.base.ViewModelProviderFactory
import com.praveenpayasi.headlinehub.ui.news.NewsListAdapter
import com.praveenpayasi.headlinehub.ui.news.NewsListViewModel
import com.praveenpayasi.headlinehub.ui.pagination.TopHeadlinePaginationAdapter
import com.praveenpayasi.headlinehub.ui.pagination.TopHeadlinePaginationViewModel
import com.praveenpayasi.headlinehub.ui.sources.NewsSourceAdapter
import com.praveenpayasi.headlinehub.ui.sources.NewsSourcesViewModel
import com.praveenpayasi.headlinehub.ui.topheadline.TopHeadlineAdapter
import com.praveenpayasi.headlinehub.ui.topheadline.TopHeadlineViewModel
import com.praveenpayasi.headlinehub.ui.utils.DispatcherProvider
import com.praveenpayasi.headlinehub.ui.utils.NetworkHelper
import com.praveenpayasi.headlinehub.ui.utils.logger.Logger
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideTopHeadLinesViewModel(
        topHeadlineRepository: TopHeadlineRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider,
        logger: Logger
    ): TopHeadlineViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(TopHeadlineViewModel::class) {
            TopHeadlineViewModel(
                topHeadlineRepository,dispatcherProvider, networkHelper, logger
            )
        })[TopHeadlineViewModel::class.java]
    }

    @Provides
    fun provideTopHeadLinesPaginationViewModel(
        paginationTopHeadlineRepository: TopHeadlinePaginationRepository,
        dispatcherProvider: DispatcherProvider
    ): TopHeadlinePaginationViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlinePaginationViewModel::class) {
                TopHeadlinePaginationViewModel(
                    paginationTopHeadlineRepository, dispatcherProvider
                )
            })[TopHeadlinePaginationViewModel::class.java]
    }

    @Provides
    fun provideNewsViewModel(
        newsRepository: NewsRepository,
        logger: Logger,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): NewsListViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsListViewModel::class) {
            NewsListViewModel(newsRepository, logger, dispatcherProvider, networkHelper)
        })[NewsListViewModel::class.java]
    }

    @Provides
    fun provideNewsSourcesViewModel(
        newsSourcesRepository: NewsSourceRepository,
        logger: Logger,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider
    ): NewsSourcesViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(NewsSourcesViewModel::class) {
            NewsSourcesViewModel(newsSourcesRepository, logger, dispatcherProvider, networkHelper)
        })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideTopHeadlinePaginationAdapter() = TopHeadlinePaginationAdapter()


    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(ArrayList())

    @Provides
    fun provideNewsAdapter() = NewsListAdapter(ArrayList())

}