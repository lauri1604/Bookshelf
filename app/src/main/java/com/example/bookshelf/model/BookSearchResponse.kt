package com.example.bookshelf.model
import com.google.gson.annotations.SerializedName

// Paso 5: representar la respuesta JSON para que Gson pueda convertirla en objetos Kotlin.
data class BookSearchResponse(
    val docs: List<BookSearchItem> = emptyList()
)

// @SerializedName enlaza los nombres con guion bajo del JSON a propiedades Kotlin.
data class BookSearchItem(
    val key: String? = null,
    val title: String? = null,
    @SerializedName("author_name")
    val authorName: List<String>? = null,
    @SerializedName("first_publish_year")
    val firstPublishYear: Int? = null,
    val isbn: List<String>? = null,
    @SerializedName("cover_i")
    val coverId: Int? = null,
    @SerializedName("edition_count")
    val editionCount: Int? = null
)
