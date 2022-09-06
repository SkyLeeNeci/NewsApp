package test.karpenko.newsapp.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import test.karpenko.newsapp.models.NewsResponse
import test.karpenko.newsapp.utils.Constants.Companion.API_KEY

interface NewsService {

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") countryCode: String = "ua",
        @Query("page") page:Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ):Response<NewsResponse>
}