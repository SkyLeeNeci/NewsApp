package test.karpenko.newsapp.data.api

import javax.inject.Inject

class NewsRepository @Inject constructor(private val service: NewsService) {

    suspend fun getTopNews(countryCode: String, pageNumber: Int) =
        service.getTopHeadlines(countryCode, pageNumber)

}