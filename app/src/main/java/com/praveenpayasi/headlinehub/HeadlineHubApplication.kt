package com.praveenpayasi.headlinehub

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HeadlineHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}