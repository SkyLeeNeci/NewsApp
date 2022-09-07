package test.karpenko.newsapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import test.karpenko.newsapp.data.api.NewsRepository
import test.karpenko.newsapp.models.NewsResponse
import test.karpenko.newsapp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor( private val newsRepository: NewsRepository): ViewModel() {

    private val _searchNewsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNewsLiveData: LiveData<Resource<NewsResponse>>
        get() = _searchNewsLiveData

    private var searchNewsPage = 1

    init {
        getSearchNews("")
    }

     fun getSearchNews(query: String) {
        viewModelScope.launch {
            _searchNewsLiveData.postValue(Resource.Loading())
            val response = newsRepository.searchNews(query = query, pageNumber = searchNewsPage)
            if (response.isSuccessful){
                response.body().let {
                    _searchNewsLiveData.postValue(Resource.Success(it))
                }
            }else{
                response.body().let {
                    _searchNewsLiveData.postValue(Resource.Error(message = response.message()))
                }
            }
        }
    }

}