package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.api.NetworkService
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.data.model.topheadlines.toTopHeadlineEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class SearchRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsByQueries(query: String): Flow<List<TopHeadlineEntity>> {
        return flow {
            emit(networkService.getNewsByQueries(query))
        }.map {
            it.apiTopHeadlines.map { apiArticle -> apiArticle.toTopHeadlineEntity(query) }
        }
    }
}