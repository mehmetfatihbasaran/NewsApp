package com.example.newsapp.ui.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.utils.DummyData
import com.example.newsapp.utils.DummyData.getTimeAgo
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(navController: NavController, article: Article, scrollState: ScrollState) {

    Scaffold(
        topBar = {
            DetailTopAppBar(
                onBackPressed = { navController.navigate("TopNews") }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(it)
                .scrollable(state = scrollState, orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)
            CoilImage(
                imageModel = { article.urlToImage ?: R.drawable.breaking_news },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconWithText(
                    icon = Icons.Default.Edit,
                    text = article.author ?: "Unknown"
                )
                DummyData.stringToDate(article.publishedAt)?.let { it1 ->
                    IconWithText(
                        icon = Icons.Default.DateRange,
                        text = it1.getTimeAgo()
                    )
                }
            }
            Text(text = article.title, fontWeight = FontWeight.Bold)
            Text(
                text = article.description ?: "No Description",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .scrollable(state = scrollState, orientation = Orientation.Vertical)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit) {

    TopAppBar(
        title = {
            Text(text = "Detail Screen")
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressed()
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )

}

@Composable
fun IconWithText(icon: ImageVector, text: String?) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = text ?: "No Text",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(
                id = R.color.purple_200
            )
        )
        if (text != null) {
            Text(text = text, fontWeight = FontWeight.SemiBold)
        }
    }
}