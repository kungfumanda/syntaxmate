package com.example.syntaxmate.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Search : BottomNavItem(Routes.Search.route, Icons.Default.Search, "Search") // Posteriormente serão icons
    object Favorites : BottomNavItem(Routes.Favorites.route, Icons.Default.Favorite, "Favorites")
    object History : BottomNavItem(Routes.History.route, Icons.Default.Refresh, "History")
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Search,
        BottomNavItem.Favorites,
        BottomNavItem.History
    )

    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}