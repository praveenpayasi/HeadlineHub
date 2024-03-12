package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.model.Country
import com.praveenpayasi.headlinehub.di.ActivityScope
import com.praveenpayasi.headlinehub.ui.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class CountryListRepository @Inject constructor() {

    fun getCountry(): Flow<List<Country>> {
        return flow { emit(AppConstant.COUNTRIES) }
    }
}