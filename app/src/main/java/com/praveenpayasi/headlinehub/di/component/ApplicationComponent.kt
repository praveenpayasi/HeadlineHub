package com.praveenpayasi.headlinehub.di.component

import android.content.Context
import com.praveenpayasi.headlinehub.HeadlineHubApplication
import com.praveenpayasi.headlinehub.data.api.NetworkService
import com.praveenpayasi.headlinehub.di.ApplicationContext
import com.praveenpayasi.headlinehub.di.module.ApplicationModule
import com.praveenpayasi.headlinehub.ui.utils.DispatcherProvider
import com.praveenpayasi.headlinehub.ui.utils.NetworkHelper
import com.praveenpayasi.headlinehub.ui.utils.logger.Logger
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: HeadlineHubApplication)

    @ApplicationContext
    fun getApplicationContext(): Context

    fun getNetworkService(): NetworkService

    fun getNetworkHelper(): NetworkHelper

    fun getDispatcherProvider(): DispatcherProvider

    fun getLoggerProvider(): Logger

}