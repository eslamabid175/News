package com.android.news.core.domain.repositories

import com.android.news.core.domain.models.Article
import com.android.news.core.domain.models.NewsList
import com.android.news.core.domain.useCases.NewsResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
suspend fun getNews():Flow<NewsResult<NewsList>>
suspend fun pagginate(nextPage:String?):Flow<NewsResult<NewsList>>
suspend fun getArticle(articleId:String):Flow<NewsResult<Article>>
}