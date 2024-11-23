package com.android.news.core.domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
suspend fun getNews():Flow<NewsResult<NewsList>>
suspend fun pagginate(nextPage:String?):Flow<NewsResult<NewsList>>
//suspend fun getArticle(articleId:String):Flow<NewsResult<Article>>
}