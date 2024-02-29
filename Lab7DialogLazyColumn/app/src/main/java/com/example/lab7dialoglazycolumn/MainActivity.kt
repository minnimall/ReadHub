package com.example.lab7dialoglazycolumn

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab7dialoglazycolumn.ui.theme.Lab7DialogLazyColumnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab7DialogLazyColumnTheme {
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab7DialogLazyColumnTheme {
        MyScreen()
    }
}

@Composable
fun CheckboxGroup(items: List<String>,
                  onSelectionChange: (List<String>) -> Unit) {
    val selectedItems = remember { mutableStateListOf<String>() }
    Column(
        verticalArrangement = Arrangement.Top
    ){
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(){
    var studentItemList = remember { mutableStateListOf<Student>() }
    val contextForToast = LocalContext.current.applicationContext

    // Alert Dialog : add student
    var showDialog by remember { mutableStateOf(false) }
    var textFieldID by remember { mutableStateOf("") }
    var textFieldName by remember { mutableStateOf("") }
    var textFieldAge by remember { mutableStateOf("") }
    //var selectedHobby by remember { mutableStateOf(emptyList<String>()) }
    // seleted hobby
    val hobbyList = listOf("Reading", "Painting", "Cocking")
    var selectedOut by remember { mutableStateOf("") }
    val selectedItems by remember { mutableStateOf(mutableListOf<String>()) }

    // Alert Dialog : confirm delete student
    var deleteDialog by remember { mutableStateOf(false) }


    Column{
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(modifier = Modifier.weight(0.85f))
            {
                Text(text = "Student Lists:",
                    fontSize = 25.sp
                )
            }
            Button(onClick = {
                showDialog = true
            }) {
                Text("Add Student")
            }
        }
        if (showDialog){
            // Create AlertDialog
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Enter Information") },
                text = {
                       Column {

                           OutlinedTextField(
                               value = textFieldID,
                               onValueChange = { textFieldID = it},
                               label = { Text("Enter your ID")}
                           )
                           OutlinedTextField(
                               value = textFieldName,
                               onValueChange = { textFieldName = it},
                               label = { Text("Enter your name")}
                           )
                           OutlinedTextField(
                               value = textFieldAge,
                               onValueChange = { textFieldAge = it},
                               label = { Text("Enter your age") },
                               keyboardOptions = KeyboardOptions(
                                   keyboardType = KeyboardType.NumberPassword,
                               )
                           )
                           Spacer(modifier = Modifier.height(20.dp))

                           Text("Select your hobby:",fontSize = 18.sp)
//                           Column {
//                               CheckboxGroup(
//                                   items = listOf("Reading", "Painting", "Cooking"),
//                                   onSelectionChange = { selectedHobby = it }
//                               )
//                           }
                           Column {
                               CheckboxGroup(items = hobbyList) { newSelectedItems ->
                                   selectedItems.clear()
                                   selectedItems.addAll(newSelectedItems)
                                   Log.d("CheckboxGroup", "Selected items: $selectedItems")
                                   selectedOut = selectedItems.toString()
                               }
                           }
                       }
                },

                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false

                            var lastIndex = selectedItems.size-1
                            var hobbySelect=""

                            selectedItems.forEachIndexed {index, item ->
                                hobbySelect += if (index == lastIndex) item else "$item, "
                            }

                            studentItemList.add(Student(textFieldID,textFieldName,textFieldAge,hobbySelect))
                            // clear value
                            textFieldID = ""
                            textFieldName = ""
                            textFieldAge = ""
                            //selectedItems.clear()
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false
                        textFieldID=""
                        textFieldName=""
                        textFieldAge=""}
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            var itemClick = Student("","","","")
            itemsIndexed(items = studentItemList,
            ){ index, item ->
              Card(
                  modifier = Modifier
                      .padding(horizontal = 8.dp, vertical = 8.dp)
                      .fillMaxWidth()
                      .height(120.dp),
                  colors = CardDefaults.cardColors(
                      containerColor = Color.White,
                  ),
                  elevation = CardDefaults.cardElevation(
                      defaultElevation = 2.dp
                  ),
                  shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                  onClick = { Toast.makeText(contextForToast,
                      "Click on ${item.name}.",
                      Toast.LENGTH_SHORT).show()}

              ) {

                  Row (
                      Modifier
                          .fillMaxWidth()
                          .height(Dp(120f))
                          .padding(10.dp)
                  ){
                      Text(
                          text = "ID: ${item.id}\n" +
                          "Name: ${item.name}\n"+
                          "Age: ${item.age}\n"+
                          "Hobby: ${item.hobby}", //.joinToString(", ")
                          Modifier.weight(0.85f)
                      )
                      TextButton(
                          onClick = {
                                itemClick = item
                                deleteDialog = true
                          }
                      )
                      {
                        Text("Delete")
                      }
                  }
                  if (deleteDialog){
                      AlertDialog(
                          onDismissRequest = {
                              deleteDialog = false
                          },
                          title = { Text(text = "Warning") },
                          text = { Text("Are you sure you want to delete ${itemClick.name}?") },
                          confirmButton = {
                              TextButton(
                                  onClick = {
                                      deleteDialog = false
                                      Toast.makeText(contextForToast,
                                          "Yes, ${itemClick.name} is deleted",
                                          Toast.LENGTH_SHORT).show()
                                      studentItemList.remove(itemClick)
                                  }
                              ) {
                                    Text(text = "Yes")
                              }
                          },
                          dismissButton = {
                              TextButton(
                                  onClick = {
                                      deleteDialog = false
                                      Toast.makeText(contextForToast,
                                          "Don't delete ${itemClick.name}", Toast.LENGTH_SHORT).show()
                                  }
                              ) {
                                  Text(text = "No")
                              }
                          },
                      )
                  }
              }
            }
        }
    }
}

