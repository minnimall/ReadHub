package com.example.readhub

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Callback
import java.io.File
import java.io.FileOutputStream


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddBookScreen(navController: NavHostController){
    val contextForToast = LocalContext.current.applicationContext
    val createClient = UserApi.create()
    var textFieldTitle by remember { mutableStateOf("") }
    var textFieldDesc by remember { mutableStateOf("") }

    var textFieldWriter by remember { mutableStateOf("") }
    var textFieldPubl by remember { mutableStateOf("") }

    var selectedItems by remember { mutableStateOf(mutableListOf<String>()) }
    var bookUri by remember { mutableStateOf<Uri?>(null) }

    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)

    var category by rememberSaveable { mutableStateOf("") }

    val documentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                bookUri = it
                println("$uri")
                if (isPdfFile(uri)) {
                    println("ไฟล์ PDF ถูกเพิ่มแล้ว: $uri")
                } else {
                    println("โปรดเลือกไฟล์ PDF")
                }
            }
        }
    )

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
                Text(text = "ลงหนังสือ",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = textFieldTitle,
                    onValueChange = {textFieldTitle = it},
                    label = { Text(text = "Enter Title")}
                )
                OutlinedTextField(
                    value = textFieldDesc,
                    onValueChange = { textFieldDesc = it },
                    label = { Text(text = "Enter Book Description") },
                    modifier = Modifier
                        .height(100.dp)
                )

                OutlinedTextField(
                    value = textFieldWriter,
                    onValueChange = {textFieldWriter = it},
                    label = { Text(text = "Enter Writer Name")}
                )
                OutlinedTextField(
                    value = textFieldPubl,
                    onValueChange = {textFieldPubl = it},
                    label = { Text(text = "Enter Publishing")}
                )
                menuDropdown(menu = category, onMenuChange = { category = it })
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(90.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                        )
                ){
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    documentLauncher.launch(arrayOf("application/pdf"))
                                }
                            ) {
                                Text(text = "Add File")
                            }
                        }

                        if (bookUri != null) {
                            val fileName = getFileName(contextForToast, bookUri!!)
                            Text(
                                text = "$fileName",
                            )
                        }else{
                            Text(
                                text = "ยังไม่เพิ่มไฟล์",
                            )
                        }
                    }

                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){Button(
                    modifier = Modifier.width(130.dp),
                    onClick = {
                        val inputStream = contextForToast.contentResolver.openInputStream(bookUri!!)
                            ?: throw Exception("Failed to open input stream")
                        val bookFile = File.createTempFile("book", ".pdf")
                        val outputStream = FileOutputStream(bookFile)
                        inputStream.copyTo(outputStream)
                        inputStream.close()
                        outputStream.close()

                        val requestBody = bookFile.asRequestBody("book/pdf".toMediaTypeOrNull())

                        val bookPart = MultipartBody.Part.createFormData(
                            "bookPart",
                            bookFile.name, requestBody
                        )

                        val titleRequestBody =
                            textFieldTitle.toRequestBody("text/plain".toMediaTypeOrNull())
                        val DescRequestBody =
                            textFieldDesc.toRequestBody("text/plain".toMediaTypeOrNull())
                        val WriterRequestBody =
                            textFieldWriter.toRequestBody("text/plain".toMediaTypeOrNull())
                        val PublRequestBody =
                            textFieldPubl.toRequestBody("text/plain".toMediaTypeOrNull())
                        val CatagRequestBody =
                            category.toRequestBody("text/plain".toMediaTypeOrNull())

                        createClient.uploadFile(
                            bookPart, titleRequestBody, DescRequestBody,WriterRequestBody,PublRequestBody,CatagRequestBody
                        ).enqueue(object : Callback<Book> {
                            override fun onResponse(
                                call: retrofit2.Call<Book>,
                                response: retrofit2.Response<Book>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        contextForToast, "Successfully Insert",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    println(bookPart.toString()+titleRequestBody.toString()+DescRequestBody.toString()+WriterRequestBody.toString()+PublRequestBody.toString()+CatagRequestBody.toString())
                                    Toast.makeText(
                                        contextForToast, "Error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(call: retrofit2.Call<Book>, t: Throwable) {
                                Toast.makeText(
                                    contextForToast, "Error onFailor" + t.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                        if (navController.currentBackStack.value.size >= 2) {
                            navController.popBackStack()
                        }
                        navController.navigate(Screen.Home.route)
                    }
                ) {
                    Text(text = "Save")
                }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        modifier = Modifier.width(130.dp),
                        onClick = {
                            textFieldTitle =""
                            textFieldWriter = ""
                            textFieldPubl = ""
                            if(navController.currentBackStack.value.size >= 2){
                                navController.popBackStack()
                            }
                            navController.navigate(Screen.Home.route)
                        }) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    } else {
        navController.navigate(Screen.Login.route)
    }
}


fun isPdfFile(uri: Uri): Boolean {
    val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
    return mimeType == "application/pdf"
}

fun getFileName(context: Context, uri: Uri): String? {
    var fileName: String? = null

    if ("content".equals(uri.scheme, ignoreCase = true)) {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val displayName = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                fileName = cursor.getString(displayName)
            }
        }
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        fileName = File(uri.path!!).name
    }

    return fileName
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun menuDropdown(
    menu: String,
    onMenuChange: (String) -> Unit,
): String {
    val keyboardController = LocalSoftwareKeyboardController.current
    val categoryList = listOf(
        "Love Novel", "Boy Love", "Girl Love", "Fantasy", "Horror",
        "Turn back time", "Historical"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectCategory by remember { mutableStateOf(menu) } // ให้ selectCategory เริ่มต้นด้วยค่า menu

    ExposedDropdownMenuBox(
        modifier = Modifier
            .clickable { keyboardController?.hide() },
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .width(340.dp)
                .menuAnchor()
                .clickable { keyboardController?.hide() },
            textStyle = TextStyle.Default.copy(fontSize = 12.sp),
            readOnly = true,
            value = selectCategory,
            onValueChange = {},
            label = {
                Text("Book Category:")
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            categoryList.forEach { selectOption ->
                DropdownMenuItem(
                    text = { Text(selectOption) },
                    onClick = {
                        selectCategory = selectOption
                        expanded = false
                        onMenuChange(selectOption) // เรียกใช้งาน callback เพื่ออัปเดตค่า
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
    return selectCategory
}


