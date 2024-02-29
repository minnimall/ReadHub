package com.example.lab11sqlite

sealed class Screen (val route: String, val name: String){
    object Home: Screen(route = "home_screen", name = "Home")
    object Insert: Screen(route = "insert_screen", name = "Insert")
    object Edit: Screen(route = "edit_screen", name = "Edit")
}