package com.praveenpayasi.headlinehub.data.local

import com.praveenpayasi.headlinehub.data.local.entity.NewsSources
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

    override fun getNewsSources(): Flow<List<NewsSources>> {
        return appDatabase.newsSourceDao().getSourcesNews()
    }

    override fun deleteAndInsertAllNewsSources(articles: List<NewsSources>) {
        appDatabase.newsSourceDao().deleteAndInsertAllSourceNews(articles)
    }

    override fun getSourceNewsByDB(sourceID: String): Flow<List<TopHeadlineEntity>> {
        return appDatabase.topHeadlinesDao().getSourceArticle(sourceID)
    }

    override fun deleteAllAndInsertAllSourceNews(articles: List<TopHeadlineEntity>, sourceID: String) {
        appDatabase.topHeadlinesDao().deleteAllAndInsertAllSourceArticles(articles, sourceID)
    }

    override fun getLanguageNews(languageID: String): Flow<List<TopHeadlineEntity>> {
        return appDatabase.topHeadlinesDao().getLanguageArticles(languageID)
    }

    override fun deleteAllAndInsertAllLanguageArticles(
        articles: List<TopHeadlineEntity>,
        languageID: String
    ) {
        appDatabase.topHeadlinesDao().deleteAllAndInsertAllLanguageArticles(articles, languageID)
    }

}