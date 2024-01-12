package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.newsapp.components.BottomMenu
import com.example.newsapp.navigation.Navigation
import com.example.newsapp.ui.viewmodel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    scrollState: ScrollState,
    viewModel: MainViewModel
) {

    Scaffold(
        bottomBar = {
            BottomMenu(navController = navController)
        }
    ) {
        Navigation(navController = navController, scrollState = scrollState, viewModel = viewModel)
    }

}