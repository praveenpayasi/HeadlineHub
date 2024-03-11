package com.praveenpayasi.headlinehub.di.component

import com.praveenpayasi.headlinehub.di.ActivityScope
import com.praveenpayasi.headlinehub.di.module.ActivityModule
import dagger.Component


@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

}