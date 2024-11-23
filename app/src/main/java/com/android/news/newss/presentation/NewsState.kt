package com.android.news.newss.presentation

import androidx.collection.emptyIntList
import androidx.collection.emptyLongList
import com.android.news.core.domain.Article

data class NewsState(
    val articleList: List<Article> = emptyList()
,val nextPage:String? = null,
    val isLoading: Boolean=false,
    val isError : Boolean= false

)
