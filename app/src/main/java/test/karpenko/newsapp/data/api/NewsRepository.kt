package test.karpenko.newsapp.data.api

import javax.inject.Inject

class NewsRepository @Inject constructor(private val service: NewsService) {

    suspend fun getTopNews(countryCode: String, pageNumber: Int) =
        service.getTopHeadlines(countryCode, pageNumber)

    suspend fun searchNews(query: String, pageNumber: Int) =
        service.getEverything(query = query, page = pageNumber)

}