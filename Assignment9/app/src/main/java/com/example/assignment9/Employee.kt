package com.example.assignment9
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    @Expose
    @SerializedName("emp_name") val emp_name: String,

    @Expose
    @SerializedName("emp_gender") val emp_gender: String,

    @Expose
    @SerializedName("emp_email") val emp_email: String,

    @Expose
    @SerializedName("emp_salary") val emp_salary: Int
): Parcelable
