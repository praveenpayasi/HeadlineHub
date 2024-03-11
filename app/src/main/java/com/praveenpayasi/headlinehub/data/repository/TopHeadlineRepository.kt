package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.api.NetworkService
import com.praveenpayasi.headlinehub.data.model.topheadlines.Article
import com.praveenpayasi.headlinehub.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlinesArticles(countryID: String): Flow<List<Article>> {
        return flow { emit(networkService.getTopHeadlines(countryID)) }
            .map {
                it.apiArticles
            }
    }
}