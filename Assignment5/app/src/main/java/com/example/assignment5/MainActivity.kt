package com.example.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment5.ui.theme.Assignment5Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment5Theme {
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment5Theme {
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
fun Mypage1(navHostController: NavHostController){

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var genderSelected by rememberSaveable { mutableStateOf("") }

    var email by rememberSaveable { mutableStateOf("") }

    var date by rememberSaveable {mutableStateOf("")}

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
            text = "Register Form",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        InputForm(username = username, onUserChange = {username=it}, password = password, onPasswordChage = {password = it})

        RadioUse(onGenderSelected = { genderSelected = it })

        EmailContent(email = email, OnEmailChange = {email=it})

        date = MyDatePicker()

        Spacer(modifier = Modifier.height(height = 8.dp))

        Button(
            onClick= {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "data",
                    Register (username, password, genderSelected, email ,date)
                )
                navHostController.navigate(ScreenRoute.Second.route)
            }
        ) {
            Text(text="Send Register")
        }
    }
}

@Composable
fun InputForm(username:String, onUserChange: (String)-> Unit,
              password: String, onPasswordChage: (String) -> Unit, ){
    Column(modifier = Modifier.padding(horizontal = 16.dp, )){
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = username,
            onValueChange = onUserChange,
            label = { Text("Username")}
        )
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = password,
            onValueChange = onPasswordChage,
            label = { Text("Password")},
            visualTransformation = PasswordVisualTransformation()

        )

    }
}

@Composable
fun EmailContent(email:String, OnEmailChange: (String)-> Unit){
    Column(modifier = Modifier.padding(horizontal = 16.dp, ))
    {
        OutlinedTextField(
            modifier = Modifier.width(400.dp),
            value = email,
            onValueChange = OnEmailChange,
            label = { Text("E-mail")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
            )
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(): String{
    val calendar = Calendar.getInstance()
    val mYear = calendar.get(Calendar.YEAR)
    val mMonth = calendar.get(Calendar.MONTH)
    val mDay = calendar.get(Calendar.DAY_OF_MONTH)
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    calendar.set(mYear, mMonth,mDay)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = calendar.timeInMillis
    )
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableLongStateOf(calendar.timeInMillis) }
    if(showDatePicker){
        DatePickerDialog(onDismissRequest = { showDatePicker = false},
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    selectedDate = datePickerState.selectedDateMillis!!
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {showDatePicker = false}) {
                    Text(text = "Cancel")
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }
    Column(modifier = Modifier.padding(horizontal = 16.dp)){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Text(modifier = Modifier.padding(5.dp),
                text = "Birthday")
            FilledIconButton(onClick = {showDatePicker = true})
            {
                Icon(
                    modifier = Modifier.size(size = 30.dp),
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = "Time Icon",
                )
            }
            Text(text = ": ${formatter.format(Date(selectedDate))}")
        }
    }

    return  "${formatter.format(Date(selectedDate))}"
}

@Composable
fun Mypage2(navHostController: NavHostController) {
    val data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Register>("data")
        ?: Register("", "", "", "","")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(color = Color.Green.copy(alpha = 0.3f))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
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
                text = "Username: ${data.name} \n\n" +
                        "Password: ${data.password} \n\n" +
                        "Gender: ${data.gender} \n\n" +
                        "Email: ${data.email} \n\n" +
                        "Birthday: ${data.date}",
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
}




