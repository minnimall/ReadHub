package com.example.assignment4

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment4.ui.theme.Assignment4Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterForm()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assignment4Theme {
        RegisterForm()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameContent(username:String, onUserChange: (String)-> Unit,
                password: String, onPasswordChage: (String) -> Unit){
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
fun RegisterForm(){
    val contextForToast = LocalContext.current.applicationContext
    var textInformation by rememberSaveable { mutableStateOf("") }

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var genderSelected by rememberSaveable { mutableStateOf("") }

    var email by rememberSaveable { mutableStateOf("") }

    var date by rememberSaveable {mutableStateOf("")}

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(modifier = Modifier.padding(5.dp),
            text = "Register Form",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp)

        NameContent(username = username, onUserChange = {username=it}, password = password, onPasswordChage = {password = it})

        RadioUse(onGenderSelected = { genderSelected = it })

        EmailContent(email = email, OnEmailChange = {email=it})

        date = MyDatePicker()

        Spacer(modifier = Modifier.height(height = 8.dp))
        Button(
            onClick = {
                textInformation = "Name: $username\n" +
                        "Password: $password\n" +
                        "Gender: $genderSelected\n" +
                        "E-mail: $email\n" +
                        "Birthday: $date"

            }
        ){
            Text(text = "Register")
        }
        Column(
            modifier = Modifier
                .width(400.dp)
                .padding(16.dp)
                .wrapContentHeight(unbounded = true)
                .background(color = Color.Green.copy(alpha = 0.3f))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(20.dp)
                )
        )

        {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Register Information: ",
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = textInformation,
                fontSize = 18.sp
            )
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

@OptIn(ExperimentalMaterial3Api::class)
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