package com.example.readhub

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CheckboxGroup(items: List<String>, onSelectionChange: (List<String>) -> Unit) {
    var selectedItems by remember { mutableStateOf(mutableListOf<String>()) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
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
fun UserInterested(navController: NavHostController) {
    val categoryList = listOf(
        "Love Novel", "Boy Love", "Girl Love", "Fantasy", "Horror",
        "Turn back time", "Historical"
    )
    val createClient = UserApi.create()
    var studentItems = remember { mutableStateListOf<LoginClass>() }

    var selectedItems by remember { mutableStateOf(mutableListOf<String>()) }
    val contextForToast = LocalContext.current.applicationContext

    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Interested category ",
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CheckboxGroup(items = categoryList) { newSelectedItems ->
                selectedItems = newSelectedItems.toMutableList()
                Log.d("CheckboxGroup", "Selected items: $selectedItems")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()

                createClient.addUserInterested(selectedItems.toString())
                navController.navigate(Screen.Login.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Login")
        }
    }
}
