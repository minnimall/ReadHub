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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RepasswordScreen(navController: NavHostController){
    val data = navController.previousBackStackEntry?.savedStateHandle?.get<user>("data")?:
    user(0,"","","")
    var textFieldName by remember { mutableStateOf(data.user_name) }
    var textFieldEmail by remember { mutableStateOf(data.email) }
    var textFieldPassword by remember { mutableStateOf(data.password) }

    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(contextForToast)
    val userId = sharedPreferences.userId ?: ""
    var newPass by remember { mutableStateOf(data.password) }
    var ConfirmPass by remember { mutableStateOf(data.password) }

    val createClient = UserApi.create()
    val initialUser = user(0,"","","")
    var userItems by remember { mutableStateOf(initialUser) }

//    sharedPreferences = SharedPreferencesManager(context = contextForToast)

    println("now logged in: "+ userId)

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column{
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(modifier = Modifier.size(80.dp),
                    onClick = {
                        navController.navigate(Screen.Login.route)
                    }
                )
                {
                    Icon(Icons.Default.ArrowBack, contentDescription = "", tint = Color.Blue)
                }
                Text(
                    text = "เปลี่ยนรหัสผ่าน",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(start = 20.dp)
                )

            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = textFieldEmail,
            onValueChange = { newValue ->
                textFieldEmail = newValue
            },
            label = {
                Text("Email: ")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = newPass,
            onValueChange = { newValue ->
                newPass = newValue
            },
            label = {
                Text("New Password: ")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = ConfirmPass,
            onValueChange = { newValue ->
                ConfirmPass = newValue
            },
            label = {
                Text("Confirm New Password: ")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if(newPass == ConfirmPass){
                    textFieldPassword = newPass
                    createClient.ResetPasswordUser(
                        textFieldEmail,textFieldPassword
                    ).enqueue(object : Callback<user>{
                        override fun onResponse(call: Call<user>, response: Response<user>) {
                            if (response.isSuccessful){
                                Toast.makeText(contextForToast,"Successfully Reset Password",
                                    Toast.LENGTH_LONG).show()
                                navController.navigate(Screen.Login.route)

                            }else{
                                Toast.makeText(contextForToast,"Reset Password Failure",
                                    Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<user>, t: Throwable) {
                            Toast.makeText(contextForToast, "Error onFailure "+ t.message,
                                Toast.LENGTH_LONG).show()
                        }
                    })
                }
                else{
                    Toast.makeText(contextForToast,"กรุณายืนยันรหัสผ่านให้ตรงกัน",
                        Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.padding(8.dp) // กำหนด padding เพื่อขนาดเล็กลง
        ) {
            Text(text = "ยืนยัน")
        }
    }
}