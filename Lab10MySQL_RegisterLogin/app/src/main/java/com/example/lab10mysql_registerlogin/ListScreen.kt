package com.example.lab10mysql_registerlogin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController){
    val createClient = StudentAPI.create()
    var studentItemsList = remember { mutableStateListOf<ProfileClass>() }
    val contextForToast = LocalContext.current.applicationContext

    //check lifecycle state
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()


    LaunchedEffect(lifecycleState){
        when (lifecycleState){
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                showAllData(studentItemsList, contextForToast)
            }
        }
    }
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
                Text(
                    text = "All Students: ",
                    fontSize = 25.sp
                )
            }
            Button(onClick = {
                if(navController.currentBackStack.value.size >= 2){
                    navController.popBackStack()
                }
                navController.navigate(Screen.Profile.route)
            }) {
                Text("Back To Profile")
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ){
            var itemClick = ProfileClass("","","","")
            itemsIndexed(
                items = studentItemsList,
            ){index, item ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .height(130.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    ),
                    shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                    onClick = {
                        Toast.makeText(
                            contextForToast,"Click on ${item.std_name}.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Row (
                        Modifier
                            .fillMaxWidth()
                            .height(Dp(130f))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "ID: ${item.std_id}\n" +
                                "Name: ${item.std_name}\n" +
                                "Gender: ${item.std_gender}\n" +
                                "Role: ${item.role}",
                            Modifier.weight(0.85f)
                        )
                    }
                }
            }
        }
    }
}


fun showAllData(studentItemsList: MutableList<ProfileClass>, context: Context){
    val createClient = StudentAPI.create()
    createClient.retrieveStudent()
        .enqueue(object : Callback<List<LoginClass>> {
            override fun onResponse(call: Call<List<LoginClass>>,
                                    response: Response<List<LoginClass>>
            ) {
                studentItemsList.clear()
                response.body()?.forEach {
                    studentItemsList.add(ProfileClass(it.std_id, "", "", ""))
                }
                studentItemsList.forEach { profile ->
                    createClient.searchStudent(profile.std_id)
                        .enqueue(object : Callback<ProfileClass> {
                            override fun onResponse(call: Call<ProfileClass>, response: Response<ProfileClass>) {
                                val updatedProfile = response.body()
                                if (updatedProfile != null) {
                                    val index = studentItemsList.indexOfFirst { it.std_id == updatedProfile.std_id }
                                    if (index != -1) {
                                        studentItemsList[index] = updatedProfile
                                    }
                                }
                            }
                            override fun onFailure(call: Call<ProfileClass>, t: Throwable) {
                                Toast.makeText(context, "Error onFailure" + t.message, Toast.LENGTH_LONG).show()
                            }
                        })
                }
            }
            override fun onFailure(call: Call<List<LoginClass>>, t: Throwable) {
                Toast.makeText(context, "Error onFailure" + t.message, Toast.LENGTH_LONG).show()
            }
        })
}
