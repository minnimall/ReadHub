package com.example.assignment5

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Register (
    val name: String,
    val password: String,
    val gender: String,
    val email: String,
    val date: String,
): Parcelable