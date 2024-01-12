package com.example.newsapp.navigation

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapp.ui.screen.Categories
import com.example.newsapp.ui.screen.DetailScreen
import com.example.newsapp.ui.screen.Sources
import com.example.newsapp.ui.screen.TopNews
import com.example.newsapp.ui.viewmodel.MainViewModel
import com.example.newsapp.utils.BottomMenuScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scrollState: ScrollState,
    viewModel: MainViewModel
) {

    val articles = viewModel.newsResponse.collectAsState().value.articles
    //newsManager.newsResponse.value.articles
    val queryState = remember {
        mutableStateOf(viewModel.query.value)
    }

    NavHost(navController = navController, startDestination = BottomMenuScreen.TopNews.route) {

        composable(BottomMenuScreen.TopNews.route) {
            TopNews(
                navController = navController,
                queryFlow = viewModel.query,
                viewModel = viewModel
            )
        }

        composable(BottomMenuScreen.Categories.route) {
            viewModel.getArticlesByCategory("business")
            viewModel.onSelectedCategoryChanged("business")
            Categories(
                onFetchCategory = {
                    viewModel.onSelectedCategoryChanged(it)
                    viewModel.getArticlesByCategory(it)
                },
                viewModel = viewModel
            )
        }

        composable(BottomMenuScreen.Sources.route) {
            Sources(viewModel = viewModel)
        }

        composable(
            "Detail/{index}",
            arguments = listOf(navArgument("index") { type = NavType.Companion.IntType })
        ) { navBackStackEntry ->
            val index = navBackStackEntry.arguments?.getInt("index") ?: 1
            index.let {
                val newsData = articles?.get(it)
                DetailScreen(
                    navController = navController,
                    article = newsData!!,
                    scrollState = scrollState
                )
            }

        }

    }

}
