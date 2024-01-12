package com.example.newsapp.network

import com.example.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
    ): NewsResponse

    @GET("everything")
    suspend fun bySources(
        @Query("sources") source: String
    ): NewsResponse

    @GET("top-headlines")
    suspend fun byCategory(
        @Query("category") category: String,
    ): NewsResponse

    @GET("everything")
    suspend fun byQuery(
        @Query("q") query: String
    ): NewsResponse

}