package com.example.assignment8

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun MyRadioGroup(
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

@Composable
fun RadioUse(onGenderSelected: (String) -> Unit) {
    val kinds = listOf("Male", "Female", "Other")
    val (selected, setSelected) = remember { mutableStateOf("") }

    Column {
        Text(
            text = "Employee Gender : $selected",
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            MyRadioGroup(
                mItems = kinds,
                selected = selected,
                onSelectionChanged = {
                    setSelected(it)
                    onGenderSelected(it)
                }
            )
        }
    }
}

@Composable
fun InsertScreen(navController: NavController){

    val contextForToast = LocalContext.current
    val createClient = EmployeeAPI.create()
    var textFieldName by remember { mutableStateOf("") }
    var genderSelected by rememberSaveable { mutableStateOf("") }
    var textFieldEmail by remember { mutableStateOf("") }
    var textFieldSalary by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Insert New Employee",
            fontSize = 25.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textFieldName,
            onValueChange = { textFieldName = it },
            label = { Text("Name")}
        )

        RadioUse(onGenderSelected = { genderSelected = it })

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textFieldEmail,
            onValueChange = { textFieldEmail = it },
            label = { Text("E-mail")}
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textFieldSalary,
            onValueChange = { textFieldSalary = it },
            label = { Text("Salary")}
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
                .width(130.dp),
                onClick = {
                    createClient.insertEmp(
                        textFieldName,genderSelected,textFieldEmail,textFieldSalary.toInt()
                    ).enqueue(object : Callback<Employee>{
                        override fun onResponse(call: Call<Employee>,
                                                response: Response<Employee>) {
                            if (response.isSuccessful){
                                Toast.makeText(contextForToast,"Successfully Inserted",
                                    Toast.LENGTH_SHORT).show()
                            } else{
                                Toast.makeText(contextForToast,"Inserted Failed",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Employee>, t: Throwable) {
                            Toast.makeText(contextForToast,"Error onFailure" +
                                    t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                    navController.navigateUp()
                }){
                Text("Save")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(modifier = Modifier
                .width(130.dp),
                onClick = {
                    textFieldName=""
                    textFieldEmail=""
                    textFieldSalary=""
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