package com.example.newsapp.network

import com.example.newsapp.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsManager(private val service: NewsService) {

    suspend fun getTopHeadlines(country: String): NewsResponse {
        return withContext(Dispatchers.IO) {
            service.getTopHeadlines(country = country)
        }
    }


    suspend fun getEverythingBySources(source: String): NewsResponse {
        return withContext(Dispatchers.IO) {
            service.bySources(source = source)
        }
    }

    suspend fun getArticleByCategory(category: String): NewsResponse {
        return withContext(Dispatchers.IO) {
            service.byCategory(category = category)
        }
    }

    suspend fun getArticlesByQuery(query: String): NewsResponse {
        return withContext(Dispatchers.IO) {
            service.byQuery(query = query)
        }
    }

}