package com.praveenpayasi.headlinehub.data.local

import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseService {

    fun getAllTopHeadlinesArticles(countryID: String): Flow<List<TopHeadlineEntity>>

    fun deleteAndInsertAllTopHeadlinesArticles(topHeadlineEntities: List<TopHeadlineEntity>, countryID: String)

}