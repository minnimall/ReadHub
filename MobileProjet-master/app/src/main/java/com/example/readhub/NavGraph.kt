package com.example.readhub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(navController)
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterScreen(navController)
        }

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.AddBook.route
        ) {
            AddBookScreen(navController)
        }

        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen(navController)
        }

        composable(
            route = Screen.BookShelf.route
        ) {
            BookShelfScreen(navController)
        }

        composable(
            route = Screen.UserInterested.route
        ) {
            UserInterested(navController)
        }

        composable(
            route = Screen.Search.route
        ) {
            SearchPage(navController)
        }

        composable(
            route = Screen.Detail.route
        ) {
            BookDetail(navController)
        }

        composable(
            route = Screen.ResetPasswordScreen.route
        ) {
            RepasswordScreen(navController)
        }
    }
}