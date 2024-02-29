package com.example.readhub

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.readhub.ui.theme.ReadHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadHubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScreen()
                }
            }
        }
    }
}

@Composable
fun MyScreen(){
    val navController = rememberNavController()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    }
    NavGraph(navController=navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(navController: NavHostController, contextForToast: Context) {
    var expanded by remember { mutableStateOf(false) }

    var checkedState by remember { mutableStateOf(false) }
    var logoutDialog by remember { mutableStateOf(false) }

    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)
    CenterAlignedTopAppBar(
        title = {
            Text(text = "ReadHub",
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = {
                logoutDialog=true
            }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF0088CC)
        )
    )
    if (logoutDialog) {
        AlertDialog(
            onDismissRequest = { logoutDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        logoutDialog = false
                        sharedPreferences.isLoggedIn = false
                        sharedPreferences.ControlShow = false
                        if (checkedState) {
                            sharedPreferences.clearUserLogin()
                            Toast.makeText(contextForToast, "Clear user login.", Toast.LENGTH_SHORT).show()
                        } else {
                            sharedPreferences.clearUserAll()
                            Toast.makeText(contextForToast, "Clear user login and e-mail.", Toast.LENGTH_SHORT).show()
                        }
                        if (navController.currentBackStack.value.size >= 2) {
                            navController.popBackStack()
                            println(sharedPreferences.isLoggedIn)
                        }
                        navController.navigate(Screen.Login.route)
                    }
                ) {
                    Text(text = "Yes")
                }
            }
            ,
            dismissButton = {
                TextButton(
                    onClick = {
                        logoutDialog = false
                        Toast.makeText(contextForToast, "Click on no",Toast.LENGTH_SHORT).show()
                    }
                ){ Text(text = "No")}
            },
            title = { Text(text = "Logout")},
            text = { Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Are you sure you want to log out?")
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = {
                                isChecked->
                            checkedState = isChecked
                        })
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Remember my username")
                }
            }
            }
        )
    }
}

@Composable
fun MyBottomBar(navController: NavHostController, contextForToast: Context){
    val navigationItems = listOf(
        Screen.Home,
        Screen.BookShelf,
        Screen.AddBook,
        Screen.Profile
    )
    var selectedScreen by remember {
        mutableIntStateOf(0)
    }
    NavigationBar {
        navigationItems.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {Icon(imageVector = screen.icon, contentDescription = null)},
                label = {Text(text = screen.name)},
                selected = (selectedScreen == index),
                onClick = {
                    if (navController.currentBackStack.value.size >= 2){
                        navController.popBackStack()
                    }
                    selectedScreen = index
                    navController.navigate(screen.route)
                    Toast.makeText(contextForToast, screen.name, Toast.LENGTH_SHORT).show()
                }
            )
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
    ReadHubTheme {
        Greeting("Android")
    }
}