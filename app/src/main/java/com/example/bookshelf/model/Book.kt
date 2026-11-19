package com.example.bookshelf.model

// Paso 6: definir el modelo de libro que usará la app tras transformar la respuesta JSON.
data class Book(
    val key: String,
    val title: String,
    val authors: List<String>,
    val firstPublishYear: Int?,
    val isbn: List<String>,
    val coverId: Int?,
    val editionCount: Int?
) {
    // cover_i permite formar la URL; por ahora solo se muestra como texto, sin descargar la imagen.
    val coverUrl: String?
        get() = coverId?.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" }
}
