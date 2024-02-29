package com.example.lab8mysql_queryinsert

sealed class Screen (val route: String, val name: String){
    object Home: Screen(route = "home_screen", name = "Home")
    object Insert: Screen(route = "insert_screen", name = "Insert")
}