package com.example.syntaxmate.ui.navigation

sealed class Routes(val route: String) {
    object Favorites : Routes("favorites")
    object Search : Routes("search")
    object History : Routes("history")
}