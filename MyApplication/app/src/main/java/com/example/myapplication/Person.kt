package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    var id: String,
    val name: String,
    var height: Double,
    var weight: Double,
    val gender: String,
    val hobby: List<String>,
    val bmi: Double
): Parcelable{

    fun calculateBMI(): Double {
        val heightInMeter = height / 100.0
        return weight / (heightInMeter * heightInMeter)
    }
}
