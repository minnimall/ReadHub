package com.example.readhub

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController){
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(contextForToast)
    val userId = sharedPreferences.userId ?: ""
    var value by remember { mutableStateOf("") }
    var bookItemsList = remember { mutableStateListOf<Book>() }

    val createClient = UserApi.create()
    val initialUser = user(0,"","","")
    var userItems by remember { mutableStateOf(initialUser) }

//    sharedPreferences = SharedPreferencesManager(context = contextForToast)

    println("now logged in: "+ userId)

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState){
        when (lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                createClient.searchUser(userId)
                    .enqueue(object : Callback<user> {
                        override fun onResponse(call: Call<user>, response: Response<user>) {
                            if (response.isSuccessful){
                                userItems = user(
                                    response.body()!!.user_id, response.body()!!.user_name,
                                    response.body()!!.email, response.body()!!.password)
                            }else{
                                Toast.makeText(contextForToast, "User ID Not Found",
                                    Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<user>, t: Throwable) {
                            Toast.makeText(contextForToast, "Error onFailure "+ t.message,
                                Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }
        when (lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                showAllDataB(bookItemsList, contextForToast)
            }
        }
    }

    if (sharedPreferences.isLoggedIn) {
        Scaffold(
            topBar = { MyTopAppBar(navController, contextForToast) },
            bottomBar = { MyBottomBar(navController, contextForToast) },
            floatingActionButtonPosition = FabPosition.End,
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "จัดการบัญชีผู้ใช้",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))

                TextField(
                    value = value,
                    onValueChange = { newValue ->
                        value = newValue
                    },
                    enabled = false,
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    label = {
                        Text("username: " + userId)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = value,
                    onValueChange = { newValue ->
                        value = newValue
                    },
                    enabled = false,
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    },
                    label = {
                        Text("email: " + "${userItems.email}" )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "หนังสือของฉัน",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 190.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 5.dp),
                    contentPadding = PaddingValues(all = 10.dp)
                ){
                    var itemClick = Book(0,"","","",0,"","","")
                    itemsIndexed(
                        items = bookItemsList,
                    ){index, item ->
                        Card(
                            modifier = Modifier
                                .width(190.dp)
                                .wrapContentHeight()
                                .padding(horizontal = 5.dp, vertical = 5.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                            onClick = {
                                createClient.updateNumOfRead(item.book_id).enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        if (response.isSuccessful) {
//                                            Toast.makeText(contextForToast, "Book view successfully", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(contextForToast, "Failed to view book", Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Toast.makeText(contextForToast, "Error: " + t.message, Toast.LENGTH_SHORT).show()
                                    }
                                })
//                                Toast.makeText(
//                                    contextForToast,"Click on ${item.book_name}.",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                                itemClick = item
                                navController.currentBackStackEntry?.savedStateHandle?.set("data",
                                    Book(item.book_id,item.book_img,item.book_name, item.description,item.num_of_read, item.writer_name, item.btype_id,item.pub_id)
                                )
                                navController.navigate(Screen.Detail.route)
                            }
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Column (
                                Modifier
                                    .width(190.dp)
                                    .wrapContentHeight()
                                    .padding(5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(text = "Name: ${item.book_name}\n"+
                                        "Views: ${item.num_of_read}\n",
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    else {
        navController.navigate(Screen.Login.route)
    }
}

fun showAllDataB(bookItemsList: MutableList<Book>, context: Context){
    val createClient = UserApi.create()
    createClient.allBook()
        .enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>,
                                    response: Response<List<Book>>
            ) {
                bookItemsList.clear()
                response.body()?.forEach {
                    bookItemsList.add(Book(it.book_id,it.book_img,it.book_name,it.description,it.num_of_read,it.writer_name,it.btype_id,it.pub_id))
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Toast.makeText(context,"Error onFailure" + t.message,
                    Toast.LENGTH_LONG).show()
            }
        })
}