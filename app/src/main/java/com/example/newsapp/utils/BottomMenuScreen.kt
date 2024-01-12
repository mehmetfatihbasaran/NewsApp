package com.example.newsapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Source
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(val route: String, val icon: ImageVector, val title: String) {

    //Todo 4: create object for each screen on the navigation
    data object TopNews :
        BottomMenuScreen("topNews", icon = Icons.Outlined.Home, title = "Top News")

    data object Categories :
        BottomMenuScreen("categories", icon = Icons.Outlined.Category, title = "Categories")

    data object Sources :
        BottomMenuScreen("sources", icon = Icons.Outlined.Source, title = "Sources")
}