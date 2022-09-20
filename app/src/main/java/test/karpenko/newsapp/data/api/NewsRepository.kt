package test.karpenko.newsapp.data.api

import test.karpenko.newsapp.data.database.ArticleDao
import test.karpenko.newsapp.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(private val service: NewsService, private val articleDao: ArticleDao) {

    suspend fun getTopNews(countryCode: String, pageNumber: Int) =
        service.getTopHeadlines(countryCode, pageNumber)

    suspend fun searchNews(query: String, pageNumber: Int) =
        service.getEverything(query = query, page = pageNumber)

    fun getFavoriteArticles() = articleDao.getAllArticles()

    suspend fun addToFavoriteArticle(article: Article) = articleDao.insertArticle(article)
    suspend fun deleteFromFavoriteArticle(article: Article) = articleDao.deleteArticle(article)

}