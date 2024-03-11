package com.praveenpayasi.headlinehub.di.component

import android.content.Context
import com.praveenpayasi.headlinehub.HeadlineHubApplication
import com.praveenpayasi.headlinehub.di.ApplicationContext
import com.praveenpayasi.headlinehub.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: HeadlineHubApplication)

    @ApplicationContext
    fun getApplicationContext(): Context

}