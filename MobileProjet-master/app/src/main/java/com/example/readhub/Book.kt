package com.example.readhub

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    @Expose
    @SerializedName("book_id") val book_id: Int,

    @Expose
    @SerializedName("book_img") val book_img: String,

    @Expose
    @SerializedName("book_name") val book_name: String,

    @Expose
    @SerializedName("description") val description: String,

    @Expose
    @SerializedName("num_of_read") val num_of_read: Int,

    @Expose
    @SerializedName("writer_name") val writer_name: String,

    @Expose
    @SerializedName("btype_id") val btype_id: String,

    @Expose
    @SerializedName("pub_id") val pub_id: String
) : Parcelable
