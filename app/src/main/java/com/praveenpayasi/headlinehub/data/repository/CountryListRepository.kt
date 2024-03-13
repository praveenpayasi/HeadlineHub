package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.model.Country
import com.praveenpayasi.headlinehub.ui.utils.AppConstant
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class CountryListRepository @Inject constructor() {

    fun getCountry(): Flow<List<Country>> {
        return flow { emit(AppConstant.COUNTRIES) }
    }
}