package com.example.newsapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.ui.viewmodel.MainViewModel
import com.example.newsapp.utils.DummyData
import com.example.newsapp.utils.DummyData.getTimeAgo
import com.example.newsapp.utils.getAllCategories
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun Categories(onFetchCategory: (String) -> Unit = {}, viewModel: MainViewModel) {

    val tabItems = getAllCategories()

    Column {
        LazyRow {
            items(tabItems) {
                CategoryTabs(
                    category = it.categoryName,
                    onFetchCategory = onFetchCategory,
                    isSelected = it == viewModel.selectedCategory.asStateFlow()
                        .collectAsState().value
                )
            }
        }
        val articles = viewModel.getArticleByCategory.collectAsState().value.articles
        if (articles.isNullOrEmpty()) {
            Text(text = "Select a category")
        } else {
            ArticleContent(article = articles)
        }
    }
}

@Composable
fun CategoryTabs(
    category: String,
    isSelected: Boolean = false,
    onFetchCategory: (String) -> Unit
) {

    val context = LocalContext.current
    val background =
        if (isSelected) colorResource(id = R.color.purple_200) else colorResource(id = R.color.purple_700)

    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ArticleContent(
    article: List<Article>,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(article) { article ->
            Card(modifier = modifier.padding(8.dp)) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(7.dp)
                ) {
                    CoilImage(
                        imageModel = { article.urlToImage ?: article.url },
                        modifier = Modifier.size(100.dp),
                    )
                    Column(modifier = Modifier.padding(7.dp)) {
                        Text(
                            text = article.title ?: "Not Available",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 9,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = article.author ?: "Not Available")
                            Text(
                                text =
                                DummyData.stringToDate(article.publishedAt)?.getTimeAgo()
                                    ?: "Not Available"
                            )
                        }
                    }
                }
            }
        }
    }
}