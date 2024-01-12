package com.example.newsapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.components.SearchBar
import com.example.newsapp.model.Article
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.viewmodel.MainViewModel
import com.example.newsapp.utils.DummyData
import com.example.newsapp.utils.DummyData.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(
    navController: NavController,
    queryFlow: MutableState<String>,
    viewModel: MainViewModel
) {

    val news = viewModel.newsResponse.collectAsState().value
    val topNewsList = news.articles
    val searchedNews = viewModel.searchedNewsResponse.collectAsState().value

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(query = queryFlow, viewModel = viewModel)
        val resultList = mutableListOf<Article>()
        if (queryFlow.value.isNotEmpty()) {
            searchedNews.articles?.let {
                resultList.addAll(it)
            }
        } else {
            resultList.addAll(topNewsList ?: emptyList())
        }
        if (resultList.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(resultList) { article ->
                    val index = resultList.indexOf(article)
                    TopNewsItem(article = article) {
                        navController.navigate("Detail/${index}")
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.navigate("Detail")
            }
        ) {
            Text(text = "Click to see news", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun TopNewsItem(article: Article, onNewsClick: () -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
            .clickable {
                onNewsClick()
            }
    ) {
        CoilImage(
            imageModel = { article.urlToImage ?: R.drawable.breaking_news },
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            DummyData.stringToDate(article.publishedAt)
                ?.let {
                    Text(
                        text = it.getTimeAgo(),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = article.title, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }

}