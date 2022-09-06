package test.karpenko.newsapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.karpenko.newsapp.models.Article

interface ArticleDao {

    @Query("select * from articles")
    suspend fun getAllArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertArticle(article: Article)

     @Delete
     suspend fun deleteArticle(article: Article)

}