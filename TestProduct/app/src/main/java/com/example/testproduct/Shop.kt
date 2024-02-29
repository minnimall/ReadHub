package com.example.testproduct

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Shop(
    val brand: String,
    val product: String,
    val size: List<String>,
    val total: Int,
    val average: Int,
): Parcelable