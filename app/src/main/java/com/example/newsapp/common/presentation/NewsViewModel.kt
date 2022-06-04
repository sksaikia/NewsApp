package com.example.newsapp.common.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.common.data.local.ArticleEntity
import com.example.newsapp.common.data.remote.entity.NewsResponse
import com.example.newsapp.core.network.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    val newsRepository : NewsRepository
) : ViewModel() {

    private val _breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNews : LiveData<Resource<NewsResponse>> = _breakingNews
    var breakingNewsPage = 1

    private val _searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews : LiveData<Resource<NewsResponse>> = _searchNews
    var searchNewsPage = 1

//    init {
//        getBreakingNews("us")
//    }

    fun getBreakingNews(countryCode : String) {
        viewModelScope.launch {
            _breakingNews.value = Resource.Loading()
            val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
            _breakingNews.postValue(handleBreakingNewsResponse(response))

        }
    }

    fun searchNews(searchQuery : String) {
        viewModelScope.launch {
            _searchNews.value = Resource.Loading()
            val response  = newsRepository.searchNews(searchQuery, searchNewsPage)
            _searchNews.postValue(handleSearchNewsResponse(response))
        }
    }

    private fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article : ArticleEntity) {
        viewModelScope.launch {
            newsRepository.upsert(article)
        }
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: ArticleEntity) {
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
        }
    }


}