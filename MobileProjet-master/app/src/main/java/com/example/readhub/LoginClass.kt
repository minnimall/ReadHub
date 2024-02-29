package com.example.readhub

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginClass(
    @Expose
    @SerializedName("success") val success:Int,

    @Expose
    @SerializedName("user_name") val user_name:String
)
