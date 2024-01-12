package com.example

import android.app.Application
import com.example.newsapp.network.Api
import com.example.newsapp.network.NewsManager
import com.example.newsapp.repository.Repository

class MainApp : Application() {

    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager)
    }

}