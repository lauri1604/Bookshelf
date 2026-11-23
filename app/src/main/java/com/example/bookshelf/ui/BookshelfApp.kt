package com.example.bookshelf.ui

import androidx.compose.runtime.Composable
import com.example.bookshelf.ui.screens.BookshelfRoute
import com.example.bookshelf.ui.theme.BookshelfTheme

@Composable
fun BookshelfApp() {
    BookshelfTheme {
        BookshelfRoute()
    }
}
