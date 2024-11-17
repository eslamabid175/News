package com.android.news

import android.app.Application
import com.android.news.core.di.coreMOdule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
    startKoin{
        //androidContext is used for
        androidContext(this@App)
        modules(
            coreMOdule
        )
    }
    }
}