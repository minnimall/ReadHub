package com.example.readhub

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InterestedClass(
    @Expose
    @SerializedName("success") val success:Int,

    @Expose
    @SerializedName("interested_item") val interested_item:String
)
