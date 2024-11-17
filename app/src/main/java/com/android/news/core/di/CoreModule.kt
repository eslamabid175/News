package com.android.news.core.di

import androidx.room.Room
import com.android.news.core.data.local.ArticleDataBase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


// module is used to define the dependencies that we want to inject and provide it to the view model or other classes
val coreMOdule= module {
// single is used to define the single instance of the class
    // and it will be shared between all the view models and other classes
    single {
       Room.databaseBuilder(androidApplication(),
           ArticleDataBase::class.java,
           "article_db.db"
           ).build()
    }
    //to provide dao
    single {
// get is used to get the instance of the database
        // and .dao is used to get the dao and return it to provide it to the repository and  view model and other classes
        get<ArticleDataBase>().dao
    }
    // to provide the http client and it will be shared between all the view models and other classes
    single {
        // cio is used to make the http requests
        HttpClient(CIO) {
            expectSuccess = true

            engine {
                endpoint {
                    keepAliveTime = 5000
                    connectTimeout = 5000
                    connectAttempts = 3
                }
            }
// to make the http requests in a json format
            install(ContentNegotiation) {
                // json is used to make the http requests in a json format
                json(
                    // Json is used to make the http requests in a json format
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
// to make the http requests in a json format
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
// to make the http requests in a json format and log what we get
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}