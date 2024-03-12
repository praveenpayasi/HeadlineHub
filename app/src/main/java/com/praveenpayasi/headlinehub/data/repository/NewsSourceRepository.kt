package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.api.NetworkService
import com.praveenpayasi.headlinehub.data.local.DatabaseService
import com.praveenpayasi.headlinehub.data.local.entity.NewsSources
import com.praveenpayasi.headlinehub.data.model.newssource.asSource
import com.praveenpayasi.headlinehub.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class NewsSourceRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getNewsSources(): Flow<List<NewsSources>> {
        return flow { emit(networkService.getNewsSources()) }
            .map {
                it.newsSource.map { apiSource -> apiSource.asSource() }
            }.flatMapConcat { apiSource ->
                flow { emit(databaseService.deleteAndInsertAllNewsSources((apiSource))) }
            }.flatMapConcat {
                databaseService.getNewsSources()
            }
    }

    fun getNewsSourcesFromDB(): Flow<List<NewsSources>> {
        return databaseService.getNewsSources()
    }

}