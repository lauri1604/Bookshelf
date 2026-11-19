package com.example.bookshelf.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.Book
import com.google.gson.JsonParseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Paso 2: el ViewModel coordina la pantalla; después conectaremos aquí el repositorio de red.
class BookshelfViewModel(
    private val booksRepository: BooksRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(BookshelfUiState())
    val uiState = _uiState.asStateFlow()

    // La lista se mantiene aparte del UiState
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            // Mostrar carga mientras se hace la petición; al terminar, publicar éxito o error.
            _books.update { emptyList() }
            _uiState.update { it.copy(status = BookshelfStatus.LOADING) }
            try {
                val books = booksRepository.getBooks()
                _books.update { books }
                _uiState.update { it.copy(status = BookshelfStatus.SUCCESS) }
            } catch (_: IOException) {
                _uiState.update { it.copy(status = BookshelfStatus.ERROR) }
            } catch (_: HttpException) {
                _uiState.update { it.copy(status = BookshelfStatus.ERROR) }
            } catch (_: JsonParseException) {
                _uiState.update { it.copy(status = BookshelfStatus.ERROR) }
            }
        }
    }

    companion object {
        // La factory recibe el repositorio para que el ViewModel no cree dependencias por su cuenta.
        fun factory(booksRepository: BooksRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    BookshelfViewModel(booksRepository)
                }
            }
    }
}
