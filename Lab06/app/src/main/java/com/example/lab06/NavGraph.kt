package com.example.lab06

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route
        ){
            HomeScreen()
        }

        composable(
            route = Screen.Profile.route
        ){
            ProfileScreen()
        }
        composable(
            route = Screen.Favorite.route
        ){
            FavoriteScreen()
        }
    }
}