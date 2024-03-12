package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.api.NetworkService
import com.praveenpayasi.headlinehub.data.local.DatabaseService
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.data.model.topheadlines.ApiTopHeadlines
import com.praveenpayasi.headlinehub.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class OfflineTopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getTopHeadlines(countryID: String): Flow<List<ApiTopHeadlines>> {
        return flow { emit(networkService.getTopHeadlines(countryID)) }
            .map {
                it.apiTopHeadlines
            }
    }

    fun deleteAndInsertAllTopHeadlinesArticles(topHeadlineEntities: List<TopHeadlineEntity>, country: String) {
        databaseService.deleteAndInsertAllTopHeadlinesArticles(topHeadlineEntities, country)
    }

    fun getTopHeadlinesFromDB(countryID: String): Flow<List<TopHeadlineEntity>> {
        return databaseService.getAllTopHeadlinesArticles(countryID)
    }
}