package com.example.assignment6

import android.content.Context
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.navigation.compose.rememberNavController
import com.example.assignment6.ui.theme.Assignment6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment6Theme {
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment6Theme {
        MyScaffoldLayout()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(contextForToast: Context, navController: NavHostController){
    var expanded by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        title = {
            Text(text = "My Application") },
        actions = {
            // icon friend1
            IconButton(
                onClick = {
                    navController.navigate(Screen.Friend1.route)
                }){
                Icon(
                    painter = painterResource(id = R.drawable.meee),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Friend1"
                )
            }

            // icon friend2
            IconButton(
                onClick = {
                    navController.navigate(Screen.Friend2.route)
                }){
                Icon(
                    painter = painterResource(id = R.drawable.meee),
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Friend2"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Green.copy(alpha = 0.3f)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(navController: NavHostController, contextForToast: Context){
    val navigationItems = listOf(
        Screen.Home,
        Screen.Friend1,
        Screen.Friend2,
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
fun MyScaffoldLayout() {
    val contextForToast = LocalContext.current.applicationContext
    val navController = rememberNavController()
    Scaffold(
        topBar = { MyTopAppBar(contextForToast = contextForToast, navController = navController) },
        bottomBar = { MyBottomBar(navController, contextForToast) },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
        NavGraph(navController = navController)
    }
}