package com.example.newsapp.common.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.*
import com.example.newsapp.MainApplication
import com.example.newsapp.common.data.local.ArticleEntity
import com.example.newsapp.common.data.remote.entity.NewsResponse
import com.example.newsapp.core.network.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    app : Application,
    val newsRepository : NewsRepository
) : AndroidViewModel(app) {

    private val _breakingNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNews : LiveData<Resource<NewsResponse>> = _breakingNews
    var breakingNewsPage = 1
    var breakingNewsResponse : NewsResponse? = null

    private val _searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews : LiveData<Resource<NewsResponse>> = _searchNews
    var searchNewsPage = 1
    var searchNewsResponse : NewsResponse? = null

//    init {
//        getBreakingNews("us")
//    }

    fun getBreakingNews(countryCode : String) {
        viewModelScope.launch {
            safeBreakingNewsCall(countryCode)

        }
    }

    fun searchNews(searchQuery : String) {
        viewModelScope.launch {
           safeSearchNewsCall(searchQuery)
        }
    }

    private fun handleBreakingNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null){
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response : Response<NewsResponse>) : Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null){
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success(searchNewsResponse ?: resultResponse)
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

    private suspend fun safeBreakingNewsCall(countryCode: String) {
        _breakingNews.value = Resource.Loading()
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
                _breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                _breakingNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t : Throwable) {
            when(t) {
                is IOException -> _breakingNews.postValue(Resource.Error("Network Failure"))
                else -> _breakingNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeSearchNewsCall(searchQuery: String) {
        _searchNews.value = Resource.Loading()
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery,searchNewsPage)
                _searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                _searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch (t : Throwable) {
            when(t) {
                is IOException -> _searchNews.postValue(Resource.Error("Network Failure"))
                else -> _searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection() : Boolean {
        val connectivityManager = getApplication<MainApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }

        }else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }



}