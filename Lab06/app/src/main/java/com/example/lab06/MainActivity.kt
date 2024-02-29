package com.example.lab06

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lab06.ui.theme.Lab06Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab06Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScaffoldLayout()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab06Theme {
        MyScaffoldLayout()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(contextForToast: Context){
    var expanded by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
            Text(text = "My Application") },
        actions = {
            // Notifications icon แจ้งเตือน
            IconButton(
                onClick = {
                    Toast.makeText(contextForToast, "Notifications", Toast.LENGTH_SHORT)
                        .show()
                }){
                Icon(imageVector = Icons.Outlined.NotificationsNone, contentDescription = "Notifications")
            }

            //home icon
            IconButton(
                onClick = {
                    Toast.makeText(contextForToast, "Home", Toast.LENGTH_SHORT)
                        .show()
                }){
                Icon(imageVector = Icons.Outlined.Home, contentDescription = "Home")
            }

            //vertical 3 dots icon จุด 3 จุด
            IconButton(
                onClick = { expanded = true }
            ){
                Icon(Icons.Default.MoreVert, contentDescription = "Open Menu")
            }
            //Dropdown Menu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ){
                //menu items
                DropdownMenuItem(
                    text = {Text("Settings")},
                    onClick = {
                        Toast.makeText(contextForToast, "Settings" , Toast.LENGTH_SHORT)
                            .show()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.Settings,
                            contentDescription = null
                        )
                    }

                )
                DropdownMenuItem(
                    text = {Text("Logout")},
                    onClick = {
                        Toast.makeText(contextForToast, "Logout" , Toast.LENGTH_SHORT)
                            .show()
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(Icons.Outlined.Logout,
                            contentDescription = null
                        )
                    }

                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Green.copy(alpha = 0.3f)
        )
    )
}

@Composable
fun MyBottomBar(navController: NavHostController, contextForToast: Context){
    val navigationItems = listOf(
        Screen.Home,
        Screen.Profile,
        Screen.Favorite,
    )
    var selectedScreen by remember{
        mutableIntStateOf(0)
    }
    NavigationBar{
        navigationItems.forEachIndexed{ index, screen ->
            NavigationBarItem(
                icon = {Icon(imageVector = screen.icon, contentDescription = null)},
                label = { Text(text = screen.name) },
                selected = (selectedScreen == index),
                onClick = {
                    if (navController.currentBackStack.value.size >= 2){
                        navController.popBackStack()
                    }
                    selectedScreen = index
                    navController.navigate(screen.route)
                    Toast.makeText(contextForToast, screen.name, Toast.LENGTH_SHORT)
                        .show()
                }
            )
        }
    }
}

@Composable
fun MyScaffoldLayout(){
    val contextForToast = LocalContext.current.applicationContext
    val navController = rememberNavController()
    Scaffold(
        topBar = { MyTopAppBar(contextForToast = contextForToast)},
        bottomBar = { MyBottomBar(navController, contextForToast)},
        floatingActionButtonPosition = FabPosition.End,
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Screen area")
        }
        NavGraph(navController = navController)
    }
}
