package com.example.bookshelf.ui

// Paso 1: definir los estados que verá la pantalla; la lista de libros se mantiene aparte.
data class BookshelfUiState(
    val status: BookshelfStatus = BookshelfStatus.LOADING
)

enum class BookshelfStatus {
    LOADING,
    SUCCESS,
    ERROR
}
