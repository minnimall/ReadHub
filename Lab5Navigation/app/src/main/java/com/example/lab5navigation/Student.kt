package com.example.lab5navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    var id: String,
    val name: String,
    val age: Int,
    val hobby: List<String>
): Parcelable