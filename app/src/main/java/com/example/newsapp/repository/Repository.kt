package com.example.newsapp.repository

import com.example.newsapp.network.NewsManager

class Repository(private val manager: NewsManager) {

    suspend fun getArticlesByQuery(query: String) = manager.getArticlesByQuery(query)
    suspend fun getArticleByCategory(category: String) = manager.getArticleByCategory(category)
    suspend fun getEverythingBySources(source: String) = manager.getEverythingBySources(source)
    suspend fun getTopHeadlines(country: String) = manager.getTopHeadlines(country)

}