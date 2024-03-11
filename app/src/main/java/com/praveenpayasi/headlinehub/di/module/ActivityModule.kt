package com.praveenpayasi.headlinehub.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.praveenpayasi.headlinehub.di.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }
}