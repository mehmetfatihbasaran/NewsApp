package com.example.newsapp.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.screen.MainScreen
import com.example.newsapp.ui.viewmodel.MainViewModel

@Composable
fun NewsApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    MainScreen(navController = navController, scrollState = scrollState, viewModel = viewModel)
}