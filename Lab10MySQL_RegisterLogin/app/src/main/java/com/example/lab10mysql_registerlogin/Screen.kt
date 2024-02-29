package com.example.lab10mysql_registerlogin

sealed class Screen (val route: String, val name: String){
    object Login: Screen(route = "login_screen", name = "Login")
    object Register: Screen(route = "register_screen", name = "Register")
    object Profile: Screen(route = "profile_screen", name = "Profile")
    object List: Screen(route = "list_screen", name = "List")
}