package com.android.news.core.data.repositoriesImpl

import io.ktor.client.request.parameter
import com.android.news.core.data.local.ArticlesDao
import com.android.news.core.data.remote.NewsListDto
import com.android.news.core.data.mappers.toArticle
import com.android.news.core.data.mappers.toArticleEntity
import com.android.news.core.data.mappers.toNewsList
import com.android.news.core.domain.models.Article
import com.android.news.core.domain.models.NewsList
import com.android.news.core.domain.repositories.NewsRepository
import com.android.news.core.domain.useCases.NewsResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl(
    //دول عشان هجيب الداتا من النت وهكيشها فهحتاج
    private val httpClient: HttpClient,
    private val dao: ArticlesDao
) : NewsRepository {
    private val tag = "NewsRepository: "
    private val baseUrl = "https://newsdata.io/api/1/latest"
    private val apiKey = "pub_589847b2dd6e317d90c96c101ac59a984286c"

    //getLocaldata(nextpage:String) is used to get the local data from the database and return it
    // and we use nextpage to get the next page of the data
    private suspend fun getLocalNews(nextPage: String?): NewsList {
        val localnews = dao.getArticleList()
        println(tag + "getLocalNews: " + localnews.size + "next page: " + nextPage)

        val newsList = NewsList(nextPage = nextPage,
            // هو هنا عاوز list of articls بس انا هجيبله من الداتا بيز article entity لذلك انا هروح اعمل ماببر بيحول
            //
            //من ده لده والعكس واي حاجه ممكن اعملها ماببر مش هيخسر
            //map is used to convert one list to another list
            articles = localnews.map { it.toArticle() }
        )

        return newsList
    }

    private suspend fun getloArticle(articleId: String?): Article {
        val localArticle = articleId?.let { dao.getArticle(it) }
        return localArticle?.toArticle()!!
    }

//    override suspend fun getArticle(articleId: String?): Article {
//        return getloArticle(articleId)
//
//    }

    private suspend fun getRemoteNews(nextPage: String?): NewsList {
        // to get remote list of news
        val newsListdto: NewsListDto = httpClient.get(baseUrl) {

            parameter("apikey", apiKey)
            parameter("language", "en")
            if (nextPage != null) {
                parameter("page", nextPage)
            }

        }.body()
        println(tag + "getRemoteNews: " + newsListdto.results?.size + "next page: " + nextPage)

        return newsListdto.toNewsList()
    }

    // this is the implementation of the news repository
    override suspend fun getNews(): Flow<NewsResult<NewsList>> {
        return flow {
            //get the remote source
            val remoteNewsList = try {
                getRemoteNews(null)
            } catch (e: Exception) {
                //printstackTrace() is used to print the stack trace of the exception
                e.printStackTrace()
                println(tag + "getNewsRemoteException: " + e.message)
                null
            }

            //let is used to check if the remoteNewsList is not null and return it
            //return it by passing it to db and emit it to the flow
            remoteNewsList?.let {
                dao.clearDatabase()
                dao.upsertArticleList(remoteNewsList.articles.map { it.toArticleEntity() })
                emit(NewsResult.Success(getLocalNews(nextPage = remoteNewsList.nextPage)))
                //emit is used to emit the data to the flow
                return@flow
            }
            //if we dont have internet we will get the data from the database
            val localNewsList = getLocalNews(null)
            if (localNewsList.articles.isNotEmpty()) {
                emit(NewsResult.Success(localNewsList))
                return@flow
            }
            emit(NewsResult.Error("Something went wrong"))
        }

    }


    override suspend fun pagginate(nextPage: String?): Flow<NewsResult<NewsList>> {
        return flow {
            //get the remote source
            val remoteNewsList = try {
                getRemoteNews(nextPage)
            } catch (e: Exception) {
                //printstackTrace() is used to print the stack trace of the exception
                e.printStackTrace()
                println(tag + "paggingException: " + e.message)
                null
            }

            //let is used to check if the remoteNewsList is not null and return it
            //return it by passing it to db and emit it to the flow
            remoteNewsList?.let {
                dao.clearDatabase()
                dao.upsertArticleList(remoteNewsList.articles.map { it.toArticleEntity() })
                // not get them from the database because we already have them in the database
                //because we also get old items that we already have in the database
                emit(NewsResult.Success(remoteNewsList))
                //emit is used to emit the data to the flow
                return@flow
            }

        }
    }

    override suspend fun getArticle(articleId: String): Flow<NewsResult<Article>> {
        return flow {
        dao.getArticle(articleId)?.let { article ->
            println(tag + " get local article" + article.articleId)
        emit(NewsResult.Success(article.toArticle()))
return@flow
        }
            // if the pervies code doesnt excute so we must catch it from (api using try catch)
            try {
val remoteArticle:NewsListDto=httpClient.get(baseUrl){
    parameter("apiKey",apiKey)
    parameter("id",articleId)
}.body()
println(tag+"get article remotly "+remoteArticle.results?.size)
            if(remoteArticle.results?.isNotEmpty()==true){
                emit(NewsResult.Success(remoteArticle.results[0].toArticle()))

            }else {
                emit(NewsResult.Error("Can't load articlee"))
            }
            }catch (e:Exception){
                // e.printStackTrace() is used to print ....
                e.printStackTrace()
                if (e is CancellationException) throw e
                println(tag + "getting article remote exeption "+e.message)
                emit(NewsResult.Error("Can't load article"))

            }

        }
    }

}