package com.android.news.core.domain

data class NewsList(
    val nextPage: String?,
    val articles: List<Article>,
)
