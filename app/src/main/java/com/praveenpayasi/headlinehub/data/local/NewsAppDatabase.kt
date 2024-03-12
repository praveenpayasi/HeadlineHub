package com.praveenpayasi.headlinehub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.praveenpayasi.headlinehub.data.local.dao.TopHeadlinesDao
import com.praveenpayasi.headlinehub.data.local.entity.TopHeadlineEntity

@Database(
    entities = [TopHeadlineEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsAppDatabase : RoomDatabase() {

    abstract fun topHeadlinesDao(): TopHeadlinesDao

}