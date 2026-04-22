package ch.xlan.bookin.model

import com.google.gson.annotations.SerializedName

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val description: String?,

    @SerializedName("cover_image_path")
    val coverImagePath: String?,

    @SerializedName("epub_file_path")
    val epubFilePath: String?,

    @SerializedName("file_size_bytes")
    val fileSizeBytes: Long?,

    val isbn: String?,
    val language: String?,

    @SerializedName("publish_date")
    val publishDate: String?,

    @SerializedName("uploaded_at")
    val uploadedAt: String?
)
