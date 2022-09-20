package test.karpenko.newsapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.karpenko.newsapp.data.api.NewsRepository
import test.karpenko.newsapp.models.Article
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _favoriteLiveData: MutableLiveData<List<Article>> = MutableLiveData()
    val favoriteLiveData: LiveData<List<Article>>
        get() = _favoriteLiveData


    /*fun getFavoriteNews() = viewModelScope.launch(Dispatchers.IO) {
        _favoriteLiveData.postValue(repository.getFavoriteArticles())
    }*/

    fun getFavoriteNews() = repository.getFavoriteArticles()

    fun deleteFromFavoriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFromFavoriteArticle(article)
    }

    fun saveToFavoriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavoriteArticle(article)
    }

}