package com.android.news.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleEntity::class],
    //version = 1 is mean that the database is the first version
    version = 1
)

abstract class ArticleDataBase :RoomDatabase() {
abstract val dao:ArticlesDao
}