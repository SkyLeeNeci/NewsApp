package test.karpenko.newsapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import test.karpenko.newsapp.models.Article

@Dao
interface ArticleDao {

    @Query("select * from articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

}