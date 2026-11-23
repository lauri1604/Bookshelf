package com.example.bookshelf.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BooksRepository
import com.google.gson.JsonParseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BookshelfViewModel(application: Application) : AndroidViewModel(application) {
    private val booksRepository: BooksRepository =
        (application as BookshelfApplication).container.booksRepository

    private val _uiState = MutableStateFlow(BookshelfUiState())
    val uiState: StateFlow<BookshelfUiState> = _uiState.asStateFlow()

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(status = BookshelfStatus.LOADING, books = emptyList())
            }
            try {
                val books = booksRepository.getBooks()
                _uiState.update {
                    it.copy(status = BookshelfStatus.SUCCESS, books = books)
                }
            } catch (_: IOException) {
                _uiState.update { it.copy(status = BookshelfStatus.ERROR) }
            } catch (_: HttpException) {
                _uiState.update { it.copy(status = BookshelfStatus.ERROR) }
            } catch (_: JsonParseException) {
                _uiState.update { it.copy(status = BookshelfStatus.ERROR) }
            }
        }
    }
}
