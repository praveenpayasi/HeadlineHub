package com.praveenpayasi.headlinehub

import android.app.Application
import com.praveenpayasi.headlinehub.di.component.ApplicationComponent
import com.praveenpayasi.headlinehub.di.component.DaggerApplicationComponent
import com.praveenpayasi.headlinehub.di.module.ApplicationModule

class HeadlineHubApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }

}