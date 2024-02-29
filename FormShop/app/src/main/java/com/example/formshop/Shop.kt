package com.example.formshop

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    var id: String,
    val name: String,
    val product: String,
    val size: List<String>,
    val total: Int,
): Parcelable