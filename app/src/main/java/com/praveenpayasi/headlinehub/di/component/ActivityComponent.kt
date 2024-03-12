package com.praveenpayasi.headlinehub.di.component

import com.praveenpayasi.headlinehub.di.ActivityScope
import com.praveenpayasi.headlinehub.di.module.ActivityModule
import com.praveenpayasi.headlinehub.ui.offline.OfflineTopHeadlineActivity
import com.praveenpayasi.headlinehub.ui.pagination.TopHeadlinePaginationActivity
import com.praveenpayasi.headlinehub.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

    fun inject(activity: OfflineTopHeadlineActivity)

    fun inject(activity: TopHeadlinePaginationActivity)

}