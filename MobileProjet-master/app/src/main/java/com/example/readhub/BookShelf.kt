package com.example.readhub

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookShelf(
    @Expose
    @SerializedName("bookshelf_id") val bookshelfId: Int,

    @Expose
    @SerializedName("book_id") val bookId: Int,

    @Expose
    @SerializedName("user_id") val userId: String?,
    ): Parcelable
