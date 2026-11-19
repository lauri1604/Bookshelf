package com.example.bookshelf.data
import com.example.bookshelf.network.OpenLibraryApiService
import com.example.bookshelf.network.RetrofitInstance

// Paso 8: reunir el servicio y el repositorio para inyectarlos manualmente en el ViewModel.
interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer : AppContainer {
    // lazy evita crear los objetos hasta que se necesitan y luego reutiliza la misma instancia.
    private val apiService: OpenLibraryApiService by lazy {
        RetrofitInstance.api
    }
    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(apiService)
    }
}
