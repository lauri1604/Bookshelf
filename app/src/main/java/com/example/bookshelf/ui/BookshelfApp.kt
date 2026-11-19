package com.example.bookshelf.ui

import androidx.compose.runtime.Composable
import com.example.bookshelf.ui.screens.BookshelfScreen
import com.example.bookshelf.ui.theme.BookshelfTheme

@Composable
fun BookshelfApp() {
    BookshelfTheme {
        BookshelfScreen()
    }
}
