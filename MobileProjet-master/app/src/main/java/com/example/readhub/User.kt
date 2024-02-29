package com.example.readhub

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class user(
    @Expose
    @SerializedName("user_id") val user_id: Int,

    @Expose
    @SerializedName("user_name") val user_name: String,

    @Expose
    @SerializedName("email") val email: String,

    @Expose
    @SerializedName("password") val password: String,
)

