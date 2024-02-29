package com.example.testbmi

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testbmi.ui.theme.TestBMITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestBMITheme {
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestBMITheme {
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
                     height:String, onHeightChange: (String)-> Unit,
                     weight:String, onWeightChange: (String)-> Unit,){
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
            value = height,
            onValueChange = onHeightChange,
            label = { Text("Height")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            )
        )
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = weight,
            onValueChange = onWeightChange,
            label = { Text("Weight")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
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
                        selectedColor = Color.Magenta
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
            text = "Gender : $selected",
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

// ตัว dropdown แสดงรายวิชา
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDropdown() : String {
    val keyboardController = LocalSoftwareKeyboardController.current
    val subjectsList = listOf(
        "Select Subject",
        "SC362007 Mobile Device Programming",
        "SC362004 Web Application Programming",
        "SC362005 Database Analysis and Design" )
    var expanded by remember { mutableStateOf(false) }
    var selectedSubject by remember { mutableStateOf(subjectsList[0]) }

    ExposedDropdownMenuBox(modifier = Modifier
        .clickable { keyboardController?.hide() },
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded }
    )
    {
        OutlinedTextField(
            modifier = Modifier
                .width(340.dp)
                .menuAnchor()
                .clickable { keyboardController?.hide() },
            textStyle = TextStyle.Default.copy(fontSize = 12.sp),
            readOnly = true,
            value = selectedSubject,
            onValueChange = {},
            label = { Text("Subjects")},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        // Dropdown menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false }
        )
        {
            //show items
            subjectsList.forEach { selectionObject ->
                DropdownMenuItem(
                    text = { Text(selectionObject) },
                    onClick = {
                        selectedSubject = selectionObject
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
    return selectedSubject
}

@Composable
fun Mypage1(navHostController: NavHostController){
    var id by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var genderSelected by rememberSaveable { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }

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
            text = "Enter Person Information",
            fontSize = 20.sp
        )

        //Call IdNameAgeContent function
        IdNameAgeContent (id = id, onIdChange = { id=it },
            name = name, onNameChange = { name = it },
            height = height, onHeightChange = { height = it},
            weight = weight, onWeightChange = { weight = it})

        subject = SubjectDropdown()

        RadioUse(onGenderSelected = { genderSelected = it })

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
                val person = Person(id, name, height.toDouble(), weight.toDouble(),genderSelected, selectedItems,0.0,subject)
                val bmi = person.calculateBMI()
                navHostController.currentBackStackEntry?.savedStateHandle?.set("data", person.copy(bmi = bmi))
                navHostController.navigate(ScreenRoute.Second.route)
            }
        ) {
            Text(text="Send Information")
        }
    }
}

@Composable
fun Mypage2(navHostController: NavHostController){
    val data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Person>("data")?:
    Person("","",0.0,0.0,"", listOf(),0.0,"")

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

        Text(
            modifier = Modifier.padding(16.dp),
            text = "Person ID: ${data.id} \n\n" +
                    "Person Name: ${data.name} \n\n" +
                    "Person Height: ${data.height} \n\n" +
                    "Person Weight: ${data.weight} \n\n" +
                    "Subject: ${data.subject}\n\n"+
                    "Gender: ${data.gender} \n\n" +
                    "Hobby: $hobbySelect\n\n" +
                    "BMI: ${String.format("%.3f", data.bmi)}\n\n"+
                    "BMI Category: ${getBMICategory(data.bmi)}",
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

fun getBMICategory(bmi: Double): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 24.9 -> "Normal weight"
        bmi < 29.9 -> "Overweight"
        else -> "Obese"
    }
}