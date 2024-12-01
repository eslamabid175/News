package com.android.news.core.domain.useCases

    sealed class NewsResult<T>(
        val data : T?=null,
        val message : String?
    ) {
    class Success<T>(date: T) : NewsResult<T>(date,null)
        class Error<T>(message: String) : NewsResult<T>(null,message)
    }