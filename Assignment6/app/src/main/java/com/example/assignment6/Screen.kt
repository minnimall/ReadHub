package com.example.assignment6

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.ui.graphics.vector.ImageVector

open class Screen (val route: String, val name: String, val icon: ImageVector){
    object Home: Screen(route = "home_screen", name = "Home", icon = Icons.Default.Home)
    object Friend1: Screen(route = "friend1_screen", name = "Friend1", icon = Icons.Default.AccountBox)
    object Friend2: Screen(route = "friend2_screen", name = "Friend2", icon = Icons.Default.PersonPin)
}