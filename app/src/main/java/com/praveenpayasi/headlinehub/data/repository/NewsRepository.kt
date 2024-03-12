package com.praveenpayasi.headlinehub.data.repository

import com.praveenpayasi.headlinehub.data.api.NetworkService
import com.praveenpayasi.headlinehub.data.local.DatabaseService
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import com.praveenpayasi.headlinehub.data.model.topheadlines.toArticleLanguage
import com.praveenpayasi.headlinehub.data.model.topheadlines.toTopHeadlineEntity
import com.praveenpayasi.headlinehub.di.ActivityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@ActivityScope
@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getNewsBySources(sourceId: String): Flow<List<TopHeadlineEntity>> {
        return flow { emit(networkService.getNewsBySources(sourceId)) }
            .map {
                it.apiTopHeadlines.map { apiArticle -> apiArticle.toTopHeadlineEntity(sourceId) }
            }.flatMapConcat { articles ->
                flow { emit(databaseService.deleteAllAndInsertAllSourceNews(articles, sourceId)) }
            }.flatMapConcat {
                databaseService.getSourceNewsByDB(sourceId)
            }
    }

    fun getNewsBySourceByDB(sourceId: String): Flow<List<TopHeadlineEntity>> {
        return databaseService.getSourceNewsByDB(sourceId)
    }

    fun getNewsByCountry(countryId: String): Flow<List<TopHeadlineEntity>> {
        return flow { emit(networkService.getNewsByCountry(countryId)) }
            .map {
                it.apiTopHeadlines.map { apiArticle -> apiArticle.toTopHeadlineEntity(countryId) }
            }.flatMapConcat { articles ->
                flow {
                    emit(
                        databaseService.deleteAndInsertAllTopHeadlinesArticles(articles, countryId)
                    )
                }
            }.flatMapConcat {
                databaseService.getAllTopHeadlinesArticles(countryId)
            }
    }

    fun getNewsByCountryByDB(countryId: String): Flow<List<TopHeadlineEntity>> {
        return databaseService.getAllTopHeadlinesArticles(countryId)
    }

    fun getNewsByLanguage(languageId: String): Flow<List<TopHeadlineEntity>> {
        return flow { emit(networkService.getNewsByLanguage(languageId)) }
            .map {
                it.apiTopHeadlines.map { apiArticle -> apiArticle.toArticleLanguage(languageId) }
            }.flatMapConcat { articles ->
                flow {
                    emit(
                        databaseService.deleteAllAndInsertAllLanguageArticles(articles, languageId)
                    )
                }
            }.flatMapConcat {
                databaseService.getLanguageNews(languageId)
            }
    }

    fun getNewsByLanguageByDB(languageId: String): Flow<List<TopHeadlineEntity>> {
        return databaseService.getLanguageNews(languageId)
    }

}