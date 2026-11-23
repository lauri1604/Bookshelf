package com.example.bookshelf.ui

import com.example.bookshelf.model.Book

data class BookshelfUiState(
    val status: BookshelfStatus = BookshelfStatus.LOADING,
    val books: List<Book> = emptyList()
)

enum class BookshelfStatus {
    LOADING,
    SUCCESS,
    ERROR
}
