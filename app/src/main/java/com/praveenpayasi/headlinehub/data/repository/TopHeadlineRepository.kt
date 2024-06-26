package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.api.NetworkService
import com.praveenpayasi.headlinehub.data.model.topheadlines.ApiTopHeadlines
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(countryID: String): Flow<List<ApiTopHeadlines>> {
        return flow { emit(networkService.getTopHeadlines(countryID)) }
            .map {
                it.apiTopHeadlines
            }
    }

}