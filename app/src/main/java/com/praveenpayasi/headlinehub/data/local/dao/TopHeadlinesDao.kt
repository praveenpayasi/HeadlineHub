package com.praveenpayasi.headlinehub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopHeadlinesDao {

    @Transaction
    @Query("SELECT * FROM TopHeadlinesArticle WHERE country =:country")
    fun getTopHeadlinesArticles(country: String): Flow<List<TopHeadlineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopHeadlineArticles(topHeadlineEntities: List<TopHeadlineEntity>): List<Long>

    @Query("DELETE FROM TopHeadlinesArticle WHERE country = :country ")
    fun clearTopHeadlinesArticles(country: String)

    @Transaction
    fun deleteAndInsertAllTopHeadlinesArticles(
        topHeadlineEntities: List<TopHeadlineEntity>, country: String
    ): List<Long> {
        clearTopHeadlinesArticles(country)
        return insertTopHeadlineArticles(topHeadlineEntities)
    }

    @Query("SELECT * FROM TopHeadlinesArticle WHERE sourceId =:sourceId")
    fun getSourceArticle(sourceId: String): Flow<List<TopHeadlineEntity>>

    @Query("DELETE FROM TopHeadlinesArticle WHERE sourceId = :sourceId ")
    fun clearSourceArticles(sourceId: String)

    @Transaction
    fun deleteAllAndInsertAllSourceArticles(topHeadlineEntities: List<TopHeadlineEntity>, sourceID: String): List<Long> {
        clearSourceArticles(sourceID)
        return insertTopHeadlineArticles(topHeadlineEntities)
    }

    @Query("SELECT * FROM TopHeadlinesArticle WHERE language =:languageId")
    fun getLanguageArticles(languageId: String): Flow<List<TopHeadlineEntity>>

    @Query("DELETE FROM TopHeadlinesArticle WHERE language = :languageId ")
    fun clearLanguageArticles(languageId: String)

    @Transaction
    fun deleteAllAndInsertAllLanguageArticles(
        topHeadlineEntities: List<TopHeadlineEntity>,
        languageId: String
    ): List<Long> {
        clearLanguageArticles(languageId)
        return insertTopHeadlineArticles(topHeadlineEntities)
    }

}