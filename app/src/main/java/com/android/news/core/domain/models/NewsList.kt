package com.android.news.core.domain.models

data class NewsList(
    val nextPage: String?,
    val articles: List<Article>,
)
