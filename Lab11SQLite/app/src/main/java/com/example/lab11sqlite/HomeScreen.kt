package com.example.lab11sqlite

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    var studentItemsList = remember { mutableStateListOf<Student>() }
    val contextForToast = LocalContext.current.applicationContext
    var textFieldID by remember { mutableStateOf("") }

    //check lifecycle state
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var dbHandler = DatabaseHelper.getInstance(contextForToast)
    dbHandler.writableDatabase


    LaunchedEffect(lifecycleState){
        when (lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                showAllData(studentItemsList, contextForToast)
            }
        }
    }

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Search:",
                fontSize = 20.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .width(230.dp)
                    .padding(10.dp),
                value = textFieldID,
                onValueChange = { textFieldID = it },
                label = { Text("Student ID") }
            )
            Button(onClick = {
                if(textFieldID.trim().isEmpty()){
                    showAllData(studentItemsList, contextForToast)
                } else {
                    studentItemsList.clear()

                    var dbHandler = DatabaseHelper.getInstance(contextForToast)
                    dbHandler.writableDatabase
                    studentItemsList.clear()

                    if(Objects.isNull(dbHandler!!.getStudents(textFieldID.trim()))){
                        Toast.makeText(contextForToast, "Student not found",
                            Toast.LENGTH_LONG)
                            .show()
                    } else {
                        studentItemsList.add(dbHandler.getStudents(textFieldID.trim())!!)
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
        Row (
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.weight(0.85f))
            {
                Text(
                    text = "Student Lists: ${studentItemsList.size}",
                    fontSize = 25.sp
                )
            }
            Button(onClick = {
                if(navController.currentBackStack.value.size >= 2){
                    navController.popBackStack()
                }
                navController.navigate(Screen.Insert.route)
            }) {
                Text("Add Student")
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ){
            var itemClick = Student("","","",0)
            itemsIndexed(
                items = studentItemsList,
            ){index, item ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .height(130.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    ),
                    shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                    onClick = {
                        Toast.makeText(
                            contextForToast,"Click on ${item.name}.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Row (
                        Modifier
                            .fillMaxWidth()
                            .height(Dp(130f))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "ID: ${item.id}\n" +
                                "Name: ${item.name}\n" +
                                "Gender: ${item.gender}\n" +
                                "Age: ${item.age}",
                            Modifier.weight(0.85f)
                        )
                        //TextButton
                        TextButton(onClick = {
                            itemClick = item
                            navController.currentBackStackEntry?.savedStateHandle?.set("data",
                                Student(item.id, item.name, item.gender, item.age)
                            )
                            navController.navigate(Screen.Edit.route)
                        }) {
                            Text("Edit/Delete")
                        }
                    }
                }
            }
        }

    }
}

fun showAllData(studentItemsList:MutableList<Student>, context: Context){
    var dbHandler = DatabaseHelper.getInstance(context)
    dbHandler.writableDatabase
    studentItemsList.clear()
    studentItemsList.addAll(dbHandler!!.getAllStudents())
}
