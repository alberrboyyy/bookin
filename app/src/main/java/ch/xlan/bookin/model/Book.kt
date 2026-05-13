package ch.xlan.bookin.model

import com.google.gson.annotations.SerializedName

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val summary: String?,
    val nbPages: Int,
    val editor: String?,
    val epubFileUrl: String?,
    val coverImageUrl: String?,
    val publishDate: String?,
    val createdAt: String?
)
