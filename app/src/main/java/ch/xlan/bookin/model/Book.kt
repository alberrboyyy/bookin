package ch.xlan.bookin.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val tags: List<String>
)