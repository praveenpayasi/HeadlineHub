package com.praveenpayasi.headlinehub.data.local

import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import kotlinx.coroutines.flow.Flow

class AppDatabaseService constructor(private val appDatabase: NewsAppDatabase) : DatabaseService {

    override fun getAllTopHeadlinesArticles(countryID: String): Flow<List<TopHeadlineEntity>> {
        return appDatabase.topHeadlinesDao().getTopHeadlinesArticles(countryID)
    }

    override fun deleteAndInsertAllTopHeadlinesArticles(
        topHeadlineEntities: List<TopHeadlineEntity>,
        countryID: String
    ) {
        appDatabase.topHeadlinesDao().deleteAndInsertAllTopHeadlinesArticles(topHeadlineEntities, countryID)
    }

}