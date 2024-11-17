package com.android.news.core.domain

    sealed class NewsResult<T>(
        val date : T?=null,
        val message : String?
    ) {
    class Success<T>(date: T) : NewsResult<T>(date,null)
        class Error<T>(message: String) : NewsResult<T>(null,message)
    }