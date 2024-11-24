package com.android.news.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ArticlesDao {
//companion object{
//    const val DATABASE_NAME="articles_db"
//
//}
@Query("SELECT * FROM articleentity")
    suspend fun getArticleList(): List<ArticleEntity>
// to pass the articles to database
    // this upsert function is used for both insert and update
    @Upsert
    suspend fun upsertArticleList(articleList: List<ArticleEntity>)

    @Query("SELECT * FROM articleentity WHERE articleId = :articleId")
    suspend fun getArticle(articleId: String): ArticleEntity?

    @Query("DELETE FROM articleentity")
    suspend fun clearDatabase()

}