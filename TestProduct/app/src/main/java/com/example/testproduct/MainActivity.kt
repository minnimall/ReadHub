package com.example.testproduct

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
import com.example.testproduct.ui.theme.TestProductTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestProductTheme {
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
    TestProductTheme {
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
fun RadioUse(onBrandSelected: (String) -> Unit) {
    val kinds = listOf("Chang", "Leo", "Sing")
    val (selected, setSelected) = remember { mutableStateOf("") }

    Column {
        Text(
            text = "Brand : $selected",
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
                    onBrandSelected(it)
                }
            )
        }
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

// ตัว dropdown แสดงรายวิชา
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDropdown() : String {
    val keyboardController = LocalSoftwareKeyboardController.current
    val productsList = listOf(
        "Select Product",
        "Pepsi",
        "Coca-Cola",
        "Est" )
    var expanded by remember { mutableStateOf(false) }
    var selectedProducts by remember { mutableStateOf(productsList[0]) }

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
            value = selectedProducts,
            onValueChange = {},
            label = { Text("Products")},
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
            productsList.forEach { selectionObject ->
                DropdownMenuItem(
                    text = { Text(selectionObject) },
                    onClick = {
                        selectedProducts = selectionObject
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
    return selectedProducts
}

@Composable
fun Mypage1(navHostController: NavHostController){
    var brandSelected by rememberSaveable { mutableStateOf("") }
    var products by remember { mutableStateOf("") }
    var total by remember { mutableStateOf(0) }


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

        RadioUse(onBrandSelected = { brandSelected = it })

        products = ProductDropdown()

        val sizeList = listOf("12 oz", "16 oz", "22 oz")
        var selectedOut by remember { mutableStateOf("") }
        val selectedItems by remember { mutableStateOf(mutableListOf<String>()) }

        Text(
            modifier = Modifier.padding (horizontal = 16.dp, vertical = 5.dp),
            text = "Select your Size:"
        )
        Row {
            CheckboxGroup(items = sizeList) { newSelectedItems ->
                selectedItems.clear()
                selectedItems.addAll(newSelectedItems)
                Log.d("CheckboxGroup", "Selected items: $selectedItems")
                selectedOut = selectedItems.toString()
            }
        }
        Spacer (modifier = Modifier.height(height = 8.dp))

        // ฟังก์ชันนี้จะคำนวณค่าของ size ที่เลือกมาแล้วแสดงผลลัพธ์
        fun calculateSizeTotal(selectedItems: List<String>): Int {
            return selectedItems.map { it.substringBefore(" oz").toInt() }.sum()
        }

        // ฟังก์ชันนี้จะคำนวณค่าเฉลี่ยของ size ที่เลือกมาแล้ว
        fun calculateSizeAverage(selectedItems: List<String>): Int {
            // ถ้าไม่มีรายการที่เลือกให้คืนค่า 0.0
            if (selectedItems.isEmpty()) {
                return 0
            }

            // คำนวณค่าเฉลี่ย
            val total = selectedItems.map { it.substringBefore(" oz").toInt() }.sum()
            return total.toInt() / selectedItems.size
        }

        Button(
            onClick= {
                // ทำการคำนวณค่า total จาก size ที่เลือกแล้วแสดงผลลัพธ์
                total = calculateSizeTotal(selectedItems)

                // คำนวณค่าเฉลี่ยจาก size ที่เลือกแล้ว
                val average = calculateSizeAverage(selectedItems)

                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "data",
                    Shop(brandSelected, products, selectedItems, total,average)
                )
                navHostController.navigate(ScreenRoute.Second.route)
            }
        ) {
            Text(text="Send Information")
        }
    }
}

@Composable
fun Mypage2(navHostController: NavHostController){
    val data = navHostController.previousBackStackEntry?.savedStateHandle?.get<Shop>("data")?:
    Shop("","",listOf(),0,0)

    var lastIndex = data.size.size-1
    var productSelect=""

    data.size.forEachIndexed{index, item ->
        productSelect += if (index == lastIndex) item else "$item, "
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
            text = "Brand: ${data.brand} \n\n" +
                    "Products: ${data.product} \n\n" +
                    "Total Size: ${data.total}\n\n"+
                    "Average Size: ${data.average}",
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