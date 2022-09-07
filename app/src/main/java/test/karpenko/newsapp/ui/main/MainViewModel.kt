package test.karpenko.newsapp.ui.main

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
class MainViewModel @Inject constructor( private val repository: NewsRepository ) : ViewModel() {

    private val _newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val newsLiveData: LiveData<Resource<NewsResponse>>
    get() = _newsLiveData

    private var newsPage = 1

    init {
        getTopNews("ua")
    }

    private fun getTopNews(countryCode: String){
        viewModelScope.launch {
            _newsLiveData.postValue(Resource.Loading())
            val response = repository.getTopNews(countryCode, newsPage)
            if (response.isSuccessful){
                response.body().let {
                    _newsLiveData.postValue(Resource.Success(it))
                }
            }else {
                _newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }
    }

}