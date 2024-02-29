package com.example.assignment6

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen()
        }

        composable(
            route = Screen.Friend1.route
        ) {
            Friend1Screen()
        }
        composable(
            route = Screen.Friend2.route
        ) {
            Friend2Screen()
        }
    }
}