package com.example.lab06

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen (val route: String, val name: String, val icon: ImageVector) {
    object Home: Screen(route = "home_screen", name = "Home", icon = Icons.Default.Home)
    object Profile: Screen(route = "profile_screen", name = "Profile", icon = Icons.Default.Person)
    object Favorite: Screen(route = "favorite_screen", name = "Favorite", icon = Icons.Default.Favorite)
}