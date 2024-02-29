package com.example.lab5navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab5navigation.ui.theme.Lab5NavigationTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposeAllNavigation()
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
    Lab5NavigationTheme {
        ComposeAllNavigation()
    }
}


sealed class ScreenRoute(val route: String){
    object First : ScreenRoute("first_screen")
    object Second : ScreenRoute("second_screen")
}

@Composable
fun ComposeAllNavigation(){
    val navController = rememberNavController()
    Column(modifier = Modifier
        .fillMaxSize()
        .border(
            width = 1.dp,
            color = Color.Black,
            shape = RoundedCornerShape(20.dp)
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        NavHost(navController = navController,
            startDestination = ScreenRoute.First.route){
            composable(ScreenRoute.First.route){
                Mypage1(navController)
            }
            composable(ScreenRoute.Second.route){
                Mypage2(navController)
            }
        }
    }
}


@Composable
fun IdNameAgeContent(id:String, onIdChange: (String)-> Unit,
                     name: String, onNameChange: (String) -> Unit,
                     age:String, onAgeChange: (String)-> Unit){
    Column(modifier = Modifier.padding(horizontal = 16.dp, ))
    {
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = id,
            onValueChange = onIdChange,
            label = { Text("Student ID")}
        )
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name")}

        )
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = age,
            onValueChange = onAgeChange,
            label = { Text("Age")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
            )
        )
    }
}

@Composable
fun CheckboxGroup(items: List<String>,
                  onSelectionChange: (List<String>) -> Unit
) {
    val selectedItems = remember { mutableStateListOf<String>() }
    Row{
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedItems.contains(item),
                    onCheckedChange = {
                        if (it) {
                            selectedItems.add(item)
                        } else {
                            selectedItems.remove(item)
                        }
                        onSelectionChange(selectedItems.toList())
                    }
                )
                Text(text = item, fontSize = 15.sp)
            }
        }
    }
}

@Composable
fun Mypage1(navHostController: NavHostController){
    var id by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp),
            text = "Page 1",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
        )
        Text(
            modifier = Modifier.padding(5.dp),
            text = "Enter Student Information",
            fontSize = 20.sp
        )

        //Call IdNameAgeContent function
        IdNameAgeContent (id = id, onIdChange = { id=it },
            name = name, onNameChange = { name = it },
            age = age, onAgeChange = { age = it})

        val hobbyList = listOf("Reading", "Painting", "Cocking")
        var selectedOut by remember { mutableStateOf("") }
        val selectedItems by remember { mutableStateOf(mutableListOf<String>()) }

        Text(
            modifier = Modifier.padding (horizontal = 16.dp, vertical = 5.dp),
            text = "Select your hobby:"
        )
        Row {
            CheckboxGroup(items = hobbyList) { newSelectedItems ->
                selectedItems.clear()
                selectedItems.addAll(newSelectedItems)
                Log.d("CheckboxGroup", "Selected items: $selectedItems")
                selectedOut = selectedItems.toString()
            }
        }
        Spacer (modifier = Modifier.height(height = 8.dp))

        Button(
            onClick= {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "data",
                    Student (id, name, age.toInt(), selectedItems)
                )
                navHostController.navigate(ScreenRoute.Second.route)
            }
        ) {
            Text(text="Send Information")
        }

        //Open Youtube
        Button(
            onClick = {
                val packageName = "com.google.android.youtube"
                startActivitySafe(context,packageName = packageName)
            }
        ){
            Text(text = "Open Youtube")
        }

        //Open Tiktok
        Button(
            onClick = {
                val packageName = "com.zhiliaoapp.musically"
                startActivitySafe(context,packageName = packageName)
            }
        ){
            Text(text = "Open Tiktok")
        }
    }
}

fun startActivitySafe(context: Context?, packageName: String){
    if(context == null || packageName == null){
        Log.e("startActivitySafely","Context or intent is null!")
        return
    }

    try{
        val intent = Intent(Intent.ACTION_MAIN)
        intent.`package`=packageName
        context.startActivity(intent)
    }catch(e: Exception){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
        ContextCompat.startActivity(context, i, null)
        Log.e("startActivitySafely", "Error starting activity", e)
    }
}


@Composable
fun Mypage2(navHostController: NavHostController){
    val data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Student>("data")?:
    Student("","",0, listOf())

    var lastIndex = data.hobby.size-1
    var hobbySelect=""

    data.hobby.forEachIndexed{index, item ->
        hobbySelect += if (index == lastIndex) item else "$item, "
    }
    IconButton(modifier = Modifier.size(100.dp),
        onClick = {
            navHostController.navigateUp() }
    )
    {
        Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Magenta)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.Start){
        Text(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)

                )
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            text = "Page 2",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
        )

        Text(modifier = Modifier.padding(16.dp),
            text = "Student ID: ${data.id} \n\n" +
                    "Student Name: ${data.name} \n\n" +
                    "Student Age: ${data.age}"+ "\n\n" +
                    "Hobby: $hobbySelect\n",
        )
        Button(modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navHostController.navigate(ScreenRoute.First.route){
                    launchSingleTop = true
                    popUpTo("first"){
                        inclusive = false }
                }
            }){
            Text(text = "Go to page1")
        }
    }
}


