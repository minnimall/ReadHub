package com.example.readhub

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BookDetail(navController: NavHostController){
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<Book>("data") ?:
    Book(0,"","","",0,"","","")

    var textFieldName by remember { mutableStateOf(data.book_name) }
    var textFieldDescription by remember { mutableStateOf(data.description) }

    val contextForToast = LocalContext.current.applicationContext
    var bookItemsList = remember { mutableStateListOf<Book>() }

    val userId = SharedPreferencesManager(contextForToast).userId

    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)
    if (sharedPreferences.isLoggedIn) {
        Scaffold(
            topBar = { MyTopAppBar(navController, contextForToast) },
            bottomBar = { MyBottomBar(navController, contextForToast) },
            floatingActionButtonPosition = FabPosition.End,
        ) { paddingValues ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(57.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                IconButton(
                    modifier = Modifier
                        .size(80.dp),
                    onClick = {
                        navController.navigate(Screen.Home.route)
                    }
                )
                {
                    Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Blue)
                }
            }
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
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterVertically),

                            text = data.book_name,
                            fontSize = 20.sp,
//                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = "ชื่อผู้แต่ง ",
                            fontSize = 15.sp,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier
                        .size(230.dp)
                        .align(Alignment.CenterHorizontally)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(30.dp),

                            )
                    ){
                        Image(
                            painter = rememberAsyncImagePainter(data.book_img),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button( modifier = Modifier
                            .width(160.dp),
                            onClick = {

                            }
                        ) {
                            Text(text = "อ่านหนังสือ")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button( modifier = Modifier
                            .width(160.dp),
                            onClick = {
                                val createClient = UserApi.create()
                                createClient.addToBookshelf(data.book_id, userId).enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(contextForToast, "Added to favorites", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(contextForToast, "Failed to add to favorites", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Toast.makeText(contextForToast, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                    }
                                })
                            }
                        ) {
                            Text(text = "เพิ่มเข้าชั้นหนังสือ")
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 37.dp)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Text(
                            text = "รายละเอียดเพิ่มเต็ม",
                            fontSize = 15.sp,
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 37.dp)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Text(text = data.description,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
        }
    } else {
        navController.navigate(Screen.Login.route)
    }
}


