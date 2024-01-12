package com.example.newsapp.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.MainApp
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.utils.ArticleCategory
import com.example.newsapp.utils.getCategoriesByCategoryName
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(NewsResponse())
    val newsResponse: StateFlow<NewsResponse>
        get() = _newsResponse

    private val _searchedNewsResponse = MutableStateFlow(NewsResponse())
    val searchedNewsResponse: StateFlow<NewsResponse>
        get() = _searchedNewsResponse


    private val _getArticleByCategory = MutableStateFlow(NewsResponse())
    val getArticleByCategory: StateFlow<NewsResponse>
        get() = _getArticleByCategory


    private val _getEverythingBySources = MutableStateFlow(NewsResponse())
    val getEverythingBySources: StateFlow<NewsResponse>
        get() = _getEverythingBySources

    val sourceName = MutableStateFlow("abc-news")
    val query = mutableStateOf("")
    val selectedCategory: MutableStateFlow<ArticleCategory> =
        MutableStateFlow(ArticleCategory.GENERAL)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading


    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean>
        get() = _isError


    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable.message != null) {
            _isError.value = true
        }
    }

    fun getTopHeadlines(country: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _newsResponse.value = repository.getTopHeadlines(country)
        }
        _isLoading.value = false
    }

    fun getArticlesByCategory(category: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _getArticleByCategory.value = repository.getArticleByCategory(category)
        }
        _isLoading.value = false
    }


    fun getEverythingBySources() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _getEverythingBySources.value =
                repository.getEverythingBySources(source = sourceName.value)
        }
        _isLoading.value = false
    }

    fun getArticlesByQuery(query: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _searchedNewsResponse.value = repository.getArticlesByQuery(query)
        }
        _isLoading.value = false
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getCategoriesByCategoryName(category)
        selectedCategory.value = newCategory
    }

}