package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.model.Language
import com.praveenpayasi.headlinehub.ui.utils.AppConstant
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class LanguageListRepository @Inject constructor() {

    fun getLanguages(): Flow<List<Language>> {
        return flow { emit(AppConstant.LANGUAGES) }
    }

}