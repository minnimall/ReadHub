package com.example.lab11sqlite

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditScreen(navController: NavController) {
    val data =
        navController.previousBackStackEntry?.savedStateHandle?.get<Student>("data") ?: Student(
            "",
            "",
            "",
            0
        )

    val contextForToast = LocalContext.current
    var textFieldID by remember { mutableStateOf(data.id) }
    var textFieldName by remember { mutableStateOf(data.name) }
    var selectedGender by remember { mutableStateOf(data.gender) }
    var textFieldAge by remember { mutableStateOf(data.age.toString()) }

    var dbHandler = DatabaseHelper.getInstance(contextForToast)
    dbHandler.writableDatabase

    var deleteDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Edit a student",
            fontSize = 25.sp
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textFieldID,
            onValueChange = { textFieldID = it },
            label = { Text("Student ID") },
            enabled = false,
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textFieldName,
            onValueChange = { textFieldName = it },
            label = { Text("Student name") },
        )

        selectedGender = EditRadioGroupUsage(selectedGender)

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textFieldAge,
            onValueChange = { textFieldAge = it },
            label = { Text("Student age") },
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Button(modifier = Modifier
                .width(100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = {
                    deleteDialog = true
                }) {
                Text("Delete")
            }
            if (deleteDialog) {
                AlertDialog(
                    onDismissRequest = {
                        deleteDialog = false
                    },
                    title = { Text(text = "Warning") },
                    text = { Text("Do you want to delete a student $textFieldID") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                deleteDialog = false
                                val result = dbHandler.deleteStudent(textFieldID)
                                if (result > -1) {
                                    Toast.makeText(
                                        contextForToast,
                                        "Deleted successfully", Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        contextForToast,
                                        "Delete Failure", Toast.LENGTH_LONG
                                    ).show()
                                }
                                navController.navigate(Screen.Home.route)
                            }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                deleteDialog = false
                            }
                        ) {
                            Text("No")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(modifier = Modifier
                .width(100.dp),
                onClick = {
                    var result = dbHandler.updateStudent(
                        Student(
                            id = textFieldID,
                            name = textFieldName,
                            gender = selectedGender,
                            age = textFieldAge.toInt()
                        )
                    )
                    if (result > -1) {
                        Toast.makeText(
                            contextForToast,
                            "Updated successfully", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            contextForToast,
                            "Updated Failure", Toast.LENGTH_LONG
                        ).show()
                    }
                    if (navController.currentBackStack.value.size >= 2) {
                        navController.popBackStack()
                    }
                    navController.navigate(Screen.Home.route)
                }) {
                Text("Update")
            }
            Spacer(modifier = Modifier.width(18.dp))

            Button(modifier = Modifier
                .width(100.dp),
                onClick = {
                    if (navController.currentBackStack.value.size >= 2) {
                        navController.popBackStack()
                    }
                    navController.navigate(Screen.Home.route)
                }) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun EditRadioGroupUsage(s:String):String{
    val kinds = listOf("Male","Female","Other")
    val (selected, setSelected) = remember{ mutableStateOf(s) }
    Text(
        text = "Student Gender : $selected",
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 10.dp),
    )
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp)){
        RadioGroup(
            mItems = kinds,
            selected , setSelected
        )
    }
    return selected
}

@Composable
fun RadioGroup(
    mItems: List<String>,
    selected: String,
    onSelectionChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        mItems.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selected == item,
                    onClick = {
                        onSelectionChanged(item)
                    },
                    enabled = true,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Green
                    )
                )
                Text(text = item, modifier = Modifier.padding(start = 5.dp))
            }
        }
    }
}



