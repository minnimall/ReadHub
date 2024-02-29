package com.example.readhub

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
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
fun SearchPage(navController: NavHostController) {
    val createClient = UserApi.create()
    val contextForToast = LocalContext.current.applicationContext
    var bookItemsList = remember { mutableStateListOf<Book>() }
    var textFieldName by remember { mutableStateOf("") }

    //check lifecycle state
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
//
//    LaunchedEffect(lifecycleState){
//        when (lifecycleState){
//            Lifecycle.State.DESTROYED -> {}
//            Lifecycle.State.INITIALIZED -> {}
//            Lifecycle.State.CREATED -> {}
//            Lifecycle.State.STARTED -> {}
//            Lifecycle.State.RESUMED -> {
//                showAllDataBook(bookItemsList, contextForToast)
//            }
//        }
//    }


    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)

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
                Column{
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Text(
                            text = "Search:",
                            fontSize = 20.sp
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .width(230.dp)
                                .padding(10.dp),
                            value = textFieldName,
                            onValueChange = { textFieldName = it },
                            label = { Text("Book Name") },
                            shape = RoundedCornerShape(30.dp)
                        )

                        Button(
                            onClick = {
                                if(textFieldName.trim().isEmpty()){
                                    showAllData(bookItemsList, contextForToast)
                                }else{
                                    bookItemsList.clear()
                                    createClient.searchBook(textFieldName)
                                        .enqueue(object : Callback<List<Book>>{
                                            override fun onResponse(
                                                call: Call<List<Book>>,
                                                response: Response<List<Book>>
                                            ) {
                                                if (response.isSuccessful) {
                                                    val books: List<Book>? = response.body()
                                                    if (books != null) {
                                                        bookItemsList.addAll(books)
                                                        Toast.makeText(contextForToast, "Books Found", Toast.LENGTH_LONG)
                                                            .show()
                                                    } else {
                                                        Toast.makeText(contextForToast, "No Books Found", Toast.LENGTH_LONG)
                                                            .show()
                                                    }
                                                } else {
                                                    Toast.makeText(contextForToast, "Failed to fetch books", Toast.LENGTH_LONG)
                                                        .show()
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<List<Book>>,
                                                t: Throwable
                                            ) {
                                                Toast.makeText(contextForToast, "" + t.message, Toast.LENGTH_LONG)
                                                    .show()
                                            }
                                        })
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    )
                    {
                        Column(modifier = Modifier.weight(0.85f))
                        {
                            Text(
                                text = "ผลการค้นหา ${bookItemsList.size} รายการ",
                                fontSize = 18.sp
                            )
                        }
                    }
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
//                                                Toast.makeText(contextForToast, "Book view successfully", Toast.LENGTH_SHORT).show()
                                            } else {
                                                Toast.makeText(contextForToast, "Failed to view book", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                        override fun onFailure(call: Call<Void>, t: Throwable) {
                                            Toast.makeText(contextForToast, "Error: " + t.message, Toast.LENGTH_SHORT).show()
                                        }
                                    })
//                                    Toast.makeText(
//                                        contextForToast,"Click on ${item.book_name}.",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
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
    } else {
        navController.navigate(Screen.Login.route)
    }

}

//fun showAllDataBook(bookItemsList: MutableList<Book>, context: Context){
//    val createClient = UserApi.create()
//    createClient.allBook()
//        .enqueue(object : Callback<List<Book>> {
//            override fun onResponse(call: Call<List<Book>>,
//                                    response: Response<List<Book>>
//            ) {
//                bookItemsList.clear()
//                response.body()?.forEach {
//                    bookItemsList.add(Book(it.book_img,it.book_name,it.description,it.writer_name,it.btype_id,it.pub_id))
//                }
//            }
//
//            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
//                Toast.makeText(context,"Error onFailure" + t.message,
//                    Toast.LENGTH_LONG).show()
//            }
//        })
//}