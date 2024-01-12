package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.asStateFlow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Sources(
    viewModel: MainViewModel
) {
    val items = listOf(
        "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sources: " + viewModel.sourceName.asStateFlow()
                            .collectAsState().value
                    )
                },
                actions = {
                    var menuExpanded by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { menuExpanded = !menuExpanded }
                    ) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                    MaterialTheme(shapes = MaterialTheme.shapes.copy(RoundedCornerShape(16.dp))) {
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            content = {
                                items.forEach {
                                    DropdownMenuItem(
                                        onClick = {
                                            viewModel.sourceName.value = it.second
                                            viewModel.getEverythingBySources()
                                            menuExpanded = false
                                        },
                                        content = {
                                            Text(text = it.first)
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    ) {
        viewModel.getEverythingBySources()
        val article = viewModel.getEverythingBySources.collectAsState().value.articles ?: listOf()
        SourceContent(articles = article)
    }
}

@Composable
fun SourceContent(articles: List<Article>) {
    val uriHandler = LocalUriHandler.current
    LazyColumn {
        items(articles) { article ->
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "",
                )
                withStyle(
                    style = SpanStyle(Color.Blue),
                ) {
                    append("Read more: " + article.title)
                }
                pop()
            }
            Card(
                backgroundColor = colorResource(id = R.color.purple_700),
                elevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = article.title ?: "Not Available",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = article.description ?: "Not Available",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Card(
                        backgroundColor = colorResource(id = R.color.white),
                        elevation = 6.dp,
                    ) {
                        ClickableText(text = annotatedString,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                annotatedString.getStringAnnotations(it, it).firstOrNull()
                                    ?.let { result ->
                                        if (result.tag == "URL") {
                                            uriHandler.openUri(result.item)
                                        }
                                    }
                            }
                        )
                    }
                }
            }
        }
    }
}