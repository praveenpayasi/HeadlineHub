package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.model.Language
import com.praveenpayasi.headlinehub.di.ActivityScope
import com.praveenpayasi.headlinehub.ui.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class LanguageListRepository @Inject constructor() {

    fun getLanguages(): Flow<List<Language>> {
        return flow { emit(AppConstant.LANGUAGES) }
    }

}