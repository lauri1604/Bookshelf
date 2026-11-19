package com.example.bookshelf.data
import com.example.bookshelf.model.Book
import com.example.bookshelf.network.OpenLibraryApiService

// Paso 7: el repositorio separa el ViewModel de los detalles de la petición y la respuesta.
interface BooksRepository {
    suspend fun getBooks(): List<Book>
}

class NetworkBooksRepository(
    private val apiService: OpenLibraryApiService
) : BooksRepository {
    // Pide el JSON y transforma sus resultados al modelo que utilizará la interfaz.
    override suspend fun getBooks(): List<Book> =
        apiService.searchBooks().docs.mapIndexedNotNull { index, item ->
            val title = item.title ?: return@mapIndexedNotNull null

            Book(
                key = item.key ?: "$title-$index",
                title = title,
                authors = item.authorName.orEmpty(),
                firstPublishYear = item.firstPublishYear,
                isbn = item.isbn.orEmpty(),
                coverId = item.coverId,
                editionCount = item.editionCount
            )
        }
}
