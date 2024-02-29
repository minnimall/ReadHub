package com.example.readhub

import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route:String, val name: String, val icon: ImageVector) {
        object Login : Screen(route = "login_screen", name = "Login", icon= Icons.Default.Home)
        object Register : Screen(route = "register_screen", name = "Register",icon= Icons.Filled.Person)
        object Home: Screen(route="home_screen", name = "Home", icon=Icons.Default.Home)
        object AddBook: Screen(route="addbook_screen", name = "AddBook", icon=Icons.Default.LibraryAdd)
        object Profile: Screen(route="profile_screen", name = "Profile", icon=Icons.Default.Person)
        object BookShelf: Screen(route="bookshelf_screen", name = "BookShelf", icon=Icons.Default.LibraryBooks)
        object UserInterested: Screen(route="userinterested_screen", name = "UserInterested", icon=Icons.Default.Lightbulb)
        object MyScreen: Screen(route="myscreen_screen", name = "MyScreen", icon=Icons.Default.Accessibility)
        object Search: Screen(route = "search_screen", name = "SearchPage", icon=Icons.Default.Search)
        object Detail: Screen(route = "detail_screen", name = "BookDetail", icon=Icons.Default.Details)
        object ResetPasswordScreen: Screen(route="myscreen_screen", name = "MyScreen", icon=Icons.Default.Accessibility)

}
