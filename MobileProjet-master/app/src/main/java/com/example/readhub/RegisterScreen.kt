package com.example.readhub

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    val createClient = UserApi.create()
    val contextForToast = LocalContext.current.applicationContext

    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isButtonEnable by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userName,
            onValueChange = {
                userName = it
                isButtonEnable = validateInput(userName, userEmail,password)
            },
            label = { Text(text = "Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = userEmail,
            onValueChange = {
                userEmail = it
                isButtonEnable = validateInput(userName, userEmail,password)
            },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isButtonEnable = validateInput(userName, userEmail,password)
            },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            keyboardController?.hide()
            focusManager.clearFocus()
            createClient.registerUser(
                userName,userEmail,password
            ).enqueue(object : Callback<LoginClass>{
                override fun onResponse(call: Call<LoginClass>, response: Response<LoginClass>) {
                    if(response.isSuccessful){
                        val body = response.body()
                        sharedPreferences.isLoggedIn=true
                        sharedPreferences.userId = body!!.user_name

                        Toast.makeText(contextForToast,"Successfully Inserted",
                            Toast.LENGTH_LONG).show()
                        if (navController.currentBackStack.value.size >= 2){
                            println("ทำอยู่ค้าบเตง")
                            navController.popBackStack()
                        }
                        navController.navigate(Screen.UserInterested.route)
                    }else{
                        Toast.makeText(contextForToast,"Inserted Failed",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginClass>, t: Throwable) {
                    Toast.makeText(
                        contextForToast, "Error onFailure rg" + t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        },
            enabled = isButtonEnable,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Next")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row (
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account?"
            )
            TextButton(
                onClick = {
                    navController.navigate(Screen.Login.route)
                },
            ) {
                Text(text = "Login")
            }
        }
    }
}
fun validateInput(userName: String, userEmail: String,password: String):Boolean{
    return !userName.isNullOrEmpty()&& !userEmail.isNullOrEmpty()&& !password.isNullOrEmpty()
}
