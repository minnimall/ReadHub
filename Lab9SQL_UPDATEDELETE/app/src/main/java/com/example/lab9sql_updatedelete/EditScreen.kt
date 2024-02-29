package com.example.lab9sql_updatedelete

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EditScreen(navController: NavController){
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<Student>("data") ?:
    Student("","","",0)

    var textFieldID by remember { mutableStateOf(data.std_id) }
    var textFieldName by remember { mutableStateOf(data.std_name) }
    var genderValue by remember { mutableStateOf(data.std_gender) }
    var textFieldAge by remember { mutableStateOf(data.std_age.toString()) }
    val contextForToast = LocalContext.current
    val createClient = StudentAPI.create()

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

        genderValue = EditRadioGroupUsage(genderValue)

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textFieldAge,
            onValueChange = { textFieldAge = it},
            label = { Text("Student age") },
        )

        Row (
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
            if(deleteDialog){
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
                                createClient.deleteStudent(textFieldID
                        ).enqueue(object: Callback<Student>{
                            override fun onResponse(
                                call: Call<Student>, response: Response<Student>) {
                                    if(response.isSuccessful){
                                        Toast.makeText( contextForToast,"Successfully Deleted",
                                            Toast.LENGTH_LONG).show()
                                    } else {
                                        Toast.makeText( contextForToast,"Deleted Failure",
                                            Toast.LENGTH_LONG).show()
                                    }
                                }

                            override fun onFailure(call: Call<Student>, t: Throwable) {
                                 Toast.makeText( contextForToast,"Error Failure" + t.message,
                                        Toast.LENGTH_LONG).show()
                                 }
                            })
                                if (navController.currentBackStack.value.size >= 2) {
                                    navController.popBackStack()
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
                    createClient.updateStudent(
                        textFieldID,textFieldName,genderValue,textFieldAge.toInt()
            ).enqueue(object : Callback<Student>{
                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    if(response.isSuccessful){
                        Toast.makeText( contextForToast,"Successfully Updated",
                             Toast.LENGTH_LONG).show()
                    } else {
                         Toast.makeText( contextForToast,"Updated Failure",
                             Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Student>, t: Throwable) {
                      Toast.makeText( contextForToast,"Error onFailure" + t.message,
                           Toast.LENGTH_LONG).show()
                            }
            })
                    if(navController.currentBackStack.value.size >= 2){
                        navController.popBackStack()
                    }
                    navController.navigate(Screen.Home.route)
                }){
                Text("Update")
            }
            Spacer(modifier = Modifier.width(18.dp))

            Button(modifier = Modifier
                .width(100.dp),
                onClick = {
                    if(navController.currentBackStack.value.size >= 2){
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
    var (selected, setSelected) = remember { mutableStateOf(s) }
    Text(
        text = "Student Gender :",
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 10.dp),
    )
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp)){
        EditeRadioGroup(
            mItems = kinds,
            selected, setSelected
        )
    }
    return selected
}

@Composable
fun EditeRadioGroup(
    mItems: List<String>,
    selected: String,
    setSelected: (selected: String) -> Unit,
){
    Row ( modifier = Modifier.fillMaxWidth(),
    ){
        mItems.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = selected == item,
                    onClick = {
                        setSelected(item)
                    },
                    enabled = true,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Green)
                )
                Text(text = item, modifier = Modifier.padding(start = 5.dp))
            }
        }
    }
}